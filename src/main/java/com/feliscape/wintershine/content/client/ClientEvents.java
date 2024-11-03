package com.feliscape.wintershine.content.client;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.screen.ConfectioneryOvenScreen;
import com.feliscape.wintershine.registry.WinterShineMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientEvents {
    @EventBusSubscriber(modid = WinterShine.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
        @SubscribeEvent
        public static void onRegisterMenuScreens(RegisterMenuScreensEvent event)
        {
            event.register(WinterShineMenuTypes.CONFECTIONERY_OVEN_MENU.get(), ConfectioneryOvenScreen::new);
        }
    }
}
