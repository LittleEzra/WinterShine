package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.crafting.ConfectioneryBakingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_TYPE, WinterShine.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ConfectioneryBakingRecipe>> CONFECTIONERY_BAKING =
            SERIALIZERS.register("confectionery_baking", () -> registerRecipeType("confectionery_baking"));
    //public static final DeferredHolder<RecipeType<?>, RecipeType<ShapelessBakingRecipe>> SHAPELESS_CONFECTIONERY_BAKING =
    //        SERIALIZERS.register("shapeless_confectionery_baking", () -> registerRecipeType("shapeless_confectionery_baking"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return WinterShine.MOD_ID + ":" + identifier;
            }
        };
    }

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }

}
