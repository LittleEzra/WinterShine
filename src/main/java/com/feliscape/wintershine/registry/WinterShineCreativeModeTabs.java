package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WinterShine.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE_CREATIVE_TAB = REGISTER.register("base",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.wintershine.base"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> new ItemStack(Items.SNOWBALL))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(WinterShineBlocks.CONFECTIONERY_OVEN.get());
                        pOutput.accept(WinterShineBlocks.CANDY_CANE_LOG.get());

                        pOutput.accept(WinterShineItems.CANDY_CANE.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus){
        REGISTER.register(eventBus);
    }
}
