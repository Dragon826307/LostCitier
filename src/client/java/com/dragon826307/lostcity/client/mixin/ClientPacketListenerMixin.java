package com.dragon826307.lostcity.client.mixin;

import com.dragon826307.lostcity.client.LostCitierClient;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(method = "handleOpenScreen",at = @At("HEAD"), cancellable = true)
    private void containerSetData(ClientboundOpenScreenPacket clientboundOpenScreenPacket, CallbackInfo ci) {
        if (LostCitierClient.TRY_TO_CHECK_STATUS_FORM_MENU  && clientboundOpenScreenPacket.getTitle().getString().equals("快捷工具")){
            LostCitierClient.menuEngagement(clientboundOpenScreenPacket);
            ci.cancel();
        }
    }
}
