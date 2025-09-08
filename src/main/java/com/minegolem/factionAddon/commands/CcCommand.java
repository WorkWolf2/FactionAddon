package com.minegolem.factionAddon.commands;

import com.minegolem.factionAddon.FactionAddon;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class CcCommand implements CommandExecutor, TabCompleter {

    private final FactionAddon plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {
            clearChat(player);

            return true;
        }

        if (args.length > 1) return true;

        switch(args[0]) {
            case "mute" -> {
                if (!player.hasPermission("factionaddon.clearchat.mute")) return true;

                if (FactionAddon.CHATMUTED) {
                    FactionAddon.CHATMUTED = false;

                    Bukkit.getOnlinePlayers().forEach(p -> {
                        p.sendMessage(plugin.mm.deserialize("<green><b>La chat è ora disponibile!</green>"));
                    });
                } else {
                    FactionAddon.CHATMUTED = true;

                    Bukkit.getOnlinePlayers().forEach(p -> {
                        p.sendMessage(plugin.mm.deserialize("<red><b>Chat mutata da " + player.getName() + "</red>"));
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
            default -> {
                clearChat(player);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("mute", "my");
        }
        return List.of();
    }

    private void clearChat(Player player) {
        if (!player.hasPermission("factionaddon.clearchat.use")) return;

        for(Player p : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i <= 100; i++) {
                if (p.hasPermission("factionaddon.clearchat.bypass")) continue;
                p.sendMessage(" ");
            }
            p.sendMessage(plugin.mm.deserialize("<green>Chat cancellata da " + player.getName() + "</green>"));
        }
    }
}
