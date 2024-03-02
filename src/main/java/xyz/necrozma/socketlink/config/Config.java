package xyz.necrozma.socketlink.config;


import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.route.Route;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.necrozma.socketlink.SocketLink;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Config {
    private static Config instance;
    private YamlDocument config;

    private static Logger logger = LoggerFactory.getLogger(Config.class);

    private Config() throws IOException {
        config = YamlDocument.create(
                new File(SocketLink.getPlugin(SocketLink.class).getDataFolder(), "config.yml"),
                Objects.requireNonNull(SocketLink.getPlugin(SocketLink.class).getResource("config.yml")),
                GeneralSettings.DEFAULT,
                LoaderSettings.DEFAULT,
                DumperSettings.DEFAULT
        );
    }

    public static Config getInstance() {
        if (instance == null) {
            try {
                instance = new Config();
            } catch (IOException e) {
                logger.error("Error creating config!");
            }
        }
        return instance;
    }

    public void Reload() {
        try {
            instance.config.reload();
        } catch (Exception e) {
            logger.error("Error reloading config!");
        }
    }

    public String getString(Route route) {
        return config.getString(route);
    }

    public Boolean getBoolean(Route route) {
        return config.getBoolean(route);
    }

    public Integer getInteger(Route route) {
        return config.getInt(route);
    }
}
