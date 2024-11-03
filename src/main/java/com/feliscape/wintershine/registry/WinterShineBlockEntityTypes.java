package com.feliscape.wintershine.registry;

import com.feliscape.wintershine.WinterShine;
import com.feliscape.wintershine.content.block.entity.ConfectioneryOvenBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class WinterShineBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WinterShine.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConfectioneryOvenBlockEntity>> CONFECTIONERY_OVEN =
            BLOCK_ENTITY_TYPES.register("confectionery_oven", () -> BlockEntityType.Builder.of(ConfectioneryOvenBlockEntity::new,
                    WinterShineBlocks.CONFECTIONERY_OVEN.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
