package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.effect.custom.SiphonMobEffect;
import com.feliscape.wintershine.content.effect.custom.WinterShineMobEffect;
import com.feliscape.wintershine.registry.holders.DeferredMobEffect;
import com.feliscape.wintershine.registry.registers.DeferredMobEffectRegister;
import com.feliscape.wintershine.util.ColorUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, WinterShine.MOD_ID);

    public static DeferredHolder<MobEffect, SiphonMobEffect> SIPHON = MOB_EFFECTS.register("siphon",
            () -> new SiphonMobEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#d53540")));
    public static DeferredHolder<MobEffect, WinterShineMobEffect> PILFERING = MOB_EFFECTS.register("pilfering",
            () -> new WinterShineMobEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#fad64a")));
    public static DeferredHolder<MobEffect, WinterShineMobEffect> COMFORT = MOB_EFFECTS.register("comfort",
            () -> new WinterShineMobEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#e75d11")));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
