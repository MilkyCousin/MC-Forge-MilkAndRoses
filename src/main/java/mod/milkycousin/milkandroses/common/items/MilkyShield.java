package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class MilkyShield extends ShieldItem
{
    public MilkyShield(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity)
    {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.fallDistance = 0;

        for(LivingEntity entity : worldIn.getEntitiesWithinAABB(
                LivingEntity.class,
                playerIn.getBoundingBox().grow(10.0D),
                (var) -> {
                    return var != null && var.isAlive() && !(var instanceof PlayerEntity);
                })
        )
        {
            entity.addPotionEffect(new EffectInstance(Effects.GLOWING, 128, 0));
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == ModEventSubscriber.ModItems.DRAGON_GEM.get();
    }
}
