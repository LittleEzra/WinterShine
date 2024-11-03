package com.feliscape.wintershine.content.effect.custom;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class WinterShineMobEffect extends MobEffect {
    public WinterShineMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    public WinterShineMobEffect(MobEffectCategory category, int color, ParticleOptions particles) {
        super(category, color, particles);
    }

    /**
     * Triggered when an effect ends.
     */
    public void onEffectEnd(LivingEntity entity, int amplifier){

    }

    /**
     * Triggered during LivingHurtEvent
     * @return Whether to cancel the entity being hurt. Function will always run no matter the return value of other effects.
     */
    public boolean beforeMobHurt(){
        return true;
    }

    /**
     * Runs when the entity attacks another entity
     * @return Whether to cancel the entity being hurt. Function will always run no matter the return value of other effects.
     */
    public boolean onMobAttack(){
        return true;
    }
}
