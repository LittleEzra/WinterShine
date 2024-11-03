package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.crafting.ConfectioneryBakingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, WinterShine.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ConfectioneryBakingRecipe>> CONFECTIONERY_BAKING =
            SERIALIZERS.register("confectionery_baking", ConfectioneryBakingRecipe.Serializer::new);
    //public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ShapelessBakingRecipe>> SHAPELESS_CONFECTIONERY_BAKING =
    //        SERIALIZERS.register("shapeless_confectionery_baking", ShapelessBakingRecipe.Serializer::new);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
