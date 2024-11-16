package io.github.storyanvil.cogwheel.infrastructure;

import io.github.storyanvil.cogwheel.Cogwheel;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.client.ModelLayers;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.models.NPCModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cogwheel.modId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.NPC_LAYER, NPCModel::createBodyLayer);
    }
}
