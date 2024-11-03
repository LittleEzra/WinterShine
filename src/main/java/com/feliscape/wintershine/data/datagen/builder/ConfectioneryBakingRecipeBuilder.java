package com.feliscape.wintershine.data.datagen.builder;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.crafting.ConfectioneryBakingRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfectioneryBakingRecipeBuilder implements RecipeBuilder {
    private final NonNullList<Ingredient> ingredients = NonNullList.of(Ingredient.EMPTY);
    private final Item result;
    private final ItemStack resultStack;
    private final int bakingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private ConfectioneryBakingRecipeBuilder(ItemLike result, int count, int bakingTime){
        this(new ItemStack(result, count), bakingTime);
    }
    private ConfectioneryBakingRecipeBuilder(ItemStack result, int bakingTime){
        this.resultStack = result;
        this.result = result.getItem();
        this.bakingTime = bakingTime;
    }

    public static ConfectioneryBakingRecipeBuilder baking(ItemLike result, int count, int bakingTime){
        return new ConfectioneryBakingRecipeBuilder(result, count, bakingTime);
    }

    public ConfectioneryBakingRecipeBuilder requires(TagKey<Item> tag){
        return requires(Ingredient.of(tag));
    }
    public ConfectioneryBakingRecipeBuilder requires(ItemLike item){
        return requires(item, 1);
    }
    public ConfectioneryBakingRecipeBuilder requires(ItemLike item, int quantity){
        return requires(Ingredient.of(item), 1);
    }
    public ConfectioneryBakingRecipeBuilder requires(Ingredient ingredient){
        return requires(ingredient, 1);
    }

    public ConfectioneryBakingRecipeBuilder requires(Ingredient ingredient, int quantity){
        for (int i = 0; i < quantity; i++){
            ingredients.add(ingredient);
        }
        return this;
    }

    @Override
    public ConfectioneryBakingRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        this.criteria.put(criterionName, criterionTrigger);
        return this;
    }

    public ConfectioneryBakingRecipeBuilder unlockedByItems(String criterionName, ItemLike... items) {
        return unlockedBy(criterionName, InventoryChangeTrigger.TriggerInstance.hasItems(items));
    }

    public ConfectioneryBakingRecipeBuilder unlockedByAnyIngredient(ItemLike... items) {
        this.criteria.put("has_any_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items).build()));
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.asItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        ResourceLocation recipeId = id.withPrefix("cooking/");
        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);
        ConfectioneryBakingRecipe recipe = new ConfectioneryBakingRecipe(
                this.ingredients,
                this.resultStack,
                this.bakingTime
        );
        output.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/cooking/")));
    }

    public void build(RecipeOutput output) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(result.asItem());
        save(output, WinterShine.asResource(location.getPath()));
    }

    public void build(RecipeOutput outputIn, String save) {
        ResourceLocation resourcelocation = BuiltInRegistries.ITEM.getKey(result.asItem());
        if ((ResourceLocation.parse(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Confectionery Baking Recipe " + save + " should remove its 'save' argument");
        } else {
            save(outputIn, ResourceLocation.parse(save));
        }
    }
}
