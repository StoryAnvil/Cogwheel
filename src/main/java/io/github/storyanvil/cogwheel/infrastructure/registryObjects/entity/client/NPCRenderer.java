package io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.storyanvil.cogwheel.Cogwheel;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.NPC;
import io.github.storyanvil.cogwheel.infrastructure.registryObjects.entity.models.NPCModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NPCRenderer extends MobRenderer<NPC, NPCModel<NPC>> {

    public NPCRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new NPCModel<>(pContext.bakeLayer(ModelLayers.NPC_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(NPC pEntity) {
        return new ResourceLocation(Cogwheel.modId, "textures/entity/" + pEntity.getSkin() + ".png");
    }

    @Override
    public void render(NPC pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
