package mod.milkycousin.milkandroses.common.features.structures;

import com.mojang.datafixers.Dynamic;
import mod.milkycousin.milkandroses.common.features.pieces.StructureEndTowerPiece;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.function.Function;

//public class StructureEndTower extends ScatteredStructure<NoFeatureConfig>
public class StructureEndTower
{
    /**
    public StructureEndTower(Function<Dynamic<?>, ? extends NoFeatureConfig> factoryIn)
    {
        super(factoryIn);
    }

    public StructureEndTower()
    {
        this(NoFeatureConfig::deserialize);
    }

    public String getStructureName() {
        return "End_Tall_Tower";
    }

    public int getSize() {
        return 1;
    }

    public Structure.IStartFactory getStartFactory() {
        return StructureEndTower.Start::new;
    }

    protected int getSeedModifier()
    {
        return 15491257;
    }

    public static class Start extends StructureStart
    {
        public Start(Structure<?> p_i225807_1_, int p_i225807_2_, int p_i225807_3_, MutableBoundingBox p_i225807_4_, int p_i225807_5_, long p_i225807_6_)
        {
            super(p_i225807_1_, p_i225807_2_, p_i225807_3_, p_i225807_4_, p_i225807_5_, p_i225807_6_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
        {
            StructureEndTowerPiece towerPiece = new StructureEndTowerPiece(this.rand, chunkX * 16, chunkZ * 16);
            this.components.add(towerPiece);
            this.recalculateStructureSize();
        }
    }
     **/
}
