package com.dragon826307.lostcity.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Render {
    public static void pushPose(GuiGraphics guiGraphics){
        guiGraphics.pose().pushPose();
    }
    public static void popPose(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
    }
    public static void translate(GuiGraphics guiGraphics,double x,double y,double z){
        guiGraphics.pose().translate(x,y,z);
    }
    public static void translate(GuiGraphics guiGraphics,float x,float y,float z){
        guiGraphics.pose().translate(x,y,z);
    }
    public static void scale(GuiGraphics guiGraphics,float x,float y,float z){
        guiGraphics.pose().scale(x,y,z);
    }
    public static void setColor(GuiGraphics guiGraphics,float r,float g,float b,float a){
       RenderSystem.setShaderColor(r,g,b,a);
    }
    public static void blit(ResourceLocation location,GuiGraphics guiGraphics,int i,int j,int k,int l,int m,int n,int o,int p){
        guiGraphics.blit(RenderType::guiTextured,location,i,j,k,l,m,n,o,p);
    }
    public static void text(GuiGraphics guiGraphics,String text,int x,int y,int color){
        guiGraphics.drawString(Minecraft.getInstance().font,text,x,y,color);
    }
    public static void text(GuiGraphics guiGraphics, Component component, int x, int y, int color){
        guiGraphics.drawString(Minecraft.getInstance().font, component,x,y,color);
    }
}
