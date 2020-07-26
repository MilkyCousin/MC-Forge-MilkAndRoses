package mod.milkycousin.milkandroses.common.items;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ItemMilkySpawnEgg extends SpawnEggItem
{
    protected static final List<ItemMilkySpawnEgg> listOfEggs = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public ItemMilkySpawnEgg(Supplier<EntityType<?>> supplierTypeIn, int primaryColorIn, int secondaryColorIn, Properties builder)
    {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier = Lazy.of(supplierTypeIn::get);
        listOfEggs.add(this);
    }

    public ItemMilkySpawnEgg(RegistryObject<? extends EntityType<?>> supplierTypeIn, int primaryColorIn, int secondaryColorIn, Properties builder)
    {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.entityTypeSupplier = Lazy.of(supplierTypeIn);
        listOfEggs.add(this);
    }

    public static void initUnaddedEggs()
    {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior()
        {
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for (final SpawnEggItem egg : listOfEggs) {
            EGGS.put(egg.getType(null), egg);
            DispenserBlock.registerDispenseBehavior(egg, defaultDispenseItemBehavior);
            // ItemColors for each spawn egg don't need to be registered because this method is called before ItemColors is created
        }
        listOfEggs.clear();
    }

    @Override
    public EntityType<?> getType(@Nullable final CompoundNBT p_208076_1_)
    {
        return entityTypeSupplier.get();
    }
}
