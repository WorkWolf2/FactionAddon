package com.minegolem.factionAddon.listeners;

import com.minegolem.factionAddon.FactionAddon;
import com.minegolem.factionAddon.utils.ChatUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ChatListener implements Listener, ChatRenderer {

    private final FactionAddon plugin;

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (FactionAddon.CHATMUTED) {
            if (!player.hasPermission("factionaddon.clearchat.mutebypass")) {
                event.setCancelled(true);

                player.sendMessage(plugin.mm.deserialize("<red>La chat è disabilita!</red>"));
                return;
            }
        }

        event.renderer(this);

        String rawMessage = PlainTextComponentSerializer.plainText().serialize(event.message());
        Component finalMessage = Component.empty();
        int lastEnd = 0;

        boolean punishCaps = ChatUtils.hasTooManyCaps(rawMessage) && !player.hasPermission("staff.caps");

        Pattern combined = Pattern.compile("(?i)\\[(?:item|i|inventory|inv)\\]");
        Matcher matcher = combined.matcher(rawMessage);

        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                String normalText = rawMessage.substring(lastEnd, matcher.start());
                finalMessage = finalMessage.append(ChatUtils.processNormalText(plugin, player, rawMessage, normalText, punishCaps));
            }

            String match = matcher.group();
            String tag = match.substring(1, match.length() - 1).toLowerCase();
            finalMessage = finalMessage.append(ChatUtils.processTag(plugin, player, tag, itemInHand));

            lastEnd = matcher.end();
        }

        if (lastEnd < rawMessage.length()) {
            String normalText = rawMessage.substring(lastEnd);
            finalMessage = finalMessage.append(ChatUtils.processNormalText(plugin, player, rawMessage, normalText, punishCaps));
        }

        if (punishCaps && plugin.getConfig().getBoolean("warnOnCaps")) {
            Bukkit.getScheduler().runTask(plugin,
                    () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warn " + player.getName() + " CAPS"));
        }

        event.message(finalMessage);
    }

    @Override
    public @NotNull Component render(Player player, Component sourceDisplayName, Component message, Audience viewer) {
       LuckPerms luckPerms = plugin.getLuckPerms();
       if (luckPerms == null) {
           return Component.text(player.getName() + ": ").append(message); // fallback
       }

       CachedMetaData meta = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);

       String group = meta.getPrimaryGroup().toLowerCase();
       String basePath = plugin.getConfig().isSet("group-formats." + group)
               ? "group-formats." + group
               : "chat-format";

       String format = plugin.getConfig().getString(basePath, "{prefix}{name}&7: {message}");

       format = ChatUtils.applyPlaceholders(player, format, meta);

       String colorized = ChatUtils.translateHexColorCodes(format);

       int msgIndex = colorized.indexOf("{message}");

       Component prefix, suffix;
       if (msgIndex >= 0) {
           String pre = colorized.substring(0, msgIndex);
           String post = colorized.substring(msgIndex + "{message}".length());

           prefix = LegacyComponentSerializer.legacyAmpersand().deserialize(pre);
           suffix = LegacyComponentSerializer.legacyAmpersand().deserialize(post);
       } else {
           prefix = LegacyComponentSerializer.legacyAmpersand().deserialize(colorized);
           suffix = Component.empty();
       }

        message = message.style(prefix.style());
        return prefix.append(message).append(suffix);

    }


}