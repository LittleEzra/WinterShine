package com.feliscape.wintershine.data.datagen.recipe;

import com.feliscape.wintershine.data.datagen.builder.ConfectioneryBakingRecipeBuilder;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import com.feliscape.wintershine.registry.WinterShineItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WSBakingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(RecipeOutput output){
        bakeFood(output);
        bakeMiscellaneous(output);
    }

    private static void bakeFood(RecipeOutput output){
        ConfectioneryBakingRecipeBuilder.baking(WinterShineItems.GINGERBREAD, 2, NORMAL_COOKING)
                .requires(Items.WHEAT)
                .requires(Items.GLOW_BERRIES)
                .unlockedByAnyIngredient(Items.WHEAT, Items.GLOW_BERRIES);
    }
    private static void bakeMiscellaneous(RecipeOutput output){
        ConfectioneryBakingRecipeBuilder.baking(WinterShineBlocks.FROSTED_GINGERBREAD_BRICKS, 2, FAST_COOKING)
                .requires(WinterShineBlocks.GINGERBREAD_BRICKS, 2)
                .requires(Items.SUGAR)
                .unlockedByAnyIngredient(WinterShineBlocks.GINGERBREAD_BRICKS);
    }
}
