package cn.bzgzs.largeprojects.client.renderer.blockentity;

import cn.bzgzs.largeprojects.LargeProjects;
import cn.bzgzs.largeprojects.api.energy.TransmitNetwork;
import cn.bzgzs.largeprojects.world.level.block.TransmissionRodBlock;
import cn.bzgzs.largeprojects.world.level.block.entity.TransmissionRodBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Optional;

public class TransmissionRodRenderer implements BlockEntityRenderer<TransmissionRodBlockEntity> {
	public static final ModelLayerLocation MAIN = new ModelLayerLocation(new ResourceLocation(LargeProjects.MODID, "transmission_rod"), "main");
	private static final ResourceLocation TEXTURE = new ResourceLocation(LargeProjects.MODID, ""); // TODO
	private final ModelPart main;

	public TransmissionRodRenderer(BlockEntityRendererProvider.Context context) {
		ModelPart root = context.bakeLayer(MAIN);
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void render(TransmissionRodBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
		poseStack.pushPose();
		poseStack.translate(0.5D, 0.5D, 0.5D);
		switch (blockEntity.getBlockState().getValue(TransmissionRodBlock.AXIS)) {
			case X -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
			case Z -> poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
		}
		Optional.ofNullable(blockEntity.getLevel()).ifPresent(level -> {
			long time = level.getGameTime() % 20;
			double oldDegree = (time - 1) * 18.0D * TransmitNetwork.Factory.get(level).speed(blockEntity.getBlockPos()) % 360.0D;
			double degree = time * 18.0D * TransmitNetwork.Factory.get(level).speed(blockEntity.getBlockPos()) % 360.0D;
			poseStack.mulPose(Vector3f.YP.rotationDegrees((float) Mth.lerp(partialTick, oldDegree, degree))); // TODO 可能会出现角度问题
		});
		main.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}
}
