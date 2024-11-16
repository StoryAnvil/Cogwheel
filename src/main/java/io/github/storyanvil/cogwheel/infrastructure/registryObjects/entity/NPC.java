package io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity;

import io.github.storyanvil.cogwheel.infrastructure.registryObjects.ITEMS;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class NPC extends Animal {

    public NPC(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idle =  new AnimationState();
    private int idleAnimTimeout = 0;
    private static final EntityDataAccessor<String> DATA_skin = SynchedEntityData.defineId(NPC.class, EntityDataSerializers.STRING);

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            setupState();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }
    private void setupState() {
        if (this.idleAnimTimeout <= 0) {
            this.idleAnimTimeout = this.random.nextInt(40) + 80;
            this.idle.start(this.tickCount);
        } else {
            --this.idleAnimTimeout;
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 2D, Ingredient.of(ITEMS.NPC_FOLLOW.get()), false));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 5f));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_skin, "test");
    }

    public String getSkin() {
        return entityData.get(DATA_skin);
    }
    public void setSkin(String skin) {
        entityData.set(DATA_skin, skin);
    }
}
