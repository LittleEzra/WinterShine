package com.feliscape.wintershine.content.sound;

import com.feliscape.wintershine.WinterShine;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, WinterShine.MOD_ID);

    public static DeferredHolder<SoundEvent, SoundEvent> SIPHON = registerSound("effect.siphon");

    public static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(WinterShine.asResource(name)));
    }

    public static void register(IEventBus eventBus){

    }
}
