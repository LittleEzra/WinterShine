package com.feliscape.wintershine.data.datagen.language;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import com.feliscape.wintershine.registry.WinterShineItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class WSLanguageProvider extends LanguageProvider {
    public WSLanguageProvider(PackOutput output, String locale) {
        super(output, WinterShine.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        this.addBlock(WinterShineBlocks.CONFECTIONERY_OVEN, "Confectionery Oven");
        this.addBlock(WinterShineBlocks.CANDY_CANE_LOG, "Candy Cane Log");
        this.addBlock(WinterShineBlocks.GINGERBREAD_BRICKS, "Gingerbread Bricks");
        this.addBlock(WinterShineBlocks.FROSTED_GINGERBREAD_BRICKS, "Frosted Gingerbread Bricks");

        this.addItem(WinterShineItems.CANDY_CANE, "Candy Cane");
        this.addItem(WinterShineItems.GINGERBREAD, "Gingerbread");
        this.addItem(WinterShineItems.GINGERBREAD_BRICK, "Gingerbread Brick");

        this.add("harvestmoon.tooltip.shift_for_more", "[Press SHIFT for more info]");

        this.add("itemGroup.wintershine.base", "Winter's Shine");
        this.add("container.wintershine.confectionery_oven", "Confectionery Oven");

    }

    private void addItemTooltip(Supplier<? extends Item> key, String name) {
        add(key.get().getDescriptionId() + ".tooltip", name);
    }
}
