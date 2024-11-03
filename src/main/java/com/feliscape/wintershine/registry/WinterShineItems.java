package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WinterShine.MOD_ID);

    public static final DeferredItem<Item> CANDY_CANE = ITEMS.register("candy_cane",
            () -> new Item(new Item.Properties().food(WinterShineFoods.CANDY_CANE)));
    public static final DeferredItem<Item> GLOWING_CANDY_CANE = ITEMS.register("glowing_candy_cane",
            () -> new Item(new Item.Properties().food(WinterShineFoods.GLOWING_CANDY_CANE)));
    public static final DeferredItem<Item> GINGERBREAD = ITEMS.register("gingerbread",
            () -> new Item(new Item.Properties().food(WinterShineFoods.GINGERBREAD)));
    public static final DeferredItem<Item> GINGERBREAD_BRICK = ITEMS.register("gingerbread_brick",
            () -> new Item(new Item.Properties().food(WinterShineFoods.GINGERBREAD)));


    public static final DeferredItem<Item> MISCHIEVOUS_GINGERBREAD_MAN = ITEMS.register("mischievous_gingerbread_man",
            () -> new Item(new Item.Properties().food(WinterShineFoods.MISCHIEVOUS_GINGERBREAD_MAN)));
    public static final DeferredItem<Item> COZY_GINGERBREAD_MAN = ITEMS.register("cozy_gingerbread_man",
            () -> new Item(new Item.Properties().food(WinterShineFoods.COZY_GINGERBREAD_MAN)));
    public static final DeferredItem<Item> SACRIFICIAL_GINGERBREAD_MAN = ITEMS.register("sacrificial_gingerbread_man",
            () -> new Item(new Item.Properties().food(WinterShineFoods.SACRIFICIAL_GINGERBREAD_MAN)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
