package com.dragon826307.lostcity.client.util;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

public final class ClientWorldEvents {
    private ClientWorldEvents() {
    }

    public static final Event<@NotNull AfterClientWorldChange> AFTER_CLIENT_WORLD_CHANGE = EventFactory.createArrayBacked(AfterClientWorldChange.class, callbacks -> (client, world) -> {
        for (AfterClientWorldChange callback : callbacks) {
            callback.afterWorldChange(client, world);
        }
    });

    @FunctionalInterface
    public interface AfterClientWorldChange {

        void afterWorldChange(Minecraft client, ClientLevel world);
    }
}
