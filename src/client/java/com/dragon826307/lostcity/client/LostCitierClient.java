package com.dragon826307.lostcity.client;

import com.dragon826307.lostcity.client.light_puzzle.LightPuzzleGui;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class LostCitierClient implements ClientModInitializer {
    public static KeyMapping openGuiKey;
	@Override
	public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.lostcitier.light_puzzle", InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_O,"key.category.minecraft.lostcitier.general"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()){
                client.setScreen(new LightPuzzleGui(Component.literal("LightPuzzleGui")));
            }
        });
	}
    public static Component modID(){
        return Component.empty().append("[").append(Component.literal("LostCitier").withStyle(ChatFormatting.BLUE,ChatFormatting.BOLD)).append("]");
    }
}