package com.feliscape.wintershine.content.crafting;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.CraftingBookCategory;

public enum ConfectioneryCraftingBookCategory implements StringRepresentable {
    CONFECTIONERY("confectionery");

    public final String name;

    ConfectioneryCraftingBookCategory(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
