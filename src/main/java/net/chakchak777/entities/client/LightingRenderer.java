package net.chakchak777.entities.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.FireBallEntity;
import net.chakchak777.entities.custom.LightningEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class LightingRenderer extends AzEntityRenderer<LightningEntity> {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/entity/lighting.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/entity/lighting.png"
    );



    public LightingRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<LightningEntity>builder(GEO, TEX)
                        .build(),
                context
        );
    }

    @Override
    public void render(
            @NotNull LightningEntity entity,
            float entityYaw,
            float partialTick,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int packedLight
    ) {

        poseStack.pushPose();


        float yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
        float pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());


        poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));

        poseStack.mulPose(Axis.ZP.rotationDegrees(pitch));

        poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 180.0F));




        super.render(entity, 0, partialTick, poseStack, bufferSource, packedLight);

        poseStack.popPose();
    }
}