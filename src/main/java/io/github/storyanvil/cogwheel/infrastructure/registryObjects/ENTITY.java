package io.github.storyanvil.cogwheel.infrastructure.registryObjects;

import io.github.storyanvil.cogwheel.Cogwheel;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ENTITY {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Cogwheel.modId);

    public static final RegistryObject<EntityType<NPC>> NPC =
            ENTITY_TYPES.register("npc", () -> EntityType.Builder.of(io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f).build("npc"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
