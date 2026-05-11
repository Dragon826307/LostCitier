package com.dragon826307.lostcity.client;

import com.dragon826307.lostcity.client.commands.DebugCommand;
import com.dragon826307.lostcity.client.light_puzzle.LightPuzzleGui;
import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class LostCitierClient implements ClientModInitializer {
    public static long check_sub_server_time = 0;
    public static boolean TRY_TO_CHECK_SERVER = false;
    public static long check_status_from_menu_time = 0;
    public static boolean TRY_TO_CHECK_STATUS_FORM_MENU = false;
    public static final int CHECK_TIMEOUT_MILLIS_SECONDS = 500;
    public static boolean debugMode = false;
    public static boolean[] isOnDungeon = {false};
    public static boolean[] isGaming = {false};
    protected static Minecraft minecraft ;
    public static final String mod_id = "lostcitier";
    public static final Component ModID = Component.empty().append("[").append(Component.literal("LostCitier").withStyle(ChatFormatting.BLUE,ChatFormatting.BOLD)).append("]");
    public static KeyMapping openGuiKey;
	@Override
	public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.lostcitier.light_puzzle", InputConstants.Type.KEYSYM,GLFW.GLFW_KEY_O,"key.category.minecraft.lostcitier.general"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            minecraft = client;
            if (minecraft != null && minecraft.level != null && minecraft.level.getGameTime() % 20 == 0) {
                //noinspection DataFlowIssue
                if (minecraft.getConnection() != null && minecraft.level.getGameTime() % 100 == 0) {
                    tickPer5Second();
                }
            }
            while (openGuiKey.consumeClick()){
                //noinspection DataFlowIssue
                client.setScreen(new LightPuzzleGui(Component.literal("LightPuzzleGui")));
            }
        });
        //玩家跨服事件监听
        ClientPlayConnectionEvents.JOIN.register((clientPacketListener, packetSender, minecraft) -> {
            //noinspection DataFlowIssue
            String serverIP = minecraft.getCurrentServer().ip;
            if (debugMode) minecraft.gui.getChat().addMessage(Component.literal(serverIP));
            if (serverIP.endsWith("t")){
                TRY_TO_CHECK_SERVER = true;
                check_sub_server_time = System.currentTimeMillis();
                //noinspection DataFlowIssue
                minecraft.getConnection().send(new ServerboundChatCommandPacket("server"));
            }
        });
        //指令注册
        ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> commandDispatcher
                .register(ClientCommandManager.literal("lostcitier")
                        .then(DebugCommand.debugMode("debug_mode",minecraft))
                        .then(DebugCommand.debugBoolean("isOnDungeon",minecraft,isOnDungeon))
                        .then(DebugCommand.debugBoolean("isGaming",minecraft,isGaming))
                        .then(ClientCommandManager.literal("open_light_puzzle_gui").executes(context -> {
                            minecraft.setScreen(new LightPuzzleGui(Component.literal("LightPuzzleGui")));
                            return 1;
                        })))));
	}
    private static void tickPer5Second(){
        if (isOnDungeon[0]) openMenuAndCheckStatus();
    }
    private static void openMenuAndCheckStatus(){
        TRY_TO_CHECK_STATUS_FORM_MENU = true;
        check_status_from_menu_time = System.currentTimeMillis();
        //noinspection DataFlowIssue
        if (minecraft.player.containerMenu instanceof InventoryMenu) Objects.requireNonNull(minecraft.getConnection()).send(new ServerboundChatCommandPacket("cd"));
    }
    public static void menuEngagement(ClientboundOpenScreenPacket clientboundOpenScreenPacket) {
        //noinspection DataFlowIssue
        minecraft.getConnection().send(new ServerboundContainerClickPacket(clientboundOpenScreenPacket.getContainerId(),minecraft.player.containerMenu.getStateId(),10,0,ClickType.PICKUP,ItemStack.EMPTY,new Int2ObjectOpenHashMap<>()));
        minecraft.getConnection().send(new ServerboundContainerClosePacket(clientboundOpenScreenPacket.getContainerId()));
    }
}