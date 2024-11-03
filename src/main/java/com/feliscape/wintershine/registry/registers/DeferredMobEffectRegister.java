package com.feliscape.wintershine.registry.registers;

import com.feliscape.wintershine.registry.holders.DeferredMobEffect;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class DeferredMobEffectRegister extends DeferredRegister<MobEffect> {
    protected DeferredMobEffectRegister(ResourceKey<? extends Registry<MobEffect>> registryKey, String namespace) {
        super(registryKey, namespace);
    }

    public static DeferredMobEffectRegister create(String namespace){
        return new DeferredMobEffectRegister(Registries.MOB_EFFECT, namespace);
    }

    public <B extends MobEffect> DeferredMobEffect<B> register(String name, Function<ResourceLocation, ? extends B> func) {
        return (DeferredMobEffect)super.register(name, func);
    }

    public <B extends MobEffect> DeferredMobEffect<B> register(String name, Supplier<? extends B> sup) {
        return this.register(name, (key) -> (B) sup.get());
    }
    protected <I extends MobEffect> DeferredMobEffect<I> createHolder(ResourceKey<? extends Registry<MobEffect>> registryKey, ResourceLocation key) {
        return DeferredMobEffect.createMobEffect(ResourceKey.create(registryKey, key));
    }
}
