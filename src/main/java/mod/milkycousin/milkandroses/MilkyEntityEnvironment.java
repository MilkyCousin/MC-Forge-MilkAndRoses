package mod.milkycousin.milkandroses;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Optional;

public class MilkyEntityEnvironment
{
    public static void addEntitiesToBiomes()
    {
        for(Biome biome: ForgeRegistries.BIOMES.getValues())
        {
            final List<Biome.SpawnListEntry> spawnListEntryList = biome.getSpawns(EntityClassification.CREATURE);
            if(biome == Biomes.FROZEN_OCEAN || biome == Biomes.DEEP_FROZEN_OCEAN)
            {
                final Optional<Biome.SpawnListEntry> currentOptional = spawnListEntryList.stream()
                        .filter((var) -> var.entityType == ModEventSubscriber.ModEntities.PENGUIN.get())
                        .findFirst();

                if(currentOptional.isPresent())
                {
                    spawnListEntryList.remove(currentOptional);
                }

                spawnListEntryList.add(
                        new Biome.SpawnListEntry(
                                ModEventSubscriber.ModEntities.PENGUIN.get(), 100, 1, 5
                        )
                );
            }
            else
            {
                continue;
            }
        }
    }
}
