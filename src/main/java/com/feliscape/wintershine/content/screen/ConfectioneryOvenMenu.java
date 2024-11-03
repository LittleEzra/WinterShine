package com.feliscape.wintershine.content.screen;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.block.entity.ConfectioneryOvenBlockEntity;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import com.feliscape.wintershine.registry.WinterShineMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ConfectioneryOvenMenu extends AbstractContainerMenu {
    protected static final int SLOT_FUEL = 9;
    protected static final int SLOT_RESULT = 10;

    public final ConfectioneryOvenBlockEntity blockEntity;
    public final ItemStackHandler inventory;
    private final Level level;
    private final ContainerData data;

    public ConfectioneryOvenMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerId, inv, getTileEntity(inv, extraData), new SimpleContainerData(4));
    }
    public ConfectioneryOvenMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData pData){
        super(WinterShineMenuTypes.CONFECTIONERY_OVEN_MENU.get(), pContainerId);
        checkContainerDataCount(pData, 4);
        blockEntity = ((ConfectioneryOvenBlockEntity) entity);
        inventory = blockEntity.getInventory();
        this.level = inv.player.level();
        this.data = pData;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        for(int slotY = 0; slotY < 3; ++slotY) {
            for(int slotX = 0; slotX < 3; ++slotX) {
                this.addSlot(new SlotItemHandler(inventory, slotX + slotY * 3, 26 + slotX * 18, 15 + slotY * 18));
            }
        }
        this.addSlot(new SlotItemHandler(inventory, SLOT_FUEL, 91, 58){ // Fuel Slot
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return isFuel(stack);
            }
        });
        this.addSlot(new SlotItemHandler(inventory, SLOT_RESULT, 124, 33){ // Result Slot
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
    }

    private static ConfectioneryOvenBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof ConfectioneryOvenBlockEntity) {
            return (ConfectioneryOvenBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, WinterShineBlocks.CONFECTIONERY_OVEN.get());
    }


    protected boolean isFuel(ItemStack pStack) {
        return pStack.getBurnTime(RecipeType.SMELTING) > 0;
    }

    public float getCookProgress() {
        WinterShine.printDebug(this.data.get(2) + ", " + this.data.get(3));

        int i = this.data.get(2);
        int j = this.data.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float)i / (float)j, 0.0F, 1.0F) : 0.0F;
    }

    public float getLitProgress() {


        int i = this.data.get(1);
        if (i == 0) {
            i = 400;
        }

        return Mth.clamp((float)this.data.get(0) / (float)i, 0.0F, 1.0F);
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }


    //region quickMoveStack
    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 11;  // must be the number of slots you have!
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
    //endregion

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    /*@Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {

    }

    @Override
    public void clearCraftingContent() {

    }

    @Override
    public boolean recipeMatches(RecipeHolder<ConfectioneryBakingRecipe> recipeHolder) {
        return recipeHolder.value().matches(CraftingInput.of(3, 3, blockEntity.getInputItems()), level);
    }

    @Override
    public int getResultSlotIndex() {
        return 10;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public int getSize() {
        return 10;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    public boolean shouldMoveToInventory(int i) {
        return false;
    }*/
}
