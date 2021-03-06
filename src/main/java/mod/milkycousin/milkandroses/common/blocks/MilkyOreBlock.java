package mod.milkycousin.milkandroses.common.blocks;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

public class MilkyOreBlock extends OreBlock
{
    public MilkyOreBlock(Block.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        return new ItemStack(ModEventSubscriber.ModItems.PURPLE_SHARD_ORE.get());
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 3;
    }

    @Override
    protected int getExperience(Random rand)
    {
        return this == ModEventSubscriber.ModBlocks.PURPLE_SHARD_ORE.get() ? MathHelper.nextInt(rand, 5, 9) : 0;
    }
}
