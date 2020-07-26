package mod.milkycousin.milkandroses;

import mod.milkycousin.milkandroses.common.MilkyOreGen;
import mod.milkycousin.milkandroses.common.RendererHandler;
import mod.milkycousin.milkandroses.common.gui.AlchemicalBenchScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import other_stuff.calcMonteCarlo;

@Mod(MilkAndRoses.MODID)
public class MilkAndRoses
{
    public static final String MODID = "milkandroses";

    private static final Logger LOGGER = LogManager.getLogger(MODID);

    private void commandsInit(final FMLCommonSetupEvent setupEvent)
    {
        MilkyEntityEnvironment.addEntitiesToBiomes();
        MilkyOreGen.addMilkyOres();
        RendererHandler.handleEntityRenderers();

        ScreenManager.registerFactory(ModEventSubscriber.ModContainers.ALCHEMICAL_BENCH_CONTAINER.get(), AlchemicalBenchScreen::new);
    }

    public MilkAndRoses()
    {
        System.out.println("Main class of " + MODID + "has launched. Have some Monte-Carlo integration of sine function:");
        System.out.println((new calcMonteCarlo(1000)).integrate(Math::sin));

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEventSubscriber.ModEntities.ENTITIES.register(modEventBus);
        ModEventSubscriber.ModBlocks.BLOCKS.register(modEventBus);
        ModEventSubscriber.ModItems.ITEMS.register(modEventBus);
        ModEventSubscriber.ModContainers.CONTAINERS.register(modEventBus);
        ModEventSubscriber.ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModEventSubscriber.ModSerializers.SERIALIZER.register(modEventBus);
        ModEventSubscriber.Sounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(this::commandsInit);
    }
}