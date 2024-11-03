package com.feliscape.wintershine.data.datagen;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.data.datagen.language.WSLanguageProvider;
import com.feliscape.wintershine.data.datagen.loot.WSBlockLootTableProvider;
import com.feliscape.wintershine.data.datagen.model.WSBlockStateProvider;
import com.feliscape.wintershine.data.datagen.model.WSItemModelProvider;
import com.feliscape.wintershine.data.datagen.tag.WSBlockTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = WinterShine.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        if (event.includeServer()){
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(true, new WSRecipeProvider(packOutput, lookupProvider));
            generator.addProvider(true, new WSBlockStateProvider(packOutput, existingFileHelper));
            generator.addProvider(true, new WSItemModelProvider(packOutput, existingFileHelper));

            generator.addProvider(true, new WSBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));

            generator.addProvider(true, new WSLanguageProvider(packOutput, "en_us"));

            generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(WSBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        }
    }
}
