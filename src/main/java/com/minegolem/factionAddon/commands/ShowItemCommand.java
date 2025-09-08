package com.minegolem.factionAddon.commands;

import com.minegolem.factionAddon.ChatInventoryHolder;
import com.minegolem.factionAddon.FactionAddon;
import com.minegolem.factionAddon.Logger;
import com.minegolem.factionAddon.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class ShowItemCommand implements CommandExecutor {

    private final FactionAddon plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length == 0) return true;

        if (!UUIDUtils.isValidUUID(args[0])) {
            player.sendMessage(plugin.getMm().deserialize("<aqua><b>QuartzMc</b></aqua> <gray>| Questo item non è più disponibile!</gray>"));
            return true;
        }

        UUID uuid = UUID.fromString(args[0]);

        if (!FactionAddon.itemMessagesUUID.containsKey(uuid)) {
            player.sendMessage(plugin.getMm().deserialize("<aqua><b>QuartzMc</b></aqua> <gray>| Questo item non è più disponibile!</gray>"));
            return true;
        }

        ItemStack itemToShow = FactionAddon.itemMessagesUUID.get(uuid);

        Component itemName = itemToShow.getItemMeta().hasDisplayName() || itemToShow.getItemMeta() != null ? itemToShow.displayName() : Component.text(itemToShow.getType().name().toLowerCase().replace('_', ' '));

        Inventory inventory = Bukkit.createInventory( new ChatInventoryHolder(), 27, itemName);
        inventory.setItem(13, itemToShow);

        player.openInventory(inventory);

        Logger.log(Logger.LogLevel.INFO, "Showing chat item to " + player.getName());

        return true;
    }
}
