package com.feliscape.wintershine.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class WinterShineFoods {
    public static final FoodProperties CANDY_CANE =
            new FoodProperties.Builder().nutrition(3).saturationModifier(0.2F).build();
    public static final FoodProperties GLOWING_CANDY_CANE =
            new FoodProperties.Builder().nutrition(4).saturationModifier(0.2F)
                    .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 200), 1F).build();
    public static final FoodProperties GINGERBREAD =
            new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
}
