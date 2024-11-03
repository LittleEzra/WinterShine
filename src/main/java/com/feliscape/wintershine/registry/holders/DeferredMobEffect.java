package com.feliscape.wintershine.registry.holders;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DeferredMobEffect<T extends MobEffect> extends DeferredHolder<MobEffect, T> {
    protected DeferredMobEffect(ResourceKey<MobEffect> key) {
        super(key);
    }

    public static <T extends MobEffect> DeferredMobEffect<T> createMobEffect(ResourceLocation key) {
        return createMobEffect(ResourceKey.create(Registries.MOB_EFFECT, key));
    }

    public static <T extends MobEffect> DeferredMobEffect<T> createMobEffect(ResourceKey<MobEffect> key) {
        return new DeferredMobEffect<>(key);
    }
}
