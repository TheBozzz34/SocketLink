package xyz.necrozma.socketlink;


import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.route.Route;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.necrozma.socketlink.commands.CommandManager;
import xyz.necrozma.socketlink.config.Config;
import xyz.necrozma.socketlink.packets.ProtocolManagerListener;
import xyz.necrozma.socketlink.websocket.ServerImpl;

import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.logging.Logger;

@Plugin(name="SocketLink", version="1.0")
@Description(value = "SocketLink allows you to link your Minecraft server with a web server using WebSockets.")
@Author(value = "Necrozma")
@Website(value = "necrozma.xyz")
@Dependency(value = "ProtocolLib")
@ApiVersion(ApiVersion.Target.v1_20)
public final class SocketLink extends JavaPlugin {
    private static final Logger logger = Logger.getLogger("SocketLink");
    private static ServerImpl s;

    @Getter
    private static SocketLink plugin;

    public SocketLink() {
    }

    @Override
    public void onEnable() {
        plugin = this;

        Config configManager = Config.getInstance();

        int port = 0;
        try {
            port = configManager.getInteger((Route.from("socket.port")));
            s = new ServerImpl(port);
        } catch (UnknownHostException e) {
            logger.severe("Failed to start server on port " + port);
            throw new RuntimeException(e);
        }

        s.start();

        ProtocolManagerListener.initialize(this, s);
        Metrics metrics = new Metrics(this, 21194);

        s.setReuseAddr(true);

        CommandManager commandManager = new CommandManager(this);

        commandManager.registerCommands();

    }

    @Override
    public void onDisable() {
        try {
            s.stop();
        } catch (InterruptedException e) {
            logger.severe("Failed to stop server");
            throw new RuntimeException(e);
        }
    }
}
