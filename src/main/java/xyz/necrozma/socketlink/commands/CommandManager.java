package xyz.necrozma.socketlink.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandManager {
    private final JavaPlugin plugin;

    Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        try {
            setCommandExecutor("metrics", new xyz.necrozma.socketlink.commands.impl.Metrics());
            logger.info("Successfully Loaded Commands");
        } catch (Exception e) {
            logger.error("Failed to load commands: " + e);
        }
    }
    private void setCommandExecutor(String commandName, CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(commandName);
        if (command != null) {
            command.setExecutor(executor);
        } else {
            logger.warn("Failed to set executor for command: " + commandName);
        }
    }

}