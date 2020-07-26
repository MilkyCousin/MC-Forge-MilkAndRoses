package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.List;

public class ItemInertPenguinGem extends Item
{
    public ItemInertPenguinGem(Properties properties)
    {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.setActiveHand(handIn);
        ItemStack currentItem = playerIn.getHeldItem(handIn);

        List<EnderCrystalEntity> list = worldIn.getEntitiesWithinAABB(
                EnderCrystalEntity.class,
                playerIn.getBoundingBox().grow(1.0D),
                (var) -> {
                    return var != null && var.isAlive() && var.isAddedToWorld();
                }
        );

        if(!list.isEmpty())
        {
            EnderCrystalEntity enderCrystalEntity = list.get(0);

            currentItem.shrink(1);
            enderCrystalEntity.remove();

            worldIn.createExplosion(null, enderCrystalEntity.getPosX(), enderCrystalEntity.getPosY(), enderCrystalEntity.getPosZ(), 7.0F, Explosion.Mode.DESTROY);
            worldIn.createExplosion(null, enderCrystalEntity.getPosX(), enderCrystalEntity.getPosY(), enderCrystalEntity.getPosZ(), 9.0F, Explosion.Mode.DESTROY);
            worldIn.createExplosion(null, enderCrystalEntity.getPosX(), enderCrystalEntity.getPosY(), enderCrystalEntity.getPosZ(), 9.0F, Explosion.Mode.BREAK);

            if(!playerIn.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.AWAKENED_PENGUIN_GEM.get())))
            {
                playerIn.dropItem(new ItemStack(ModEventSubscriber.ModItems.AWAKENED_PENGUIN_GEM.get()), false);
            }
        }

        return ActionResult.resultSuccess(currentItem);
    }
}
