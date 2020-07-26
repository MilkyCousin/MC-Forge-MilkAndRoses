package mod.milkycousin.milkandroses.common.gui;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class AlchemicalBenchTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{

    public AlchemicalBenchTileEntity()
    {
        super(ModEventSubscriber.ModTileEntities.ALCHEMICAL_BENCH_TILE_ENTITY.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(ModEventSubscriber.ModBlocks.ALCHEMICAL_WORKBENCH.get().getRegistryName().toString());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new AlchemicalBenchContainer(p_createMenu_1_, p_createMenu_2_);
    }

    @Override
    public void tick()
    {

    }
}
