package com.minegolem.factionAddon.commands;

import com.minegolem.factionAddon.FactionAddon;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CcCommand implements CommandExecutor {

    private final FactionAddon plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            if (!player.hasPermission("factionaddon.clearchat.use")) return true;

            for(Player p : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i <= 100; i++) {
                    if (p.hasPermission("factionaddon.clearchat.bypass")) continue;
                    p.sendMessage(" ");
                }
                p.sendMessage(plugin.mm.deserialize("<green>Chat cancellata da " + player.getName() + "</green>"));
            }
        }

        if (args.length > 1) return true;

        switch(args[0]) {
            case "mute" -> {
                if (!player.hasPermission("factionaddon.clearchat.mute")) return true;

                if (FactionAddon.CHATMUTED) {
                    FactionAddon.CHATMUTED = false;

                    Bukkit.getOnlinePlayers().forEach(p -> {
                        p.sendMessage(plugin.mm.deserialize("<red><b>Chat mutata da" + player.getName() + "</red>"));
                    });
                } else {
                    FactionAddon.CHATMUTED = true;

                    Bukkit.getOnlinePlayers().forEach(p -> {
                        p.sendMessage(plugin.mm.deserialize("<green><b>La chat è ora disponibile!</green>"));
                    });
                }
            }
            case "my" -> {
                if (!player.hasPermission("factionaddon.clearchat.my")) return true;

                for (int i = 1; i <= 100; i++) {
                    player.sendMessage(" ");
                }

                player.sendMessage(plugin.mm.deserialize("<green>Hai cancellato la tua chat!</green>"));
            }
        }

        return true;
    }
}
