package mod.milkycousin.milkandroses.common.items;

import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemTeleportationScepter extends Item
{
    public ItemTeleportationScepter(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack currentItem = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote())
        {
            currentItem.damageItem(1, playerIn, (var) -> {var.sendBreakAnimation(handIn);});
            playerIn.getCooldownTracker().setCooldown(currentItem.getItem(), 32);
            EnderPearlEntity teleportProjectile = new EnderPearlEntity(worldIn, playerIn);
            teleportProjectile.setItem(new ItemStack(Items.ENDER_EYE));
            teleportProjectile.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.5F, 0.0F);
            teleportProjectile.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 0.4F, 1.0F);
            worldIn.addEntity(teleportProjectile);
        }
        return ActionResult.resultSuccess(currentItem);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return ActionResultType.PASS;
    }
}
