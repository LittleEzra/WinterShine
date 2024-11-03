package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.block.custom.ConfectioneryOvenBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class WinterShineBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(WinterShine.MOD_ID);

    public static final DeferredBlock<Block> CONFECTIONERY_OVEN = registerBlock("confectionery_oven",
            () -> new ConfectioneryOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)));
    public static final DeferredBlock<RotatedPillarBlock> CANDY_CANE_LOG = registerBlock("candy_cane_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).strength(0.75F).mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> GINGERBREAD_BRICKS = registerBlock("gingerbread_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA).strength(1F).mapColor(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<Block> FROSTED_GINGERBREAD_BRICKS = registerBlock("gingerbread_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA).strength(1F).mapColor(MapColor.COLOR_ORANGE)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<Item> registerBlockItem(String name, DeferredBlock<T> block)
    {
        return WinterShineItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
