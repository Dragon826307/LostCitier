package com.dragon826307.lostcity.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Render {
    public static void pushPose(GuiGraphics guiGraphics){
        guiGraphics.pose().pushPose();
    }
    public static void popPose(GuiGraphics guiGraphics){
        guiGraphics.pose().popPose();
    }
    public static <E> void translate(GuiGraphics guiGraphics,E x,E y,E z){
        guiGraphics.pose().translate((Float) x,(Float) y,(Float) z);
    }
    public static <E> void scale(GuiGraphics guiGraphics,E x,E y,E z){
        guiGraphics.pose().translate((Float) x,(Float) y,(Float) z);
    }
    public static void setColor(GuiGraphics guiGraphics,float r,float g,float b,float a){
        guiGraphics.setColor(r,g,b,a);
    }
    public static void blit(ResourceLocation location,GuiGraphics guiGraphics,int i,int j,int k,int l,int m,int n,int o,int p){
        guiGraphics.blit(location,i,j,k,l,m,n,o,p);
    }
    public static void text(GuiGraphics guiGraphics,String text,int x,int y,int color){
        guiGraphics.drawString(Minecraft.getInstance().font,text,x,y,color);
    }
    public static void text(GuiGraphics guiGraphics, Component component, int x, int y, int color){
        guiGraphics.drawString(Minecraft.getInstance().font, component,x,y,color);
    }
}
