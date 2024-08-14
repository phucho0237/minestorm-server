package org.phucho0237;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

public class Main {
    public static void main(String[] args) {
        // Initialize server
        MinecraftServer server = MinecraftServer.init();

        // Create instance
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // Generate the world
        instanceContainer.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK);
        });

        // Add light
        instanceContainer.setChunkSupplier(LightingChunk::new);

        // Event handler
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0,42,42));
        });

        // Run server
        server.start("0.0.0.0", 25565);
    }
}