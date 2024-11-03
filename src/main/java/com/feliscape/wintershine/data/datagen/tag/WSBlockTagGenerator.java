package com.feliscape.wintershine.data.datagen.tag;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class WSBlockTagGenerator extends BlockTagsProvider {
    public WSBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WinterShine.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                WinterShineBlocks.CONFECTIONERY_OVEN.get(),
                WinterShineBlocks.CANDY_CANE_LOG.get(),
                WinterShineBlocks.GINGERBREAD_BRICKS.get(),
                WinterShineBlocks.FROSTED_GINGERBREAD_BRICKS.get()
        );
    }
}
