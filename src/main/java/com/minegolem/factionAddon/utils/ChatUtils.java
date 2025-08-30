package com.minegolem.factionAddon.utils;

import com.minegolem.factionAddon.FactionAddon;
import com.minegolem.factionAddon.Logger;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

@UtilityClass
public class ChatUtils {

    public boolean hasTooManyCaps(String message) {
        if (message == null || message.isEmpty()) return false;

        int totalLetters = 0;
        int capsCount = 0;

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                totalLetters++;
                if (Character.isUpperCase(c)) {
                    capsCount++;
                }
            }
        }

        if (totalLetters == 0) return false; // nessuna lettera nel messaggio

        if (totalLetters <= 5) return false; // più di 6 lettere per caps

        double percentage = (capsCount * 100.0) / totalLetters;
        return percentage > 85.0;
    }

    public static Component processNormalText(FactionAddon plugin, Player player, String rawMessage, String text, boolean punishCaps) {
        if (punishCaps) {
            text = text.toLowerCase();
        }

        boolean allowColor = player.hasPermission("lpc.colorcodes");
        boolean allowHex = player.hasPermission("lpc.rgbcodes");

        if (allowHex) {
            text = translateHexColorCodes(text);
        }

        return allowColor ? LegacyComponentSerializer.legacyAmpersand().deserialize(text) : Component.text(text);
    }

    public static Component processTag(FactionAddon plugin, Player player, String tag, ItemStack itemInHand) {
        switch (tag) {
            case "item","i" -> {
                if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                    UUID itemMessageUUID = UUID.randomUUID();

                    ClickEvent clickEvent = ClickEvent.runCommand("/showitem " + itemMessageUUID);
                    HoverEvent<HoverEvent.ShowItem> hoverEvent = itemInHand.asHoverEvent();

                    Component itemComponent = itemInHand.displayName()
                            .hoverEvent(hoverEvent)
                            .clickEvent(clickEvent);

                    FactionAddon.itemMessagesUUID.put(itemMessageUUID, itemInHand);

                    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->
                            FactionAddon.itemMessagesUUID.remove(itemMessageUUID),
                            20L * 30);

                    return itemComponent;
                }
                return Component.empty();
            }
            case "inventory","inv" -> {
                UUID invMessageUUID = UUID.randomUUID();

                ClickEvent clickEvent = ClickEvent.runCommand("/showinv " + invMessageUUID);

                Component invComponent = plugin.getMm().deserialize(
                        "<aqua><i>[Inventario di " + player.getName() + "]</i></aqua>"
                ).clickEvent(clickEvent);

                FactionAddon.inventoriesUUID.put(invMessageUUID, player.getInventory());
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->
                        FactionAddon.inventoriesUUID.remove(invMessageUUID),
                        20L * 30);

                return invComponent;
            }

            default -> {
                return Component.empty();
            }
        }
    }

    private static final java.util.regex.Pattern HEX_PATTERN = java.util.regex.Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String translateHexColorCodes(final String message) {
        final char colorChar = '\u00A7'; // '§'
        final java.util.regex.Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find()) {
            final String group = matcher.group(1);
            matcher.appendReplacement(buffer, "" + colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }
        return matcher.appendTail(buffer).toString();
    }

    public static String applyPlaceholders(Player player, String format, CachedMetaData meta) {
        return format
                .replace("{prefix}", safe(meta.getPrefix()))
                .replace("{suffix}", safe(meta.getSuffix()))
                .replace("{world}", player.getWorld().getName())
                .replace("{name}", player.getName())
                .replace("{displayname}", PlainTextComponentSerializer.plainText().serialize(player.displayName()))
                .replace("{username-color}", safe(meta.getMetaValue("username-color")))
                .replace("{message-color}", safe(meta.getMetaValue("message-color")))
                // PlaceholderAPI
                .transform(f -> Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")
                        ? PlaceholderAPI.setPlaceholders(player, f)
                        : f);
    }

    private String safe(String value) {
        return value != null ? value : "";
    }

}
