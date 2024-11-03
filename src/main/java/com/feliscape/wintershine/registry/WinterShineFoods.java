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
    public static final FoodProperties GINGERBREAD_MAN =
            new FoodProperties.Builder().nutrition(5).saturationModifier(0.4F).build();

    public static final FoodProperties SACRIFICIAL_GINGERBREAD_MAN = new FoodProperties.Builder().nutrition(-4)
            .saturationModifier(0.4F).effect(() -> new MobEffectInstance(WinterShineMobEffects.SIPHON, 200), 1.0F)
            .alwaysEdible().build();
    public static final FoodProperties MISCHIEVOUS_GINGERBREAD_MAN = new FoodProperties.Builder().nutrition(5)
            .saturationModifier(0.4F).effect(() -> new MobEffectInstance(WinterShineMobEffects.PILFERING, 200), 1.0F)
            .alwaysEdible().build();
    public static final FoodProperties COZY_GINGERBREAD_MAN = new FoodProperties.Builder().nutrition(5)
            .saturationModifier(0.4F).effect(() -> new MobEffectInstance(WinterShineMobEffects.COMFORT, 30 * 20), 1.0F)
            .alwaysEdible().build();
}
