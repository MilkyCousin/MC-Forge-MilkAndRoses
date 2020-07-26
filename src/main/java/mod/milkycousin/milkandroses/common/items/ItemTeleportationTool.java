package mod.milkycousin.milkandroses.common.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class ItemTeleportationTool extends Item
{
    public ItemTeleportationTool(Properties properties)
    {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;

        ArrayList<DimensionType> dimensionTypes = new ArrayList<>();

        for(DimensionType dimensionType: DimensionType.getAll())
        {
            dimensionTypes.add(dimensionType);
        }
        dimensionTypes.remove(playerEntity.world.getDimension().getType());

        System.out.println(!worldIn.isRemote);
        if(!worldIn.isRemote)
        {
            final BlockPos posTarget = playerEntity.getPosition();

            playerEntity.addPotionEffect(new EffectInstance(Effects.LEVITATION, 64, 0));
            playerEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 64, 0));

            int num = MathHelper.nextInt(new Random(), 0, dimensionTypes.size()-1);

            System.out.println(num);
            playerEntity.changeDimension(
                    dimensionTypes.get(num),
                    new ITeleporter() {
                        // Works occasionally, one day will fix that
                        @Override
                        public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
                        {
                            Entity newRepositionEntity = repositionEntity.apply(false);
                            newRepositionEntity.setPositionAndUpdate(posTarget.getX(), posTarget.getY(), posTarget.getZ());
                            return newRepositionEntity;
                        }
                    }
            );
        }

        if (playerEntity != null)
        {
            playerEntity.addStat(Stats.ITEM_USED.get(this));
        }

        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 32;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        return ActionResultType.PASS;
    }
}
