package mod.milkycousin.milkandroses.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.milkycousin.milkandroses.common.entities.living.SnowflakeEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowflakeEntityModel  extends EntityModel<SnowflakeEntity>
{
    private final ModelRenderer body;
    private final ModelRenderer first_flake;
    private final ModelRenderer second_flake;

    public SnowflakeEntityModel()
    {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(2.0F, 15.0F, -2.0F);
        body.setTextureOffset(0, 0).addBox(-5.0F, -3.0F, -1.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
        body.setTextureOffset(12, 28).addBox(-6.0F, -2.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        body.setTextureOffset(26, 26).addBox(1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        body.setTextureOffset(24, 21).addBox(-4.0F, 3.0F, 0.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        body.setTextureOffset(0, 24).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

        first_flake = new ModelRenderer(this);
        first_flake.setRotationPoint(0.0F, 22.0F, 0.0F);
        first_flake.setTextureOffset(26, 13).addBox(-6.0F, 1.0F, 2.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        first_flake.setTextureOffset(18, 0).addBox(-8.0F, 0.0F, 1.0F, 6.0F, 1.0F, 4.0F, 0.0F, false);
        first_flake.setTextureOffset(18, 6).addBox(-10.0F, -1.0F, 0.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        first_flake.setTextureOffset(14, 14).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);

        second_flake = new ModelRenderer(this);
        second_flake.setRotationPoint(0.0F, 7.0F, 0.0F);
        second_flake.setTextureOffset(0, 17).addBox(-6.0F, 1.0F, -2.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        second_flake.setTextureOffset(12, 21).addBox(1.0F, 1.0F, -2.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);
        second_flake.setTextureOffset(0, 12).addBox(-4.0F, 0.0F, -1.0F, 6.0F, 1.0F, 4.0F, 0.0F, false);
        second_flake.setTextureOffset(12, 17).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        first_flake.render(matrixStack, buffer, packedLight, packedOverlay);
        second_flake.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setRotationAngles(SnowflakeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.first_flake.rotateAngleY = ageInTicks;
        this.second_flake.rotateAngleY = ageInTicks;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
