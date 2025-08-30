package com.minegolem.factionAddon.commands;

import com.minegolem.factionAddon.FactionAddon;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ReloadCommand implements CommandExecutor {

    private final FactionAddon plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("factionaddon.reload")) return true;

        plugin.reloadConfig();

        commandSender.sendMessage(plugin.getMm().deserialize("<aqua><b>Config reloaded Successfully!</b></aqua>"));

        return true;
    }
}
