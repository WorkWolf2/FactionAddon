package com.minegolem.factionAddon.commands;

import com.minegolem.factionAddon.ChatInventoryHolder;
import com.minegolem.factionAddon.FactionAddon;
import com.minegolem.factionAddon.Logger;
import com.minegolem.factionAddon.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("Duplicates")
@RequiredArgsConstructor
public class ShowInvCommand implements CommandExecutor {

    private final FactionAddon plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length == 0) return true;

        if (!UUIDUtils.isValidUUID(args[0])) {
            player.sendMessage(plugin.getMm().deserialize("<aqua><b>QuartzMc</b></aqua> <gray>| Questo inventario non è più disponibilee!</gray>"));
            return true;
        }

        UUID uuid = UUID.fromString(args[0]);

        if (!FactionAddon.inventoriesUUID.containsKey(uuid)) {
            player.sendMessage(plugin.getMm().deserialize("<aqua><b>QuartzMc</b></aqua> <gray>| Questo inventario non è più disponibile!</gray>"));
            return true;
        }

        PlayerInventory playerInv = FactionAddon.inventoriesUUID.get(uuid);

        String playerName = PlainTextComponentSerializer.plainText().serialize(((Player) playerInv.getHolder()).displayName());

        ItemStack[] contents = playerInv.getStorageContents();
        ItemStack[] armorContents = playerInv.getArmorContents();

        for (int i = 0; i < armorContents.length / 2; i++) {
            ItemStack temp = armorContents[i];
            armorContents[i] = armorContents[armorContents.length - 1 - i];
            armorContents[armorContents.length - 1 - i] = temp;
        }

        Inventory inventory = Bukkit.createInventory(new ChatInventoryHolder(), 54, plugin.getMm().deserialize(Objects.requireNonNull(plugin.getConfig().getString("gui.inventory.name")).replace("{player}", playerName)));

        for (int i = 35; i <= 44; i++) {
            ItemStack separator = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta meta = separator.getItemMeta();

            meta.displayName(Component.text(""));
            meta.setHideTooltip(true);
            separator.setItemMeta(meta);

            inventory.setItem(i, separator);
        }

        for (int i = 0; i <= 35; i++) {
            inventory.setItem(i, contents[i]);
        }

        for (int i = 0; i < armorContents.length; i++) {
            inventory.setItem(45 + i, armorContents[i]);
        }

        player.openInventory(inventory);

        Logger.log(Logger.LogLevel.INFO, "Showing " + playerName + "'s inventory to " + player.getName());

        return true;
    }
}
