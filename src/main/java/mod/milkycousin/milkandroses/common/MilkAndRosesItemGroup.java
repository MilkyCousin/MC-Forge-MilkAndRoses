package mod.milkycousin.milkandroses.common;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mod.milkycousin.milkandroses.MilkAndRoses.MODID;

public class MilkAndRosesItemGroup
{
    // controversial indexing method
    public static final ItemGroup MILK_AND_ROSES_GROUP = (new ItemGroup(ItemGroup.getGroupCountSafe(), MODID)
    {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModEventSubscriber.ModItems.VESSEL_MILK.get());
        }

        @Override
        public void fill(NonNullList<ItemStack> items)
        {
            super.fill(items);
            items.sort((item1, item2) -> item1.getTranslationKey().compareTo(item2.getTranslationKey()));
        }
    });
    
    /**
    @Override
    public ItemStack createIcon() 
    {
        return new ItemStack(ModEventSubscriber.ModItems.VESSEL_MILK.get());
    }
    
    public MilkAndRosesItemGroup(String labelIn)
    {
        super(labelIn);
    }

    public MilkAndRosesItemGroup(int idIn, String labelIn)
    {
        super(idIn, labelIn);
    }
    **/
}
