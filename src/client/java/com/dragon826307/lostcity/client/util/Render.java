package com.dragon826307.lostcity.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Render {
    private static int color;
    public static void pushPose(GuiGraphics guiGraphics){
        guiGraphics.pose().pushMatrix();
    }
    public static void popPose(GuiGraphics guiGraphics){
        guiGraphics.pose().popMatrix();
    }
    public static void translate(GuiGraphics guiGraphics,double x,double y,double z){
        guiGraphics.pose().translate((float) x, (float) y);
    }
    public static void translate(GuiGraphics guiGraphics,float x,float y,float z){
        guiGraphics.pose().translate(x,y);
    }
    public static void scale(GuiGraphics guiGraphics,float x,float y,float z){
        guiGraphics.pose().scale(x,y);
    }
    public static void setColor(GuiGraphics guiGraphics,float r,float g,float b,float a){
        color = (int)(a * 255) << 24 | (int)(r * 255) << 16 | (int)(g * 255) << 8 | (int)(b * 255);
    }
    public static void blit(ResourceLocation location,GuiGraphics guiGraphics,int i,int j,int k,int l,int m,int n,int o,int p){
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,location,i,j,k,l,m,n,o,p,color);
    }
    public static void text(GuiGraphics guiGraphics,String text,int x,int y,int color){
        guiGraphics.drawString(Minecraft.getInstance().font,text,x,y,0xFF000000|color);
    }
    public static void text(GuiGraphics guiGraphics, Component component, int x, int y, int color){
        guiGraphics.drawString(Minecraft.getInstance().font, component,x,y,0xFF000000|color);
    }
}
