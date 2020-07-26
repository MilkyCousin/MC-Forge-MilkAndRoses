package mod.milkycousin.milkandroses.common;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import mod.milkycousin.milkandroses.client.renders.MilkyPenguinEntityRenderer;
import mod.milkycousin.milkandroses.client.renders.SnowflakeEntityRenderer;
import mod.milkycousin.milkandroses.common.entities.item.BulletEntity;
import mod.milkycousin.milkandroses.common.entities.item.ThrownOrbEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RendererHandler
{
    public static void handleEntityRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(
                ModEventSubscriber.ModEntities.PENGUIN.get(),
                new MilkyPenguinEntityRenderer.RenderFactory()
        );
        RenderingRegistry.registerEntityRenderingHandler(
                ModEventSubscriber.ModEntities.SNOWFLAKE.get(),
                new SnowflakeEntityRenderer.RenderFactory()
        );
        RenderingRegistry.registerEntityRenderingHandler(
                ModEventSubscriber.ModEntities.THROWN_BATTLE_ORB.get(),
                new IRenderFactory<ThrownOrbEntity>() {
                    @Override
                    public EntityRenderer<? super ThrownOrbEntity> createRenderFor(EntityRendererManager manager) {
                        return new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer());
                    }
                }
        );
        RenderingRegistry.registerEntityRenderingHandler(
                ModEventSubscriber.ModEntities.BULLET_ENTITY.get(),
                new IRenderFactory<BulletEntity>() {
                    @Override
                    public EntityRenderer<? super BulletEntity> createRenderFor(EntityRendererManager manager) {
                        return new SpriteRenderer<>(manager, Minecraft.getInstance().getItemRenderer());
                    }
                }
        );
    }
}
