package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class ItemManaBigVessel extends ItemWaterVessel
{
    public ItemManaBigVessel(Properties properties)
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
            entityLiving.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 512, 2));
            entityLiving.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 1024, 4));
            entityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1024, 0));
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
                return new ItemStack(ModEventSubscriber.ModItems.VESSEL_BIG_EMPTY.get());
            }

            if (playerentity != null)
            {
                if(!playerentity.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_BIG_EMPTY.get())))
                {
                    playerentity.dropItem(new ItemStack(ModEventSubscriber.ModItems.VESSEL_BIG_EMPTY.get()), false);
                }
            }
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 64;
    }
}