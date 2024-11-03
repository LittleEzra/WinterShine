package com.feliscape.wintershine;

import com.feliscape.wintershine.registry.WinterShineAttachmentTypes;
import com.feliscape.wintershine.registry.WinterShineMobEffects;
import com.feliscape.wintershine.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(WinterShine.MOD_ID)
public class WinterShine
{
    public static final String MOD_ID = "wintershine";
    private static final Logger LOGGER = LogUtils.getLogger();

    public WinterShine(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        WinterShineItems.register(modEventBus);
        WinterShineBlocks.register(modEventBus);
        WinterShineBlockEntityTypes.register(modEventBus);

        WinterShineMobEffects.register(modEventBus);
        WinterShineAttachmentTypes.register(modEventBus);

        WinterShineCreativeModeTabs.register(modEventBus);

        WinterShineRecipeTypes.register(modEventBus);
        WinterShineRecipeSerializers.register(modEventBus);
        WinterShineMenuTypes.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation asResource(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public static void printDebug(String line){
        LOGGER.debug(line);
    }
    public static void printDebug(boolean value){
        LOGGER.debug(((Boolean)value).toString());
    }
    public static void printDebug(int value){
        LOGGER.debug(((Integer)value).toString());
    }
    public static void printDebug(float value){
        LOGGER.debug(((Float)value).toString());
    }
    public static void printDebug(double value){
        LOGGER.debug(((Double)value).toString());
    }

    public static void printDebug(String format, Object... args){
        LOGGER.debug(format, args);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(final BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event)
    {

    }
}
