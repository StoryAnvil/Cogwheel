package io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.client;

import io.github.storyanvil.cogwheel.Cogwheel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLayers {
    public static final ModelLayerLocation NPC_LAYER = new ModelLayerLocation(
            new ResourceLocation(Cogwheel.modId, "npc_layer"),
    "main");
}
