package com.feliscape.wintershine.data.datagen.model;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.registry.WinterShineItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class WSItemModelProvider extends ItemModelProvider {
    public WSItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WinterShine.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheldItem(WinterShineItems.CANDY_CANE);
        handheldItem(WinterShineItems.GLOWING_CANDY_CANE);
    }

    private ItemModelBuilder simpleItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                WinterShine.asResource("item/" + item.getId().getPath()));
    }
    private ItemModelBuilder handheldItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0",
                WinterShine.asResource("item/" + item.getId().getPath()));
    }
    private ItemModelBuilder candyCaneItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(),
                WinterShine.asResource("item/candy_cane_template")).texture("layer0",
                WinterShine.asResource("item/" + item.getId().getPath()));
    }
    private ItemModelBuilder rotatedHandheldItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(),
                WinterShine.asResource("item/rotated_handheld")).texture("layer0",
                WinterShine.asResource("item/" + item.getId().getPath()));
    }

    /*public void manualBlockItem(DeferredBlock<?> block) {
        this.withExistingParent(WinterShine.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + NeoForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(DeferredBlock<?> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + NeoForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<?> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  WinterShine.asResource("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<?> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  WinterShine.asResource("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<?> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  WinterShine.asResource("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }*/

    private ItemModelBuilder blockItemSprite(DeferredBlock<?> item) { // Uses a block instead of item (Example: Doors)
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                WinterShine.asResource("item/" + item.getId().getPath()));
    }
    private ItemModelBuilder generatedBlockItem(DeferredBlock<?> item) { // Uses the texture from textures/block (Example: Saplings)
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                WinterShine.asResource("block/" + item.getId().getPath()));
    }
    private ItemModelBuilder torchItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                WinterShine.asResource("block/" + item.getId().getPath()));
    }
    private ItemModelBuilder mugItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(),
                WinterShine.asResource("item/mug_item")).texture("layer0",
                WinterShine.asResource( "item/" + item.getId().getPath()));
    }
}
