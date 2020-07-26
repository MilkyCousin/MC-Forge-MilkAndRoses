package mod.milkycousin.milkandroses.common.gui;

import mod.milkycousin.milkandroses.ModEventSubscriber;
import mod.milkycousin.milkandroses.common.AlchemicalBenchRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Optional;

public class AlchemicalBenchContainer extends Container
{
    private PlayerEntity playerEntity;
    private IInventory manaInv;
    private IItemHandler playerInventory;
    private final CraftingInventory craftingInventory = new CraftingInventory(this, 3, 3);
    private final CraftResultInventory craftResultInventory = new CraftResultInventory();
    private final IWorldPosCallable posCallable;

    public AlchemicalBenchContainer(int windowId, PlayerInventory playerInventoryIn, IInventory inventoryIn, IWorldPosCallable posCallableIn)
    {
        super(ModEventSubscriber.ModContainers.ALCHEMICAL_BENCH_CONTAINER.get(), windowId);
        this.manaInv = inventoryIn;
        this.playerInventory = new InvWrapper(playerInventoryIn);
        this.playerEntity = playerInventoryIn.player;
        this.posCallable = posCallableIn;

        layoutPlayerInventorySlots(8, 84);
        layoutBlockInventorySlots(45, 17);


    }

    public AlchemicalBenchContainer(int windowId, PlayerInventory playerInventoryIn)
    {
        this(windowId, playerInventoryIn, new Inventory(1), IWorldPosCallable.DUMMY);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.manaInv.isUsableByPlayer(playerIn);
    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutBlockInventorySlots(int leftCol, int topRow)
    {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftingInventory, j + i * 3, leftCol + j * 18, topRow + i * 18));
            }
        }

        this.addSlot(new CraftingResultSlot(this.playerEntity, this.craftingInventory, this.craftResultInventory, 9, 139, 35));
        this.addSlot(new Slot(this.manaInv, 0, 16, 34));
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);
    }

    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        this.posCallable.consume((var1, var2) -> {
            this.clearContainer(playerIn, var1, this.craftingInventory);
        });
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        System.out.println(index);
        if(slot != null && slot.getHasStack())
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if(index == 45)
            {
                this.posCallable.consume((p_217067_2_, p_217067_3_) -> {
                    itemStack1.getItem().onCreated(itemStack1, p_217067_2_, playerIn);
                });
                if (!this.mergeItemStack(itemStack1, 0, 35, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if(index >= 36 && index <= 46)
            {
                if (!this.mergeItemStack(itemStack1, 0, 35, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemStack1, 36, 47, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemStack1);
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }
        return itemStack;
    }

    public int getOutputSlot() {
        return 45;
    }

    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != this.craftResultInventory && super.canMergeSlot(stack, slotIn);
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSize() {
        return 11;
    }
}
