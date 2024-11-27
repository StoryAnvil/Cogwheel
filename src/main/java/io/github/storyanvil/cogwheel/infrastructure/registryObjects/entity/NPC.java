package io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity;

import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.NPCAction;
import io.github.storyanvil.cogwheel.infrastructure.StoryWorks.Utils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NPC extends Animal {

    public NPC(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idle =  new AnimationState();
    private int idleAnimTimeout = 0;
    private int waitBeforeNextAction = 0;
    private boolean allowNextAction = true;
    public List<NPCAction> queue = Collections.synchronizedList(new ArrayList<NPCAction>());
    private NPCAction current;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            setupState();
        }
        if (waitBeforeNextAction > 0) {
            waitBeforeNextAction--;
        } else {
            if (!allowNextAction) {
                if (current != null && current.amIFreeToGo(this)) {
                    allowNextAction = true;
                }
            } else if (queue.size() > 0) {
                current = queue.remove(0);
                current.execute(this);
                addWaitTime(current.postDelay);
            }
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
        //this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 5f));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public String getSkin() {
        //return Utils.getEntityData(this, "skin");
        return "test";
    }
    public void setSkin(String skin) {
        Utils.setEntityData(this, "skin", skin);
    }
    public void addWaitTime(int ticks) {
        waitBeforeNextAction += ticks;
    }

    public void setAllowNextAction(boolean allowNextAction) {
        this.allowNextAction = allowNextAction;
    }
}
