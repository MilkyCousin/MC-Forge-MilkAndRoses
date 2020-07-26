package mod.milkycousin.milkandroses.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.milkycousin.milkandroses.client.models.MilkyPenguinEntityModel;
import mod.milkycousin.milkandroses.common.entities.living.MilkyPenguinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class MilkyPenguinEntityRenderer extends MobRenderer<MilkyPenguinEntity, MilkyPenguinEntityModel>
{
    private final ResourceLocation PENGUIN_TEXTURE = new ResourceLocation("milkandroses:textures/entity/penguin/penguin.png");

    public static class RenderFactory implements IRenderFactory<MilkyPenguinEntity>
    {
        @Override
        public EntityRenderer<? super MilkyPenguinEntity> createRenderFor(EntityRendererManager manager)
        {
            return new MilkyPenguinEntityRenderer(manager);
        }
    }

    public MilkyPenguinEntityRenderer(EntityRendererManager rendererManager)
    {
        super(rendererManager, new MilkyPenguinEntityModel(), 0.3F);
    }

    @Override
    protected void preRenderCallback(MilkyPenguinEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime)
    {
        float numeric = 1.0F;
        if(entitylivingbaseIn.isChild())
        {
            numeric = (float)((double)numeric * 0.5D);
            this.shadowSize = 0.15F;
        }
        else
        {
            this.shadowSize = 0.3F;
        }

        matrixStackIn.scale(numeric, numeric, numeric);
    }

    @Override
    public ResourceLocation getEntityTexture(MilkyPenguinEntity entity)
    {
        return PENGUIN_TEXTURE;
    }
}
