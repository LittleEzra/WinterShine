package com.feliscape.wintershine.content.effect.custom;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.attachment.MobEffectData;
import com.feliscape.wintershine.networking.payload.S2CMobEffectDataPayload;
import com.feliscape.wintershine.registry.WinterShineAttachmentTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.neoforged.neoforge.network.PacketDistributor;

public class SiphonMobEffect extends WinterShineMobEffect {
    public SiphonMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectEnd(LivingEntity entity, int amplifier) {
        WinterShine.printDebug("Siphon effect ended");
        MobEffectData data = entity.getData(WinterShineAttachmentTypes.MOB_EFFECT_DATA.get());
        WinterShine.printDebug("Entity Absorption before Siphon: " + entity.getAbsorptionAmount());
        entity.setAbsorptionAmount(entity.getAbsorptionAmount() + data.siphon.getSiphonedHearts());
        WinterShine.printDebug("Entity Absorption after Siphon: " + entity.getAbsorptionAmount());
        data.siphon.reset();
        if (entity instanceof ServerPlayer player)
            PacketDistributor.sendToPlayer(player, new S2CMobEffectDataPayload(data.siphon.getSiphonedHearts()));
    }
}
