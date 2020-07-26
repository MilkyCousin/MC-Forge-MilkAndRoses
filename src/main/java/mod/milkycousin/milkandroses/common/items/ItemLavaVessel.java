package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class ItemLavaVessel extends ItemWaterVessel
{
    public ItemLavaVessel(Properties properties)
    {
        super(properties);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
        EffectInstance effectInstance = new EffectInstance(Effects.INSTANT_DAMAGE);

        if (playerentity instanceof ServerPlayerEntity)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }

        if (!worldIn.isRemote)
        {
            effectInstance.getPotion().affectEntity(playerentity, playerentity, entityLiving, 2, 1.0D);
            entityLiving.setFire(2048);
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

            if(playerentity != null)
            {
                if(!playerentity.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_EMPTY.get())))
                {
                    playerentity.dropItem(new ItemStack(ModEventSubscriber.ModItems.VESSEL_EMPTY.get()), false);
                }
            }
        }

        return stack;
    }
}
