package com.feliscape.wintershine.content.event;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.attachment.MobEffectData;
import com.feliscape.wintershine.content.effect.custom.WinterShineMobEffect;
import com.feliscape.wintershine.content.sound.WinterShineSoundEvents;
import com.feliscape.wintershine.registry.WinterShineAttachmentTypes;
import com.feliscape.wintershine.content.client.gui.SiphonGuiLayer;
import com.feliscape.wintershine.registry.WinterShineMobEffects;
import com.feliscape.wintershine.networking.ClientPayloadHandler;
import com.feliscape.wintershine.networking.payload.S2CMobEffectDataPayload;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;

public class Events {
    @EventBusSubscriber(modid = WinterShine.MOD_ID)
    public static class GameEvents{
        @SubscribeEvent
        public static void onLivingHurtPre(final LivingDamageEvent.Pre event){
            LivingEntity entity = event.getEntity();
            if (entity.hasEffect(WinterShineMobEffects.COMFORT)){
                MobEffectInstance instance = entity.getEffect(WinterShineMobEffects.COMFORT);
                event.setNewDamage(Math.max(event.getNewDamage(), event.getNewDamage() - 2 - instance.getAmplifier()));
            }
        }
        @SubscribeEvent
        public static void onLivingHurtPost(final LivingDamageEvent.Post event){
            LivingEntity livingEntity = event.getEntity();

            Entity directAttacker = event.getSource().getDirectEntity();
            Entity attacker = event.getSource().getEntity();

            LivingEntity livingAttacker = null;

            if (attacker instanceof LivingEntity living) livingAttacker = living;
            else if (directAttacker instanceof LivingEntity living) livingAttacker = living;

            if (livingAttacker != null){
                if (livingAttacker.hasEffect(WinterShineMobEffects.SIPHON)){
                    MobEffectData data = livingAttacker.getData(WinterShineAttachmentTypes.MOB_EFFECT_DATA.get());
                    data.siphon.siphonHearts(event.getNewDamage());
                    if (livingAttacker instanceof ServerPlayer player)
                        PacketDistributor.sendToPlayer(player, new S2CMobEffectDataPayload(data.siphon.getSiphonedHearts()));
                }
                if (livingAttacker.hasEffect(WinterShineMobEffects.PILFERING)){
                    List<MobEffectInstance> effects = livingEntity.getActiveEffects().stream().toList();
                    MobEffectInstance instance = effects.get(livingEntity.getRandom().nextInt(effects.size()));
                    livingAttacker.addEffect(instance);
                    livingEntity.removeEffect(instance.getEffect());

                    livingAttacker.level().playSound((Player)null, livingAttacker.getX(), livingAttacker.getY(), livingAttacker.getZ(), WinterShineSoundEvents.SIPHON.get(),
                            livingAttacker.getSoundSource(), 1.0F, 1.0F);
                }
            }
        }
        @SubscribeEvent
        public static void onMobEffectExpired(final MobEffectEvent.Expired event){
            MobEffectInstance instance = event.getEffectInstance();
            if (instance.getEffect().value() instanceof WinterShineMobEffect customEffect){
                customEffect.onEffectEnd(event.getEntity(), instance.getAmplifier());
            }
        }
    }
    @EventBusSubscriber(modid = WinterShine.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEvents{
        @SubscribeEvent
        public static void registerPayloads(final RegisterPayloadHandlersEvent event){
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playToClient(
                    S2CMobEffectDataPayload.TYPE,
                    S2CMobEffectDataPayload.STREAM_CODEC,
                    ClientPayloadHandler::handleMobEffectDataSync
            );
        }

        @SubscribeEvent
        public static void registerGuiLayers(final RegisterGuiLayersEvent event)
        {
            event.registerAbove(VanillaGuiLayers.CROSSHAIR, SiphonGuiLayer.ID, new SiphonGuiLayer());
        }
    }
}
