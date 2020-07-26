package mod.milkycousin.milkandroses.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class ItemTeleportationEye extends Item
{
    public ItemTeleportationEye(Properties properties)
    {
        super(properties.setNoRepair());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack currentItem = playerIn.getHeldItem(handIn);

        currentItem.damageItem(1, playerIn, (var) -> {var.sendBreakAnimation(handIn);});

        playerIn.getCooldownTracker().setCooldown(currentItem.getItem(), 64);

        final Vec3d toTeleport = new Vec3d(
                playerIn.getBedPosition().isPresent() ? playerIn.getBedLocation(DimensionType.OVERWORLD) : worldIn.getSpawnPoint()
                );

        final double toTeleportX = toTeleport.getX();
        final double toTeleportY = toTeleport.getY();
        final double toTeleportZ = toTeleport.getZ();

        playerIn.fallDistance = 0.0F;

        if(playerIn.dimension != DimensionType.OVERWORLD)
        {
            playerIn.changeDimension(
                    DimensionType.OVERWORLD,
                    new ITeleporter() {
                        @Override
                        public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
                        {
                            Entity teleportedEnitity = repositionEntity.apply(false);
                            teleportedEnitity.setPositionAndUpdate(
                                    toTeleportX,
                                    toTeleportY,
                                    toTeleportZ
                            );
                            return teleportedEnitity;
                        }
                    }
            );
        }
        else
        {
            playerIn.setPositionAndUpdate(
                    toTeleportX,
                    toTeleportY,
                    toTeleportZ
            );
        }

        return ActionResult.resultSuccess(currentItem);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return ActionResultType.PASS;
    }
}
