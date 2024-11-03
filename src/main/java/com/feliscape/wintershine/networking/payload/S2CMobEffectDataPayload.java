package com.feliscape.wintershine.networking.payload;

import com.feliscape.wintershine.WinterShine;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

// Server-To-Client
public record S2CMobEffectDataPayload(int siphonedHearts) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CMobEffectDataPayload> TYPE
            = new CustomPacketPayload.Type<>(WinterShine.asResource("mob_effect_data_sync"));

    public static final StreamCodec<ByteBuf, S2CMobEffectDataPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            S2CMobEffectDataPayload::siphonedHearts,
            S2CMobEffectDataPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
