package io.github.storyanvil.cogwheel.infrastructure;

import io.github.storyanvil.cogwheel.Cogwheel;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.ENTITY;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.client.ModelLayers;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.models.NPCModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cogwheel.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ENTITY.NPC.get(), NPC.createAttributes().build());
    }
}
