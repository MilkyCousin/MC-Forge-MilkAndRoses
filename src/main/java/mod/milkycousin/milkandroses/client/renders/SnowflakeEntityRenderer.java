package mod.milkycousin.milkandroses.client.renders;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.milkycousin.milkandroses.client.models.MilkyPenguinEntityModel;
import mod.milkycousin.milkandroses.client.models.SnowflakeEntityModel;
import mod.milkycousin.milkandroses.common.entities.living.MilkyPenguinEntity;
import mod.milkycousin.milkandroses.common.entities.living.SnowflakeEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SnowflakeEntityRenderer extends MobRenderer<SnowflakeEntity, SnowflakeEntityModel>
{
    private final ResourceLocation SNOWFLAKE_TEXTURE = new ResourceLocation("milkandroses:textures/entity/snowflake/snowflake.png");

    public static class RenderFactory implements IRenderFactory<SnowflakeEntity>
    {
        @Override
        public EntityRenderer<? super SnowflakeEntity> createRenderFor(EntityRendererManager manager)
        {
            return new SnowflakeEntityRenderer(manager);
        }
    }

    public SnowflakeEntityRenderer(EntityRendererManager rendererManager)
    {
        super(rendererManager, new SnowflakeEntityModel(), 0.3F);
    }

    @Override
    protected void preRenderCallback(SnowflakeEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime)
    {
        this.shadowSize = 0.5F;
    }

    @Override
    public ResourceLocation getEntityTexture(SnowflakeEntity entity)
    {
        return SNOWFLAKE_TEXTURE;
    }
}