package mod.milkycousin.milkandroses.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.milkycousin.milkandroses.common.entities.living.MilkyPenguinEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MilkyPenguinEntityModel extends EntityModel<MilkyPenguinEntity>
{
    private final ModelRenderer head_parts;
    private final ModelRenderer body_parts;
    private final ModelRenderer left_feet;
    private final ModelRenderer right_feet;
    private final ModelRenderer left_wing;
    private final ModelRenderer right_wing;

    public MilkyPenguinEntityModel()
    {
        textureWidth = 64;
        textureHeight = 64;

        head_parts = new ModelRenderer(this);
        head_parts.setRotationPoint(0.0F, 12.0F, 0.0F);
        head_parts.setTextureOffset(0, 16).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 5.0F, 5.0F, 0.0F, false);
        head_parts.setTextureOffset(0, 3).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

        body_parts = new ModelRenderer(this);
        body_parts.setRotationPoint(4.0F, 23.0F, -3.0F);
        body_parts.setTextureOffset(0, 0).addBox(-5.0F, -3.0F, 7.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        body_parts.setTextureOffset(0, 0).addBox(-8.0F, -9.0F, 0.0F, 8.0F, 9.0F, 7.0F, 0.0F, false);

        left_feet = new ModelRenderer(this);
        left_feet.setRotationPoint(0.0F, 24.0F, 0.0F);
        left_feet.setTextureOffset(23, 0).addBox(1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);

        right_feet = new ModelRenderer(this);
        right_feet.setRotationPoint(0.0F, 24.0F, 0.0F);
        right_feet.setTextureOffset(0, 26).addBox(-3.0F, -1.0F, -2.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);

        left_wing = new ModelRenderer(this);
        left_wing.setRotationPoint(4.5F, 14.0F, 0.5F);
        left_wing.setTextureOffset(25, 11).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, 0.0F, false);

        right_wing = new ModelRenderer(this);
        right_wing.setRotationPoint(-4.5F, 14.0F, 0.5F);
        right_wing.setTextureOffset(17, 21).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 8.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        head_parts.render(matrixStack, buffer, packedLight, packedOverlay);
        body_parts.render(matrixStack, buffer, packedLight, packedOverlay);
        left_feet.render(matrixStack, buffer, packedLight, packedOverlay);
        right_feet.render(matrixStack, buffer, packedLight, packedOverlay);
        left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
        right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setLivingAnimations(MilkyPenguinEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick)
    {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setRotationAngles(MilkyPenguinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.head_parts.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.head_parts.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.right_wing.rotateAngleZ = (float)Math.max(Math.sin((ageInTicks)) * 0.1, 0);
        this.left_wing.rotateAngleZ = (float)Math.min(-Math.sin((ageInTicks)) * 0.1, 0);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

//public class MilkyPenguinEntityModel extends ChickenModel<MilkyPenguinEntity>
