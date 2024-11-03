package com.feliscape.wintershine.data.datagen.language;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import com.feliscape.wintershine.registry.WinterShineItems;
import com.feliscape.wintershine.registry.WinterShineMobEffects;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
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
        this.addItem(WinterShineItems.GLOWING_CANDY_CANE, "Glowing Candy Cane");
        this.addItem(WinterShineItems.GINGERBREAD, "Gingerbread");
        this.addItem(WinterShineItems.GINGERBREAD_BRICK, "Gingerbread Brick");

        this.addItem(WinterShineItems.SACRIFICIAL_GINGERBREAD_MAN, "Sacrificial Gingerbread Man");
        this.addItem(WinterShineItems.MISCHIEVOUS_GINGERBREAD_MAN, "Mischievous Gingerbread Man");
        this.addItem(WinterShineItems.COZY_GINGERBREAD_MAN, "Cozy Gingerbread Man");

        this.addMobEffect(WinterShineMobEffects.SIPHON, "Siphon");
        this.addMobEffect(WinterShineMobEffects.PILFERING, "Pilfering");
        this.addMobEffect(WinterShineMobEffects.COMFORT, "Comfort");

        this.add("wintershine.tooltip.shift_for_more", "[Press SHIFT for more info]");

        this.add("itemGroup.wintershine.base", "Winter's Shine");
        this.add("container.wintershine.confectionery_oven", "Confectionery Oven");

    }

    private void addItemTooltip(Supplier<? extends Item> key, String name) {
        add(key.get().getDescriptionId() + ".tooltip", name);
    }
    private void addMobEffect(Supplier<? extends MobEffect> key, String name) {
        add(key.get().getDescriptionId(), name);
    }
}
