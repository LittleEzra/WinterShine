package com.feliscape.wintershine;

import com.feliscape.wintershine.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

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

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
}
