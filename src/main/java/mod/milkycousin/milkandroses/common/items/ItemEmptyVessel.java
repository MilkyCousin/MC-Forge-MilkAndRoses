package mod.milkycousin.milkandroses.common.items;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class ItemEmptyVessel extends Item
{
    public ItemEmptyVessel(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack currentItem = playerIn.getHeldItem(handIn);
        RayTraceResult rayTraceResult = this.rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);

        if(rayTraceResult.getType() == RayTraceResult.Type.MISS)
        {
           return ActionResult.resultPass(currentItem);
        }
        else if(rayTraceResult.getType() != RayTraceResult.Type.BLOCK)
        {
            return ActionResult.resultPass(currentItem);
        }
        else
        {
            Block currentStateBlock = worldIn.getBlockState(new BlockPos(rayTraceResult.getHitVec())).getBlock();
            if(currentStateBlock == Blocks.WATER)
            {
                playerIn.getHeldItem(handIn).shrink(1);
                if(!playerIn.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_WATER.get())))
                {
                    playerIn.dropItem(new ItemStack(ModEventSubscriber.ModItems.VESSEL_WATER.get()), false);
                }
                return ActionResult.resultSuccess(currentItem);
            }
            if(currentStateBlock == Blocks.LAVA)
            {
                playerIn.getHeldItem(handIn).shrink(1);
                if(!playerIn.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_LAVA.get())))
                {
                    playerIn.dropItem(new ItemStack(ModEventSubscriber.ModItems.VESSEL_LAVA.get()), false);
                }
                return ActionResult.resultSuccess(currentItem);
            }
        }

        return ActionResult.resultFail(currentItem);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        if(target.getEntity() instanceof CowEntity)
        {
            playerIn.getHeldItem(hand).shrink(1);
            playerIn.inventory.addItemStackToInventory(new ItemStack(ModEventSubscriber.ModItems.VESSEL_MILK.get()));
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
