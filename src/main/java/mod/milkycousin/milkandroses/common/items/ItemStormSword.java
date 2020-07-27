package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ItemStormSword extends SwordItem
{
    public ItemStormSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
    {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.setActiveHand(handIn);
        ItemStack currentItem = playerIn.getHeldItem(handIn);

        List<LivingEntity> list = worldIn.getEntitiesWithinAABB(
                LivingEntity.class,
                playerIn.getBoundingBox().grow(3.0D),
                (var) -> {
                    return var != null && var.isAlive();
                });

        playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 0.75F);
        currentItem.damageItem(2, playerIn, (var) -> {var.sendBreakAnimation(handIn);});
        playerIn.getCooldownTracker().setCooldown(currentItem.getItem(), 48);

        if(!list.isEmpty())
        {
            for (LivingEntity entity : list)
            {
                entity.move(MoverType.SHULKER_BOX, new Vec3d(0, 0, 0));
            }
        }

        return ActionResult.resultSuccess(currentItem);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }
}
