package com.dragon826307.lostcity.client.commands;

import com.dragon826307.lostcity.client.LostCitierClient;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class DebugCommand {
    public static LiteralArgumentBuilder<FabricClientCommandSource> debugMode(String string, Minecraft minecraft) {
        return ClientCommandManager.literal(string).executes(context -> {
            minecraft.gui.getChat().addMessage(Component.literal(LostCitierClient.debugMode?"Debug mode is currently ON":"Debug mode is currently OFF"));
            return 1;
        }).then(ClientCommandManager.argument(string, BoolArgumentType.bool()).executes(context -> {
            LostCitierClient.debugMode = BoolArgumentType.getBool(context, string);
            minecraft.gui.getChat().addMessage(Component.literal(LostCitierClient.debugMode?"Debug mode ON":"Debug mode OFF"));
            return 1;
        }));
    }
    public static LiteralArgumentBuilder<FabricClientCommandSource> debugBoolean(String string, Minecraft minecraft, boolean[] bl) {
        return ClientCommandManager.literal("debug").requires(fabricClientCommandSource -> LostCitierClient.debugMode).then(ClientCommandManager.literal(string).executes(context -> {
            minecraft.gui.getChat().addMessage(Component.literal(string+" is "+bl[0]));
            return 1;
        }).then(ClientCommandManager.argument(string, BoolArgumentType.bool()).executes(context -> {
            bl[0] = BoolArgumentType.getBool(context, string);
            minecraft.gui.getChat().addMessage(Component.literal("Set "+string+" to "+ bl[0]));
            return 1;
        })));
    }
}
