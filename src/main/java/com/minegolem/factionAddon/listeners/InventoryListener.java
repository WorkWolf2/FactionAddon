package com.minegolem.factionAddon.listeners;

import com.minegolem.factionAddon.ChatInventoryHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof ChatInventoryHolder) {
            event.setCancelled(true);
        }
    }
}
