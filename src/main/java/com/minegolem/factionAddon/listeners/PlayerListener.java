package com.minegolem.factionAddon.listeners;

import com.minegolem.factionAddon.FactionAddon;
import com.minegolem.factionAddon.Logger;
import com.minegolem.factionAddon.webhook.DiscordWebhook;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final FactionAddon plugin;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws IOException {
        Player player = event.getPlayer();

        if (!player.hasPermission("staff.notify")) return;

        boolean isOnStaff = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .anyMatch(p -> p.hasPermission("staff.notify"));

        int playerOnline = (int) Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .count();

        if (!isOnStaff) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("webhook.url"));

                webhook.setContent("<@&1119362851811901450>");
                webhook.setAvatarUrl("https://store.quartzmc.it/assets/img/logo.png");
                webhook.setUsername("QuartzMc | Staff Notify!");
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setTitle("**⚠️ Faction Scoperta!**")
                        .setDescription("**<@&1119362851811901450> la modalità è scoperta!**")
                        .setColor(new Color(231, 76, 60))
                        .addField("**Player Online**", "` " + String.valueOf(playerOnline) + " `", true)
                        .addField("**Rilevato da**", "` Console `", true)
                        .addField("**Staff uscito**", "` " + player.getName()  + " `", true)
                        .setFooter(Calendar.getInstance().getTime().toString(), "https://store.quartzmc.it/assets/img/logo.png"));

                try {
                    webhook.execute();

                    Logger.log(Logger.LogLevel.SUCCESS, "Sending discord webhook to notify staff");
                } catch (IOException e) {
                    Logger.log(Logger.LogLevel.ERROR, "Error sending discord webhook to notify staff", e);
                }
            });
        }
    }
}
