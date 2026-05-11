package com.dragon826307.lostcity.client.hud_bars;

import com.dragon826307.lostcity.client.LostCitierClient;
import com.dragon826307.lostcity.client.util.Render;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.swing.*;

public class HudStatusAndBars {
    private static Player player;
    private static final ResourceLocation BAR_BACK = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/bar_back.png");
    private static final ResourceLocation BAR_FILL = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/bar_fill.png");
    private static final ResourceLocation HEART = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/full.png");
    private static final ResourceLocation ABSORPTION = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/absorbing_full.png");
    private static final ResourceLocation FOOD = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/food_full.png");
    private static final ResourceLocation VEHICLE = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/vehicle_full.png");
    private static final ResourceLocation DEFENSE = ResourceLocation.fromNamespaceAndPath(LostCitierClient.mod_id,"textures/gui/hud/armor_full.png");
    private static int FoodBarLeft;
    private static int FoodBarTop;
    public static float DefenseAmount;
    public static void healthBarRender(GuiGraphics guiGraphics, Player player, int left, int top, float maxHealth, boolean highlight){
        FoodBarTop = top;
        FoodBarLeft = left+98;
        HudStatusAndBars.player = player;
        int health_bar_length = (int) (84*player.getHealth()/maxHealth);
        float highlight_bar = highlight ? 1.8f : 1f;
        Render.pushPose(guiGraphics);
        if (health_bar_length<16) {
            Render.setColor(guiGraphics,1f, (float) (0.3f - 0.3f * Math.sin(System.currentTimeMillis() * 0.005)), (float) (0.3f - 0.3f * Math.sin(System.currentTimeMillis() * 0.005)), 1);
        }else {
            Render.setColor(guiGraphics,1f,0.6f,0.6f,1);
        }
        Render.blit(BAR_BACK,guiGraphics,left,top+4,84,5, 84,5,84,5);
        Render.setColor(guiGraphics,0.86f*highlight_bar,0.07f*highlight_bar,0.06f*highlight_bar,1);
        Render.blit(BAR_FILL,guiGraphics,left,top+4,84,5, health_bar_length,5,84,5);
        if (player.getAbsorptionAmount()>0){
            Render.setColor(guiGraphics,0.76f,0.73f,0.17f,1);
            Render.blit(BAR_BACK,guiGraphics,left,top-2,84,5, 84,5,84,5);
            Render.blit(BAR_FILL,guiGraphics,left,top-2,84,5, (int) Math.min(84,84*player.getAbsorptionAmount()/maxHealth),5,84,5);
            Render.translate(guiGraphics,0,-6,0);
            Render.setColor(guiGraphics,1f,1f,1f,1);
            Render.blit(ABSORPTION,guiGraphics,left+53,top-5,9,9,9,9,9,9);
            Render.text(guiGraphics, String.valueOf(Math.round(player.getAbsorptionAmount()*10)/10f),left+62,top-4,0xFFFF00);
        }
        Render.setColor(guiGraphics,0.8f,0.8f,0.8f,1);
        Render.blit(HEART,guiGraphics,left,top-5,9,9,9,9,9,9);
        Render.text(guiGraphics, Math.round(player.getHealth() * 100) / 100f + "/" +Math.round(maxHealth),left+9,top-4,0xFF0000);
        Render.setColor(guiGraphics,1,1,1,1);
        DefenseRender(guiGraphics,left,top);
        if (player.getAbsorptionAmount()>0)Render.translate(guiGraphics,0,6,0);
        Render.popPose(guiGraphics);
    }
    public static void vehicleHealthBarAndFoodBarRender(GuiGraphics guiGraphics){
        vehicleHealthBarAndFoodBarRender(guiGraphics,HudStatusAndBars.player);
    }
    public static void vehicleHealthBarAndFoodBarRender(GuiGraphics guiGraphics, Player player){
        int food_bar_length = 84*player.getFoodData().getFoodLevel()/20;
        LivingEntity livingEntity = (LivingEntity) player.getVehicle();
        Render.pushPose(guiGraphics);
        if (player.getFoodData().getFoodLevel() <7) {
            Render.setColor(guiGraphics,0.9f, (float) (0.56f - 0.3f * Math.sin(System.currentTimeMillis() * 0.005)), (float) (0.31f - 0.3f * Math.sin(System.currentTimeMillis() * 0.005)), 1);
        }else {
            Render.setColor(guiGraphics,0.81f,0.56f,0.29f,1);
        }
        Render.blit(BAR_BACK,guiGraphics, FoodBarLeft, FoodBarTop+4,84,5, 84,5,84,5);
        Render.setColor(guiGraphics,0.81f,0.56f,0.29f,1);
        Render.blit(BAR_FILL, guiGraphics,FoodBarLeft, FoodBarTop+4,84,5, food_bar_length,5,84,5);
        if (livingEntity != null) {
            Render.setColor(guiGraphics,0.5f,0.22f,0.1f,1);
            Render.blit(BAR_BACK, guiGraphics,FoodBarLeft,FoodBarTop-2,84,5, 84,5,84,5);
            Render.blit(BAR_FILL, guiGraphics,FoodBarLeft,FoodBarTop-2,84,5, (int) (84*livingEntity.getHealth()/livingEntity.getMaxHealth()),5,84,5);
            Render.text(guiGraphics, String.valueOf(Math.round(livingEntity.getHealth()*10)/10f),FoodBarLeft+32,FoodBarTop-10,0xFFFFFF);
            Render.translate(guiGraphics,0,-6,0);
            Render.setColor(guiGraphics,1f,1f,1f,1);
            Render.blit(VEHICLE,guiGraphics,FoodBarLeft+23,FoodBarTop-5,9,9,9,9,9,9);
        }else {
            Render.setColor(guiGraphics,1,1,1,1);
        }
        Render.blit(FOOD,guiGraphics,FoodBarLeft,FoodBarTop-5,9,9,9,9,9,9);
        Render.text(guiGraphics, String.valueOf(player.getFoodData().getFoodLevel()),FoodBarLeft+9,FoodBarTop-4,0xD08F4A);
        if (livingEntity != null)Render.translate(guiGraphics,0,6,0);
        Render.popPose(guiGraphics);
    }
    private static void DefenseRender(GuiGraphics guiGraphics, int left ,int top){
        Render.pushPose(guiGraphics);
        Render.setColor(guiGraphics,0.07f,0.98f,1f,1);
        Render.blit(DEFENSE,guiGraphics,left,top-15,9,9,9,9,9,9);
        Render.setColor(guiGraphics,1,1,1,1);
        Render.text(guiGraphics, String.valueOf(DefenseAmount),left+10,top-14,0x00FFFF);
        Render.popPose(guiGraphics);
    }
}
