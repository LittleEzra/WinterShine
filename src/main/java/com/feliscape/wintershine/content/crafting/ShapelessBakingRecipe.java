package com.feliscape.wintershine.content.crafting;

import com.feliscape.wintershine.registry.WinterShineRecipeSerializers;
import com.feliscape.wintershine.registry.WinterShineRecipeTypes;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.Iterator;

public class ShapelessBakingRecipe implements CraftingRecipe {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;
    /**
     * Expand the max width and height allowed in the deserializer.
     * This should be called by modders who add custom crafting tables that are larger than the vanilla 3x3.
     * @param width your max recipe width
     * @param height your max recipe height
     */
    public static void setCraftingSize(int width, int height) {
        if (MAX_WIDTH < width) MAX_WIDTH = width;
        if (MAX_HEIGHT < height) MAX_HEIGHT = height;
    }
    final String group;
    final CraftingBookCategory category;
    final NonNullList<Ingredient> ingredients;
    final ItemStack result;
    final int bakingTime;
    private final boolean isSimple;

    public ShapelessBakingRecipe(String pGroup, CraftingBookCategory pCategory, NonNullList<Ingredient> pIngredients, ItemStack pResult, int pBakingTime) {
        this.group = pGroup;
        this.category = pCategory;
        this.ingredients = pIngredients;
        this.result = pResult;
        this.bakingTime = pBakingTime;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WinterShineRecipeSerializers.CONFECTIONERY_BAKING.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return this.result;
    }

    public int getBakingTime() {
        return this.bakingTime;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= this.ingredients.size();
    }

    public boolean matches(CraftingInput pInput, Level pLevel) {
        if (pInput.ingredientCount() != this.ingredients.size()) {
            return false;
        } else if (!this.isSimple) {
            ArrayList<ItemStack> nonEmptyItems = new ArrayList(pInput.ingredientCount());
            Iterator var4 = pInput.items().iterator();

            while(var4.hasNext()) {
                ItemStack item = (ItemStack)var4.next();
                if (!item.isEmpty()) {
                    nonEmptyItems.add(item);
                }
            }

            return RecipeMatcher.findMatches(nonEmptyItems, this.ingredients) != null;
        } else {
            return pInput.size() == 1 && this.ingredients.size() == 1 ? ((Ingredient)this.ingredients.getFirst()).test(pInput.getItem(0)) : pInput.stackedContents().canCraft(this, (IntList)null);
        }
    }

    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        return this.result.copy();
    }

    @Override
    public RecipeType<?> getType() {
        return WinterShineRecipeTypes.CONFECTIONERY_BAKING.get();
    }

    @Override
    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream().filter((p_151277_) -> {
            return !p_151277_.isEmpty();
        }).anyMatch(Ingredient::hasNoItems);
    }

    /*public static class Serializer implements RecipeSerializer<ShapelessBakingRecipe> {
        private static final MapCodec<ShapelessBakingRecipe> CODEC = RecordCodecBuilder.mapCodec((apply) -> {
            return apply.group(Codec.STRING.optionalFieldOf("group", "").forGetter((recipe) -> {
                return recipe.group;
            }), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((recipe) -> {
                return recipe.category;
            }), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((recipe) -> {
                return recipe.result;
            }), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((p_301021_) -> {
                Ingredient[] aingredient = (Ingredient[])p_301021_.toArray((x$0) -> {
                    return new Ingredient[x$0];
                });
                if (aingredient.length == 0) {
                    return DataResult.error(() -> {
                        return "No ingredients for shapeless recipe";
                    });
                } else {
                    return aingredient.length > 9 ? DataResult.error(() -> {
                        return "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(9);
                    }) : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                }
            }, DataResult::success).forGetter((shapelessBakingRecipe) -> {
                return shapelessBakingRecipe.ingredients;
            }), Codec.INT.fieldOf("baking_time").forGetter((recipe) -> {
                return recipe.bakingTime;
            })).apply(apply, ShapelessBakingRecipe::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessBakingRecipe> STREAM_CODEC = StreamCodec.of(ShapelessBakingRecipe.Serializer::toNetwork, ShapelessBakingRecipe.Serializer::fromNetwork);


        @Override
        public MapCodec<ShapelessBakingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessBakingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapelessBakingRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
            String s = byteBuf.readUtf();
            CraftingBookCategory craftingbookcategory = byteBuf.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(byteBuf);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
            int bakingTime = byteBuf.readInt();
            boolean showNotification = byteBuf.readBoolean();
            return new ShapelessBakingRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, bakingTime, showNotification);
        }

        private static void toNetwork(RegistryFriendlyByteBuf byteBuf, ShapelessBakingRecipe recipe) {
            byteBuf.writeUtf(recipe.group);
            byteBuf.writeEnum(recipe.category);
            ShapedRecipePattern.STREAM_CODEC.encode(byteBuf, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(byteBuf, recipe.result);
            byteBuf.writeInt(recipe.bakingTime);
            byteBuf.writeBoolean(recipe.showNotification);
        }
    }*/
}
