package com.feliscape.wintershine.data.datagen;

import com.feliscape.wintershine.data.datagen.recipe.WSBakingRecipes;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import com.feliscape.wintershine.registry.WinterShineItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class WSRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public WSRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        WSBakingRecipes.register(recipeOutput);

        twoByTwoPackingAndUnpacking(recipeOutput, RecipeCategory.BUILDING_BLOCKS, WinterShineBlocks.CANDY_CANE_LOG.get(), WinterShineItems.CANDY_CANE.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, WinterShineBlocks.GINGERBREAD_BRICKS, 2)
                .define('#', WinterShineItems.GINGERBREAD_BRICK)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_gingerbread_brick", has(WinterShineItems.GINGERBREAD_BRICK))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, WinterShineItems.CANDY_CANE, 2)
                .define('#', Items.SUGAR)
                .define('o', Items.SWEET_BERRIES)
                .pattern("#o")
                .pattern("o ")
                .pattern("# ")
                .unlockedBy("has_sweet_berries", has(Items.SWEET_BERRIES))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, WinterShineItems.CANDY_CANE, 2)
                .define('#', Items.SUGAR)
                .define('o', Items.SWEET_BERRIES)
                .pattern("#o")
                .pattern("o ")
                .pattern("# ")
                .unlockedBy("has_sweet_berries", has(Items.SWEET_BERRIES))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, WinterShineBlocks.FROSTED_GINGERBREAD_BRICKS, 2)
                .requires(WinterShineBlocks.GINGERBREAD_BRICKS, 2)
                .requires(Items.SUGAR)
                .unlockedBy("has_gingerbread_bricks", has(WinterShineBlocks.GINGERBREAD_BRICKS))
                .save(recipeOutput);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(WinterShineItems.GINGERBREAD), RecipeCategory.MISC, WinterShineItems.GINGERBREAD_BRICK, 0.3F, 200)
                .unlockedBy("has_gingerbread", has(WinterShineItems.GINGERBREAD)).save(recipeOutput);
    }

    private void twoByTwoPackingAndUnpacking(RecipeOutput recipeOutput, RecipeCategory recipeCategory, ItemLike packed, ItemLike unpacked) {
        twoByTwoPacker(recipeOutput, recipeCategory, packed, unpacked);
        ShapelessRecipeBuilder.shapeless(recipeCategory, unpacked, 4)
                .requires(packed)
                .unlockedBy(getHasName(packed), has(packed))
                .save(recipeOutput, getConversionRecipeName(unpacked, packed));
    }
}