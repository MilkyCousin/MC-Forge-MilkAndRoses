package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class ItemManaVessel extends ItemWaterVessel
{
    public ItemManaVessel(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;

        if (playerentity instanceof ServerPlayerEntity)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }

        if (!worldIn.isRemote)
        {
            entityLiving.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 256, 1));
            entityLiving.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 512, 1));
        }

        if (playerentity != null)
        {
            playerentity.addStat(Stats.ITEM_USED.get(this));
            if (!playerentity.abilities.isCreativeMode)
            {
                stack.shrink(1);
            }
        }

        if (playerentity == null || !playerentity.abilities.isCreativeMode)
        {
            if (stack.isEmpty())
            {
                return new ItemStack(ModEventSubscriber.ModItems.VESSEL_EMPTY.get());
            }

            if (playerentity != null)
            {
                if(!playerentity.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_EMPTY.get())))
                {
                    playerentity.dropItem(new ItemStack(ModEventSubscriber.ModItems.VESSEL_EMPTY.get()), false);
                }
            }
        }

        return stack;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.setActiveHand(handIn);
        ItemStack currentItem = playerIn.getHeldItem(handIn);

        List<AreaEffectCloudEntity> list = worldIn.getEntitiesWithinAABB(
                AreaEffectCloudEntity.class,
                playerIn.getBoundingBox().grow(2.0D),
                (var) -> {
                    return var != null && var.isAlive() && var.getOwner() instanceof EnderDragonEntity;
                });

        if(!list.isEmpty())
        {
            AreaEffectCloudEntity areaEffectCloudEntity = list.get(0);
            areaEffectCloudEntity.setRadius(areaEffectCloudEntity.getRadius() - 0.5F);
            currentItem.shrink(1);
            if(!playerIn.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_ACID.get())))
            {
                playerIn.dropItem(new ItemStack(ModEventSubscriber.ModItems.VESSEL_ACID.get()), false);
            }
        }

        return ActionResult.resultSuccess(currentItem);
    }

}
