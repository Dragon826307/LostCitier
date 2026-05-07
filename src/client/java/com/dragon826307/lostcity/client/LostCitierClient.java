package com.dragon826307.lostcity.client;

import com.dragon826307.lostcity.client.light_puzzle.LightPuzzleGui;
import com.dragon826307.lostcity.client.util.ClientWorldEvents;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.telemetry.events.WorldLoadEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class LostCitierClient implements ClientModInitializer {
    public static boolean DEBUG = true;
    protected Minecraft minecraft ;
    public static final Component ModID = Component.empty().append("[").append(Component.literal("LostCitier").withStyle(ChatFormatting.BLUE,ChatFormatting.BOLD)).append("]");
    public static KeyMapping openGuiKey;
	@Override
	public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.lostcitier.light_puzzle", InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_O,"key.category.minecraft.lostcitier.general"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()){
                client.setScreen(new LightPuzzleGui(Component.literal("LightPuzzleGui")));
            }
        });
        //玩家跨越维度事件监听
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((minecraft, world) -> {
            this.minecraft = minecraft;
            if (!minecraft.isLocalServer()){
                if (DEBUG){
                    minecraft.gui.getChat().addMessage(Component.empty().append("[Debug]").append(world.dimension().location().toString()));
//                    if (minecraft.getChatStatus()){
                    minecraft.gui.getChat().addMessage(Component.empty().append("[Debug]").append(minecraft.getChatStatus().getMessage()));
//                }
            }
        }});
        //指令注册
        ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> {
            commandDispatcher.register(ClientCommandManager.literal("lostcitier").then(ClientCommandManager.literal("debug").then(ClientCommandManager.argument("debug", BoolArgumentType.bool()).executes(context -> {
                DEBUG = BoolArgumentType.getBool(context, "debug");
                minecraft.gui.getChat().addMessage(Component.literal(DEBUG ? "Debug Mode on" : "Debug Mode off"));
                return 1;
            }))).then(ClientCommandManager.literal("debug").executes(context -> {
                DEBUG = !DEBUG;
                minecraft.gui.getChat().addMessage(Component.literal(DEBUG ? "Debug Mode on" : "Debug Mode off"));
                return 1;
            })));
        }));
	}
}