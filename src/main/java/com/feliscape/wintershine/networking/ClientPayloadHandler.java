package com.feliscape.wintershine.networking;

import com.feliscape.wintershine.content.attachment.MobEffectData;
import com.feliscape.wintershine.content.client.ClientMobEffectData;
import com.feliscape.wintershine.networking.payload.S2CMobEffectDataPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void handleMobEffectDataSync(final S2CMobEffectDataPayload data, final IPayloadContext context){
        ClientMobEffectData.set(data.siphonedHearts());
    }
}

