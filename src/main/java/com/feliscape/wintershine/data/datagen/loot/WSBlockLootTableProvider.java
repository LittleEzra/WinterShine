package com.feliscape.wintershine.data.datagen.loot;

import com.feliscape.wintershine.registry.WinterShineBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class WSBlockLootTableProvider extends BlockLootSubProvider {
    public WSBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(WinterShineBlocks.CONFECTIONERY_OVEN.get());
        dropSelf(WinterShineBlocks.CANDY_CANE_LOG.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return WinterShineBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
