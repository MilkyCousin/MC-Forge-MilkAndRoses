package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemWaterVessel extends Item
{
    public ItemWaterVessel(Properties properties)
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

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 24;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.DRINK;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return ActionResultType.PASS;
    }
}
