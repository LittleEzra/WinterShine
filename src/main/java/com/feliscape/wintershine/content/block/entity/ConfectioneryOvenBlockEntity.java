package com.feliscape.wintershine.content.block.entity;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.block.custom.ConfectioneryOvenBlock;
import com.feliscape.wintershine.content.crafting.ConfectioneryBakingRecipe;
import com.feliscape.wintershine.content.screen.ConfectioneryOvenMenu;
import com.feliscape.wintershine.registry.WinterShineBlockEntityTypes;
import com.feliscape.wintershine.registry.WinterShineRecipeTypes;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ConfectioneryOvenBlockEntity extends SyncedBlockEntity implements MenuProvider, Nameable, RecipeCraftingHolder {
    protected static final int INPUT_SLOT_COUNT = 9;
    protected static final int SLOT_FUEL = 9;
    protected static final int SLOT_RESULT = 10;
    protected static final int INVENTORY_SIZE = SLOT_RESULT + 1;

    private final ItemStackHandler inventory;
    private final IItemHandler inputHandler;
    private final IItemHandler outputHandler;
    protected final ContainerData dataAccess;

    int litTime;
    int litDuration;
    private int cookTime;
    private int cookTimeTotal;
    private Component customName;
    private final Object2IntOpenHashMap<ResourceLocation> usedRecipeTracker;
    private final RecipeManager.CachedCheck<CraftingInput, ConfectioneryBakingRecipe> quickCheck;

    public ConfectioneryOvenBlockEntity(BlockPos pos, BlockState state) {
        super(WinterShineBlockEntityTypes.CONFECTIONERY_OVEN.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = new ConfectioneryOvenItemHandler(inventory, Direction.UP);
        this.outputHandler = new ConfectioneryOvenItemHandler(inventory, Direction.DOWN);
        this.usedRecipeTracker = new Object2IntOpenHashMap<>();
        this.quickCheck = RecipeManager.createCheck(WinterShineRecipeTypes.CONFECTIONERY_BAKING.get());
        this.dataAccess = createContainerData();
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                WinterShineBlockEntityTypes.CONFECTIONERY_OVEN.get(),
                (be, context) -> {
                    if (context == Direction.UP) {
                        return be.inputHandler;
                    }
                    return be.outputHandler;
                }
        );
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE)
        {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }
    private ContainerData createContainerData() {
        return new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ConfectioneryOvenBlockEntity.this.litTime;
                    case 1 -> ConfectioneryOvenBlockEntity.this.litDuration;
                    case 2 -> ConfectioneryOvenBlockEntity.this.cookTime;
                    case 3 -> ConfectioneryOvenBlockEntity.this.cookTimeTotal;
                    default -> {
                        WinterShine.printDebug("Default!");
                        yield 0;
                    }
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ConfectioneryOvenBlockEntity.this.litTime = pValue;
                    case 1 -> ConfectioneryOvenBlockEntity.this.litDuration = pValue;
                    case 2 -> ConfectioneryOvenBlockEntity.this.cookTime = pValue;
                    case 3 -> ConfectioneryOvenBlockEntity.this.cookTimeTotal = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }
    
    private boolean isLit() {
        return this.litTime > 0;
    }

    private Optional<RecipeHolder<ConfectioneryBakingRecipe>> getMatchingRecipe(CraftingInput input) {
        if (level == null) return Optional.empty();
        return hasInput() ? quickCheck.getRecipeFor(input, this.level) : Optional.empty();
    }

    private boolean hasInput() {
        for (int i = 0; i < INPUT_SLOT_COUNT; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    public static void cookingTick(Level level, BlockPos pos, BlockState state, ConfectioneryOvenBlockEntity oven) {
        boolean wasLit = oven.isLit();
        boolean inventoryChanged = false;

        if (oven.hasInput()) {
            Optional<RecipeHolder<ConfectioneryBakingRecipe>> recipe = oven.getMatchingRecipe(CraftingInput.of(3, 3, oven.getInputItems()));
            if (!oven.isLit() && recipe.isPresent()) {
                ItemStack fuelStack = oven.inventory.getStackInSlot(SLOT_FUEL);
                if (!fuelStack.isEmpty()) {
                    oven.litDuration = fuelStack.getBurnTime(RecipeType.SMELTING);
                    oven.litTime = oven.litDuration;
                    fuelStack.shrink(1);
                    inventoryChanged = true;
                }
            }

            if (oven.isLit()) {
                if (recipe.isPresent() && oven.canBake(recipe.get().value())) {
                    inventoryChanged = oven.processBaking(recipe.get(), oven);
                } else {
                    oven.cookTime = 0;
                }
            } else if (oven.cookTime > 0) { // Reduce Cook Time
                oven.cookTime = Mth.clamp(oven.cookTime - 2, 0, oven.cookTimeTotal);
            }
        } else if (oven.cookTime > 0) { // Reduce Cook Time
            oven.cookTime = Mth.clamp(oven.cookTime - 2, 0, oven.cookTimeTotal);
        }

        if (wasLit != oven.isLit()){
            inventoryChanged = true;
            state = state.setValue(ConfectioneryOvenBlock.LIT, oven.isLit());
            level.setBlock(pos, state, 3);
        }
        if (inventoryChanged){
            oven.inventoryChanged();
        }
    }
    protected boolean canBake(ConfectioneryBakingRecipe recipe) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem(this.level.registryAccess());
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack result = inventory.getStackInSlot(SLOT_RESULT);
                if (result.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(result, resultStack)) {
                    return false;
                } else if (result.getCount() + resultStack.getCount() <= inventory.getSlotLimit(SLOT_RESULT)) {
                    return true;
                } else {
                    return result.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }
    private boolean processBaking(RecipeHolder<ConfectioneryBakingRecipe> recipe, ConfectioneryOvenBlockEntity cookingPot) {
        if (level == null) return false;

        ++cookTime;
        if (litTime > 0) --litTime;
        cookTimeTotal = recipe.value().getBakingTime();
        if (cookTime < cookTimeTotal) {
            return false;
        }

        cookTime = 0;
        ItemStack resultSlot = inventory.getStackInSlot(SLOT_RESULT);
        ItemStack resultStack = recipe.value().getResultItem(this.level.registryAccess());
        if (resultSlot.isEmpty()) {
            inventory.setStackInSlot(SLOT_RESULT, resultStack.copy());
        } else if (ItemStack.isSameItem(resultSlot, resultStack)) {
            resultSlot.grow(resultStack.getCount());
        }
        cookingPot.setRecipeUsed(recipe);

        for (int i = 0; i < INPUT_SLOT_COUNT; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            /*if (slotStack.hasCraftingRemainingItem()) {
                ejectIngredientRemainder(slotStack.getCraftingRemainingItem());
            } else if (INGREDIENT_REMAINDER_OVERRIDES.containsKey(slotStack.getItem())) {
                ejectIngredientRemainder(INGREDIENT_REMAINDER_OVERRIDES.get(slotStack.getItem()).getDefaultInstance());
            }*/
            if (!slotStack.isEmpty())
                slotStack.shrink(1);
        }
        return true;
    }

    public List<ItemStack> getInputItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(INPUT_SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < INPUT_SLOT_COUNT; i++){
            items.set(i, inventory.getStackInSlot(i));
        }
        return items;
    }

    @Override
    public Component getName() {
        return customName != null ? customName : Component.translatable("container.wintershine.confectionery_oven");
    }

    @Override
    public Component getDisplayName() {
        return getName();
    }

    @Override
    @Nullable
    public Component getCustomName() {
        return customName;
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        litTime = compound.getInt("LitTime");
        litDuration = compound.getInt("LitTimeTotal");
        cookTime = compound.getInt("CookTime");
        cookTimeTotal = compound.getInt("CookTimeTotal");
        if (compound.contains("CustomName", 8)) {
            customName = Component.Serializer.fromJson(compound.getString("CustomName"), registries);
        }
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            usedRecipeTracker.put(ResourceLocation.parse(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("LitTime", litTime);
        compound.putInt("LitTimeTotal", litDuration);
        compound.putInt("CookTime", cookTime);
        compound.putInt("CookTimeTotal", cookTimeTotal);
        if (customName != null) {
            compound.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
        compound.put("Inventory", inventory.serializeNBT(registries));
        CompoundTag compoundRecipes = new CompoundTag();
        usedRecipeTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private CompoundTag writeItems(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return writeItems(new CompoundTag(), registries);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ConfectioneryOvenMenu(i, inventory, this, dataAccess);
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.id();
            usedRecipeTracker.addTo(recipeID, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    public void dropContents() {
        if (level != null) {
            for (int i = 0; i < INVENTORY_SIZE; i++) {
                Block.popResource(this.level, this.getBlockPos(), inventory.getStackInSlot(i));
            }
        }
    }
}
