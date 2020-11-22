package de.verdox.csi.files;

import de.verdox.vcore.files.Configuration;
import org.bukkit.plugin.Plugin;

public class MainConfig extends Configuration {
    public MainConfig(Plugin plugin, String fileName, String pluginDirectory) {
        super(plugin, fileName, pluginDirectory);
    }

    @Override
    public void setupConfig() {
        config.options().copyDefaults(true);
        save();
    }
}
