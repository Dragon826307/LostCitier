package com.dragon826307.lostcity.client.mixin;

import com.dragon826307.lostcity.client.LostCitierClient;
import com.dragon826307.lostcity.client.hud_bars.HudStatusAndBars;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @Unique
    private static int cancel_count = 0;
    @Unique
    private static final boolean[] checking = new boolean[256];
    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;)V",at = @At("HEAD"), cancellable = true)
    private void addMessage(Component component, CallbackInfo ci){
        LostCitierClient.TRY_TO_CHECK_SERVER = System.currentTimeMillis()-LostCitierClient.check_sub_server_time < LostCitierClient.CHECK_TIMEOUT_MILLIS_SECONDS;
        if (LostCitierClient.TRY_TO_CHECK_SERVER) {
            Matcher matcher = Pattern.compile("\\s([a-zA-Z\\d]+[。.])$").matcher(component.getString());
            if (matcher.find()) {
                checking[0] = true;
                checking[1] = true;
                LostCitierClient.TRY_TO_CHECK_SERVER = false;
                LostCitierClient.check_sub_server_time = 0;
                cancel_count = 2;
            }
        }
        LostCitierClient.TRY_TO_CHECK_STATUS_FORM_MENU = System.currentTimeMillis()-LostCitierClient.check_status_from_menu_time < LostCitierClient.CHECK_TIMEOUT_MILLIS_SECONDS;
        if (LostCitierClient.TRY_TO_CHECK_STATUS_FORM_MENU) {
            boolean read = component.getString().equals("-----------------主要属性-----------------");
            if (read) {
                LostCitierClient.TRY_TO_CHECK_STATUS_FORM_MENU = false;
                LostCitierClient.check_status_from_menu_time = 0;
                cancel_count = 7;
                checking[0] = true;
                checking[2] = true;
            }
        }
        if (checking[1]) {
            Matcher matcher = Pattern.compile("\\b(dg|du)\\w{5,6}\\b(?=\\W*$)").matcher(component.getString());
            if (matcher.find()){
                LostCitierClient.isOnDungeon[0] = true;
                if (matcher.group().startsWith(" dg")){
                    LostCitierClient.isGaming[0] = true;
                }
                checking[1] = false;
            }else {
                LostCitierClient.isOnDungeon[0] = false;
                LostCitierClient.isGaming[0] = false;
            }
        } else if (checking[2]) {
            Matcher matcher = Pattern.compile("\uD83D\uDEE1防御:(.*?) \uD83D\uDEE1").matcher(component.getString());
            if (matcher.find()){
                HudStatusAndBars.DefenseAmount = Float.parseFloat(matcher.group(1));
                checking[2] = false;
            }
        }
        if (cancel_count-->0) ci.cancel();
        else if (checking[0]) Arrays.fill(checking, false);
    }
}
