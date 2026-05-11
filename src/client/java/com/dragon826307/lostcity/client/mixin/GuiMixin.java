package com.dragon826307.lostcity.client.mixin;

import com.dragon826307.lostcity.client.LostCitierClient;
import com.dragon826307.lostcity.client.hud_bars.HudStatusAndBars;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderHearts",at = @At("HEAD"), cancellable = true)
    private void HealthBarRender(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, float f, int m, int n, int o, boolean bl, CallbackInfo ci){
        if (LostCitierClient.isOnDungeon[0]) {
            HudStatusAndBars.healthBarRender(guiGraphics,player,i,j, f, bl);
            ci.cancel();
        }
    }
    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    private void VehicleHealthRender(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (LostCitierClient.isOnDungeon[0]) {
            HudStatusAndBars.vehicleHealthBarAndFoodBarRender(guiGraphics);
            ci.cancel();
        }
    }
    @Inject(method = "renderFood",at = @At("HEAD"), cancellable = true)
    private void FoodBarRender(GuiGraphics guiGraphics, Player player, int i, int j, CallbackInfo ci){
        if (LostCitierClient.isOnDungeon[0]) {
            HudStatusAndBars.vehicleHealthBarAndFoodBarRender(guiGraphics, player);
            ci.cancel();
        }
    }
}
