package com.dragon826307.lostcity.client;

import com.dragon826307.lostcity.client.light_puzzle.LightPuzzleGui;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.mixin.client.rendering.ClientWorldMixin;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.ClientboundPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class LostCitierClient implements ClientModInitializer {
    public static final Minecraft minecraft = Minecraft.getInstance();
    public static final Component ModID = Component.empty().append("[").append(Component.literal("LostCitier").withStyle(ChatFormatting.BLUE,ChatFormatting.BOLD)).append("]");
    public static final KeyMapping.Category LOSTCITY = new KeyMapping.Category(ResourceLocation.withDefaultNamespace("lostcitier.general"));
    public static KeyMapping openGuiKey;
	@Override
	public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.lostcitier.light_puzzle", InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_O, LOSTCITY));
        //按键监听
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()){
                client.setScreen(new LightPuzzleGui(Component.literal("LightPuzzleGui")));
            }
        });
        //玩家跨越维度事件监听
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((minecraft, world) -> {
            if (!minecraft.isLocalServer()){
                System.out.println(minecraft.level.dimension().location());
            }
        });
	}
}