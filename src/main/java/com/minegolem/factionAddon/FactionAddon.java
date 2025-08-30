package com.minegolem.factionAddon;

import com.minegolem.factionAddon.commands.ReloadCommand;
import com.minegolem.factionAddon.commands.ShowItemCommand;
import com.minegolem.factionAddon.commands.ShowInvCommand;
import com.minegolem.factionAddon.listeners.InventoryListener;
import com.minegolem.factionAddon.listeners.PlayerListener;
import com.minegolem.factionAddon.listeners.ChatListener;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public final class FactionAddon extends JavaPlugin {

    @Getter
    public final MiniMessage mm = MiniMessage.miniMessage();

    public static final HashMap<UUID, ItemStack> itemMessagesUUID = new HashMap<UUID, ItemStack>();
    public static final HashMap<UUID, PlayerInventory> inventoriesUUID = new HashMap<UUID, PlayerInventory>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        Objects.requireNonNull(getCommand("showitem")).setExecutor(new ShowItemCommand(this));
        Objects.requireNonNull(getCommand("showinv")).setExecutor(new ShowInvCommand(this));
        Objects.requireNonNull(getCommand("factionaddonreload")).setExecutor(new ReloadCommand(this));

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public LuckPerms getLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        return provider != null ? provider.getProvider() : null;
    }
}
