package mod.milkycousin.milkandroses;

import mod.milkycousin.milkandroses.common.AlchemicalBenchRecipe;
import mod.milkycousin.milkandroses.common.MilkyArmorMaterial;
import mod.milkycousin.milkandroses.common.MilkyTier;
import mod.milkycousin.milkandroses.common.blocks.AlchemicalBenchBlock;
import mod.milkycousin.milkandroses.common.blocks.MilkyOreBlock;
import mod.milkycousin.milkandroses.common.blocks.MineralOreBlock;
import mod.milkycousin.milkandroses.common.blocks.OnyxOreBlock;
import mod.milkycousin.milkandroses.common.entities.item.BulletEntity;
import mod.milkycousin.milkandroses.common.entities.item.ThrownOrbEntity;
import mod.milkycousin.milkandroses.common.entities.living.MilkyPenguinEntity;
import mod.milkycousin.milkandroses.common.entities.living.SnowflakeEntity;
import mod.milkycousin.milkandroses.common.gui.AlchemicalBenchContainer;
import mod.milkycousin.milkandroses.common.gui.AlchemicalBenchTileEntity;
import mod.milkycousin.milkandroses.common.MilkAndRosesItemGroup;
import mod.milkycousin.milkandroses.common.items.*;
import mod.milkycousin.milkandroses.common.features.structures.StructureEndTower;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEventSubscriber
{
    public static final class Sounds
    {
        static final DeferredRegister<SoundEvent> SOUNDS = new DeferredRegister<>(ForgeRegistries.SOUND_EVENTS, MilkAndRoses.MODID);

        public static final RegistryObject<SoundEvent> PENGUIN_AMBIENT_SOUND = SOUNDS.register(
                "penguin_ambient",
                () -> new SoundEvent(new ResourceLocation("milkandroses", "penguin_ambient"))
                );
    }

    @SuppressWarnings("deprecation")
    public static final class ModEntities
    {
        static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, MilkAndRoses.MODID);

        // omg i love penguins
        public static final RegistryObject<EntityType<MilkyPenguinEntity>> PENGUIN = ENTITIES.register(
                "penguin",
                () -> EntityType.Builder.create(MilkyPenguinEntity::new, EntityClassification.CREATURE).size(0.625F, 0.9375F).build("")
        );

        public static final RegistryObject<EntityType<SnowflakeEntity>> SNOWFLAKE = ENTITIES.register(
                "snowflake",
                () -> EntityType.Builder.create(SnowflakeEntity::new, EntityClassification.MONSTER).size(1.5F, 1.5F).build("")
        );

        public static final RegistryObject<EntityType<ThrownOrbEntity>> THROWN_BATTLE_ORB = ENTITIES.register(
                "thrown_battle_orb",
                () -> EntityType.Builder.<ThrownOrbEntity>create(ThrownOrbEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build("")
        );

        public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY = ENTITIES.register(
                "bullet_entity",
                () -> EntityType.Builder.<BulletEntity>create(BulletEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build("")
        );
    }

    public static final class ModBlocks
    {
        static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MilkAndRoses.MODID);

        // Ores
        public static final RegistryObject<Block> PURPLE_SHARD_ORE = BLOCKS.register("purple_shard_ore", () -> new MilkyOreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4, 3.5F)));
        public static final RegistryObject<Block> ONYX_ORE = BLOCKS.register("onyx_ore", () -> new OnyxOreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(60, 1400)));
        public static final RegistryObject<Block> MINERAL_ORE = BLOCKS.register("mineral_ore", () -> new MineralOreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(512, 5000)));

        // Machinery TODO -> fix bugs
        public static final RegistryObject<Block> ALCHEMICAL_WORKBENCH = BLOCKS.register("alchemibench", () -> new AlchemicalBenchBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.5F, 7).harvestLevel(3)));

        // Dimension
        public static final RegistryObject<Block> PORTAL_BLOCK = BLOCKS.register("dim_portal", () -> new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(7.5F, 1600).harvestLevel(3)));

        public static final RegistryObject<Block> GRASS_BLOCK = BLOCKS.register("dim_natural_grass", () -> new Block(Block.Properties.create(Material.LEAVES).hardnessAndResistance(1.5F, 4).harvestLevel(3)));
        public static final RegistryObject<Block> DIRT_BLOCK = BLOCKS.register("dim_natural_dirt", () -> new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(1.5F, 4).harvestLevel(3)));
        public static final RegistryObject<Block> STONE_BLOCK = BLOCKS.register("dim_natural_stone", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F, 6).harvestLevel(3)));
        public static final RegistryObject<Block> STONE_BRICKS_BLOCK = BLOCKS.register("dim_natural_stone_bricks", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F, 6).harvestLevel(3)));
    }

    public static final class ModContainers
    {
        static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, MilkAndRoses.MODID);

        public static final RegistryObject<ContainerType<AlchemicalBenchContainer>> ALCHEMICAL_BENCH_CONTAINER = CONTAINERS.register(
                ModBlocks.ALCHEMICAL_WORKBENCH.getId().getPath(),
                () -> new ContainerType<>(AlchemicalBenchContainer::new)
        );
    }

    public static final class ModTileEntities
    {
        static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MilkAndRoses.MODID);

        public static final RegistryObject<TileEntityType<AlchemicalBenchTileEntity>> ALCHEMICAL_BENCH_TILE_ENTITY = TILE_ENTITIES.register(
                ModBlocks.ALCHEMICAL_WORKBENCH.getId().getPath(),
                () -> TileEntityType.Builder.create(AlchemicalBenchTileEntity::new, ModBlocks.ALCHEMICAL_WORKBENCH.get()).build(null)
        );
    }

    public static final class ModEffects
    {
        static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, MilkAndRoses.MODID);

    }

    public static final class ModItems
    {
        static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MilkAndRoses.MODID);

        // BlockItem objects
        public static final RegistryObject<Item> PURPLE_SHARD_ORE = ITEMS.register("purple_shard_ore", () -> new BlockItem(ModBlocks.PURPLE_SHARD_ORE.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> ONYX_ORE = ITEMS.register("onyx_ore", () -> new BlockItem(ModBlocks.ONYX_ORE.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> MINERAL_ORE = ITEMS.register("mineral_ore", () -> new BlockItem(ModBlocks.MINERAL_ORE.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> ALCHEMICAL_WORKBENCH = ITEMS.register("alchemibench", () -> new BlockItem(ModBlocks.ALCHEMICAL_WORKBENCH.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> PORTAL_BLOCK = ITEMS.register("dim_portal", () -> new BlockItem(ModBlocks.PORTAL_BLOCK.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> GRASS_BLOCK = ITEMS.register("dim_natural_grass", () -> new BlockItem(ModBlocks.GRASS_BLOCK.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> DIRT_BLOCK = ITEMS.register("dim_natural_dirt", () -> new BlockItem(ModBlocks.DIRT_BLOCK.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> STONE_BLOCK = ITEMS.register("dim_natural_stone", () -> new BlockItem(ModBlocks.STONE_BLOCK.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> STONE_BRICKS_BLOCK = ITEMS.register("dim_natural_stone_bricks", () -> new BlockItem(ModBlocks.STONE_BRICKS_BLOCK.get(), new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Milky items
        public static final RegistryObject<Item> MILKY_ITEM = ITEMS.register("milky_item", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Crystals, gems
        public static final RegistryObject<Item> PURPLE_SHARD = ITEMS.register("purple_shard", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> ONYX_GEM = ITEMS.register("onyx_gem", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> BEDROCK_MINERAL = ITEMS.register("mineral_gem", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> DRAGON_GEM = ITEMS.register("draconic_stone", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> WATER_GEM = ITEMS.register("water_gem", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> FIRE_GEM = ITEMS.register("fire_gem", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> INERT_PENGUIN_GEM = ITEMS.register("inert_penguin_gem", () -> new ItemInertPenguinGem(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> AWAKENED_PENGUIN_GEM = ITEMS.register("awakened_penguin_gem", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Metals
        public static final RegistryObject<Item> BRASS_INGOT = ITEMS.register("ingot_brass", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> MAGIC_INGOT = ITEMS.register("ingot_magic", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Powders
        public static final RegistryObject<Item> POWDER_MANA_WEAK = ITEMS.register("powder_weak_mana", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> POWDER_MANA_STRONG = ITEMS.register("powder_strong_mana", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Particles
        public static final RegistryObject<Item> VOID_PARTICLE = ITEMS.register("ender_particle", () -> new Item(new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Vessels TODO -> code refactor
        public static final RegistryObject<Item> VESSEL_EMPTY = ITEMS.register("vessel_empty", () -> new ItemEmptyVessel(new Item.Properties().maxStackSize(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> VESSEL_MILK = ITEMS.register("vessel_milk", () -> new ItemMilkVessel(new Item.Properties().maxStackSize(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> VESSEL_WATER = ITEMS.register("vessel_water", () -> new ItemWaterVessel(new Item.Properties().maxStackSize(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> VESSEL_LAVA = ITEMS.register("vessel_lava", () -> new ItemLavaVessel(new Item.Properties().maxStackSize(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> VESSEL_MANA = ITEMS.register("vessel_mana", () -> new ItemManaVessel(new Item.Properties().maxStackSize(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> VESSEL_ACID = ITEMS.register("vessel_draconic_acid", () -> new ItemDraconicAcidVessel(new Item.Properties().maxStackSize(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> VESSEL_BIG_EMPTY = ITEMS.register("vessel_big_empty", () -> new Item(new Item.Properties().maxStackSize(4).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> VESSEL_BIG_MILK = ITEMS.register("vessel_big_mana", () -> new ItemManaBigVessel(new Item.Properties().maxStackSize(4).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Teleportation based items
        public static final RegistryObject<Item> TELEPORTATION_EYE = ITEMS.register("teleportation_eye", () -> new ItemTeleportationEye(new Item.Properties().maxDamage(16).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> TELEPORTATION_SCEPTER = ITEMS.register("teleportation_scepter", () -> new ItemTeleportationScepter(new Item.Properties().maxDamage(200).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> TELEPORTATION_TOOL = ITEMS.register("teleportation_tool", () -> new ItemTeleportationTool(new Item.Properties().maxStackSize(1).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Upgrades
        public static final RegistryObject<Item> UPGRADE_NULL = ITEMS.register("upgrade_null", () -> new Item(new Item.Properties().maxStackSize(1).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> UPGRADE_ONYX = ITEMS.register("upgrade_onyx", () -> new Item(new Item.Properties().maxStackSize(1).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Tools
        public static final RegistryObject<Item> MILKY_SWORD = ITEMS.register("milky_sword", () -> new SwordItem(MilkyTier.PURPLE, 13, 0.5F, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> STORM_SWORD = ITEMS.register("storm_sword", () -> new ItemStormSword(MilkyTier.MINERAL, 6, 1.5F, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> ONYX_AXE = ITEMS.register("onyx_axe", () -> new AxeItem(MilkyTier.ONYX, 6, 0.25F, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> ONYX_PICKAXE = ITEMS.register("onyx_pickaxe", () -> new PickaxeItem(MilkyTier.ONYX, 5, 0.25F, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> ONYX_HOE = ITEMS.register("onyx_hoe", () -> new HoeItem(MilkyTier.ONYX, 0.25F, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> ONYX_SHOVEL = ITEMS.register("onyx_shovel", () -> new ShovelItem(MilkyTier.ONYX, 4, 0.25F, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> YOYO_BASE = ITEMS.register("battle_item_1", () -> new ItemBattleOrb(new Item.Properties().maxDamage(250).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP), 5.0F));
        public static final RegistryObject<Item> YOYO_ADVANCED = ITEMS.register("battle_item_2", () -> new ItemBattleOrb(new Item.Properties().maxDamage(500).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP), 8.0F));
        public static final RegistryObject<Item> YOYO_MASTER = ITEMS.register("battle_item_3", () -> new ItemBattleOrb(new Item.Properties().maxDamage(750).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP), 12.0F));

        public static final RegistryObject<Item> ROSE_AMULET = ITEMS.register("rose_amulet", () -> new ItemBeautifulRose(new Item.Properties().maxDamage(10).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        public static final RegistryObject<Item> QUEEN_OF_FIRE = ITEMS.register("queen_of_fire", () -> new QueenRangedItem(new Item.Properties().maxDamage(500).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP), 10, Items.MAGMA_BLOCK.getDefaultInstance(), null)); // ModEffects.BURNING_EFFECT.get()
        public static final RegistryObject<Item> QUEEN_OF_ICE = ITEMS.register("queen_of_ice", () -> new QueenRangedItem(new Item.Properties().maxDamage(500).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP), 10, Items.ICE.getDefaultInstance(), new EffectInstance(Effects.SLOWNESS, 128, 4)));

        // Shields
        public static final RegistryObject<Item> PENGUIN_SHIELD = ITEMS.register("penguin_shield", () -> new MilkyShield(new Item.Properties().maxDamage(1250).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Armor
        public static final RegistryObject<Item> PENGUIN_HELMET = ITEMS.register("penguin_helmet", () -> new ArmorItem(MilkyArmorMaterial.DRAGON_GEM, EquipmentSlotType.HEAD, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> PENGUIN_CHESTPLATE = ITEMS.register("penguin_chestplate", () -> new ArmorItem(MilkyArmorMaterial.DRAGON_GEM, EquipmentSlotType.CHEST, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> PENGUIN_LEGGINGS = ITEMS.register("penguin_leggings", () -> new ArmorItem(MilkyArmorMaterial.DRAGON_GEM, EquipmentSlotType.LEGS, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
        public static final RegistryObject<Item> PENGUIN_BOOTS = ITEMS.register("penguin_boots", () -> new ArmorItem(MilkyArmorMaterial.DRAGON_GEM, EquipmentSlotType.FEET, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Dimension
        public static final RegistryObject<Item> DIMENSION_ACTIVATOR = ITEMS.register("dim_activator", () -> new Item(new Item.Properties().maxStackSize(1).group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));

        // Spawning Eggs
        public static final RegistryObject<Item> PENGUIN_SPAWN_EGG = ITEMS.register("penguin_spawn_egg", () -> new ItemMilkySpawnEgg(ModEntities.PENGUIN, 5117611, 16763136, new Item.Properties().group(MilkAndRosesItemGroup.MILK_AND_ROSES_GROUP)));
    }

    public static final class ModSerializers
    {
        static final DeferredRegister<IRecipeSerializer<?>> SERIALIZER = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, MilkAndRoses.MODID);

        public static final RegistryObject<AlchemicalBenchRecipe.Serializer> ALCHEMICAL_SERIALIZER = SERIALIZER.register("alchemical_recipes", () -> new AlchemicalBenchRecipe.Serializer());
    }

    public static final class ModStructures
    {
        static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, MilkAndRoses.MODID);

        //public static final RegistryObject<StructureEndTower> END_TALL_TOWER = FEATURES.register("end_tall_tower", () -> new StructureEndTower());
    }

    public static final class ModPaintings
    {
        static final DeferredRegister<PaintingType> PAINTINGS = new DeferredRegister<>(ForgeRegistries.PAINTING_TYPES, MilkAndRoses.MODID);

        public static final RegistryObject<PaintingType> PAINTING_CARS = PAINTINGS.register("painting_cars", () -> new PaintingType(64, 32));
        public static final RegistryObject<PaintingType> PAINTING_GOGH_STAR = PAINTINGS.register("painting_gogh_1", () -> new PaintingType(64, 32));
        public static final RegistryObject<PaintingType> PAINTING_GOGH_CITY = PAINTINGS.register("painting_gogh_2", () -> new PaintingType(64, 32));
        public static final RegistryObject<PaintingType> PAINTING_MATH = PAINTINGS.register("painting_math", () -> new PaintingType(96, 32));
        public static final RegistryObject<PaintingType> PAINTING_PENGUIN = PAINTINGS.register("painting_penguin", () -> new PaintingType(32, 32));
        public static final RegistryObject<PaintingType> PAINTING_PENGUIN_MATH = PAINTINGS.register("painting_penguin_math", () -> new PaintingType(32, 32));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerEntitiesEvent(final RegistryEvent.Register<EntityType<?>> registryEvent)
    {
        ItemMilkySpawnEgg.initUnaddedEggs();
    }
}