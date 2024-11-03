package com.feliscape.wintershine.data.datagen.model;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.block.custom.ConfectioneryOvenBlock;
import com.feliscape.wintershine.registry.WinterShineBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Function;

public class WSBlockStateProvider extends BlockStateProvider {

    public WSBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WinterShine.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeFurnaceLikeBlock(WinterShineBlocks.CONFECTIONERY_OVEN, state -> state.getValue(ConfectioneryOvenBlock.LIT));
        axisBlockWithItem(WinterShineBlocks.CANDY_CANE_LOG.get());
        blockWithItem(WinterShineBlocks.GINGERBREAD_BRICKS);
        blockWithItem(WinterShineBlocks.FROSTED_GINGERBREAD_BRICKS);
    }

    private void makeFurnaceLikeBlock(DeferredBlock<? extends Block> block, Function<BlockState, Boolean> onOffFunc) {
        ResourceLocation baseTexture = blockTexture(block.get());
        ModelFile off = models().orientableWithBottom(name(block.get()),
                extend(baseTexture, "_side"),
                extend(baseTexture, "_front"),
                extend(baseTexture, "_bottom"),
                extend(baseTexture, "_top")
        );
        ModelFile on = models().orientableWithBottom(name(block.get()) + "_on",
                extend(baseTexture, "_side"),
                extend(baseTexture, "_front_on"),
                extend(baseTexture, "_bottom"),
                extend(baseTexture, "_top")
        );
        simpleBlockItem(block.get(), off);

        horizontalBlock(block.get(), state -> onOffFunc.apply(state) ? on : off);
    }

    public void pillarBlock(RotatedPillarBlock pillarBlock) {
        axisBlock(pillarBlock, blockTexture(pillarBlock), extend(blockTexture(pillarBlock), "_end"));
    }
    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private void blockItem(DeferredBlock<? extends Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(WinterShine.MOD_ID +
                ":block/" + key(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(DeferredBlock<? extends Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void blockWithItemAndRenderType(DeferredBlock<? extends Block> blockRegistryObject, String renderType){
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(name(blockRegistryObject.get()), blockTexture(blockRegistryObject.get())).renderType(renderType));
    }

    private void leavesBlock(DeferredBlock<? extends Block> blockRegistryObject, String renderType){
        ModelFile model = models().withExistingParent(blockRegistryObject.getId().getPath(), "minecraft:block/leaves")
                .texture("all", blockTexture(blockRegistryObject.get())).renderType(renderType);
        getVariantBuilder(blockRegistryObject.get())
                .partialState().setModels( new ConfiguredModel(model));
        simpleBlockItem(blockRegistryObject.get(), model);
    }

    public void logBlockWithItem(RotatedPillarBlock block) {
        axisBlockWithItem(block, blockTexture(block), extend(blockTexture(block), "_top"));
    }

    public void axisBlockWithItem(RotatedPillarBlock block) {
        axisBlockWithItem(block, extend(blockTexture(block), "_side"),
                extend(blockTexture(block), "_end"));
    }

    public void axisBlockWithItem(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) {
        axisBlockWithItem(block,
                models().cubeColumn(name(block), side, end),
                models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
    }

    public void axisBlockWithItem(RotatedPillarBlock block, ModelFile vertical, ModelFile horizontal) {
        getVariantBuilder(block)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
        simpleBlockItem(block, vertical);
    }

    public void crossBlockWithRenderType(Block block, String renderType) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().cross(name(block), blockTexture(block)).renderType(renderType)));
    }

    public void horizontalBlockWithItem(DeferredBlock<? extends Block> block, ModelFile model){
        horizontalBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }
    public void cubeBottomTop(Block block){
        simpleBlockWithItem(block, models().cubeBottomTop(name(block),
                extend(blockTexture(block), "_side"),
                extend(blockTexture(block), "_bottom"),
                extend(blockTexture(block), "_top")));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
    private String name(Block block) {
        return key(block).getPath();
    }
    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }
}
