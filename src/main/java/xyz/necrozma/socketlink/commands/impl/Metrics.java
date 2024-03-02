package xyz.necrozma.socketlink.commands.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.socketlink.websocket.ServerImpl;
import xyz.necrozma.socketlink.websocket.SocketMetrics;;

@Permission(name = "SocketLink.metrics", desc = "Allows metrics command", defaultValue = PermissionDefault.OP)
@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "metrics", desc = "Provides websocket metrics", permission = "SocketLink.metrics", permissionMessage = "You do not have permission to use this command!", usage = "/<command>"))
public class Metrics implements CommandExecutor {
    Logger logger = LoggerFactory.getLogger(Metrics.class);

    private static final SocketMetrics instance = ServerImpl.getMetrics();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("metrics")) {


            Component message = Component.text("----------------- SocketLink Metrics -----------------", TextColor.color(0x00FF00))
                    .append(Component.newline())
                    .append(Component.text("Connections: ", TextColor.color(0x00FF00)))
                    .append(Component.text(instance.getConnections(), TextColor.color(0xFF0000)))
                    .append(Component.newline())
                    .append(Component.text("Messages Sent: ", TextColor.color(0x00FF00)))
                    .append(Component.text(instance.getMessagesSent(), TextColor.color(0xFF0000)))
                    .append(Component.newline())
                    .append(Component.text("Messages Received: ", TextColor.color(0x00FF00)))
                    .append(Component.text(instance.getMessagesReceived(), TextColor.color(0xFF0000)))
                    .append(Component.newline())
                    .append(Component.text("Errors: ", TextColor.color(0x00FF00)))
                    .append(Component.text(instance.getErrors(), TextColor.color(0xFF0000)))
                    .append(Component.newline())
                    .append(Component.text("-----------------------------------------------------", TextColor.color(0x00FF00))
            );

            commandSender.sendMessage(message);

        }
        return true;
    }
}