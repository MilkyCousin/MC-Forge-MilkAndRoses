package mod.milkycousin.milkandroses.common;

import mod.milkycousin.milkandroses.MilkAndRoses;
import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MilkyOreGen
{
    private static final BlockState PURPLE_SHARD_ORE = ModEventSubscriber.ModBlocks.PURPLE_SHARD_ORE.get().getDefaultState();
    private static final BlockState ONYX_ORE = ModEventSubscriber.ModBlocks.ONYX_ORE.get().getDefaultState();
    private static final BlockState MINERAL_ORE = ModEventSubscriber.ModBlocks.MINERAL_ORE.get().getDefaultState();

    private static final Logger LOGGER = LogManager.getLogger(MilkAndRoses.MODID);

    public static void addMilkyOres()
    {
        for(Biome biome: ForgeRegistries.BIOMES.getValues())
        {
            if(biome.getCategory() == Biome.Category.THEEND)
            {
                biome.addFeature(
                        GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(
                                new OreFeatureConfig(
                                        OreFeatureConfig.FillerBlockType.create(
                                                "end_stone_enum",
                                                "end_stone_filler",
                                                (var) -> {
                                            if(var == null)
                                            {
                                                return false;
                                            }
                                            else
                                            {
                                                Block block = var.getBlock();
                                                return block == Blocks.END_STONE;
                                            }
                                        }),
                                        ONYX_ORE, 3
                                )
                        ).withPlacement(Placement.COUNT_RANGE.configure(
                                new CountRangeConfig(5, 0, 0, 30)
                        )));
            }
            else if(biome.getCategory() == Biome.Category.NETHER)
            {
                continue;
            }
            else
            {
                biome.addFeature(
                        GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(
                                new OreFeatureConfig(
                                        OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                        PURPLE_SHARD_ORE, 3
                                )
                        ).withPlacement(Placement.COUNT_RANGE.configure(
                                new CountRangeConfig(1, 0, 0, 12)
                        )));
                biome.addFeature(
                        GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(
                                new OreFeatureConfig(
                                        OreFeatureConfig.FillerBlockType.create(
                                                "bedrock_enum",
                                                "bedrock_filler",
                                                (var) -> {
                                                    if(var == null)
                                                    {
                                                        return false;
                                                    }
                                                    else
                                                    {
                                                        Block block = var.getBlock();
                                                        return block == Blocks.BEDROCK;
                                                    }
                                                }),
                                        MINERAL_ORE, 3
                                )
                        ).withPlacement(Placement.COUNT_RANGE.configure(
                                new CountRangeConfig(1, 0, 0, 7)
                        )));
            }
        }

        /**
        final BiomeManager.BiomeType[] biomeTypes = {
                BiomeManager.BiomeType.COOL,
                BiomeManager.BiomeType.DESERT,
                BiomeManager.BiomeType.ICY,
                BiomeManager.BiomeType.WARM
        };

        try {
            for(BiomeManager.BiomeType type: biomeTypes)
            {
                BiomeManager.getBiomes(type).forEach((BiomeManager.BiomeEntry entry) -> entry.biome.addFeature(
                        GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.withConfiguration(
                                new OreFeatureConfig(
                                        OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                        PURPLE_SHARD_ORE, 3
                                )
                        ).withPlacement(Placement.COUNT_RANGE.configure(
                                new CountRangeConfig(1, 0, 0, 12)
                        ))
                ));
            }
        }
        catch (NullPointerException err)
        {
            LOGGER.warn(Main.MODID + "\t" + "encountered:" + err); // maybe will change it later
        }
         **/
    }
}
