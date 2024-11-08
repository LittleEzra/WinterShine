package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.screen.ConfectioneryOvenMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, WinterShine.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<ConfectioneryOvenMenu>> CONFECTIONERY_OVEN_MENU =
            registerMenuType("confectionery_oven_menu", ConfectioneryOvenMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
