package com.github.frcsty.nicknamemanager.storage;

import com.github.frcsty.nicknamemanager.NicknamePlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class ConfigStorage {

    private final Map<String, String> contents = new HashMap<>();

    /**
     * Loads the configuration file contents into a manageable HashMap
     *
     * @param plugin Our plugin instance
     */
    public void load(@NotNull final NicknamePlugin plugin) {
        final FileConfiguration configuration = plugin.getConfig();

        for (final String key : configuration.getKeys(false)) {
            final ConfigurationSection section = configuration.getConfigurationSection(key);

            if (section == null) continue;
            section.getKeys(false).forEach(it ->
                    contents.put(key + "." + it, section.getString(it))
            );
        }
    }

    /**
     * @param path Desired content path
     * @return Desired content as a string or input path
     */
    public String getConfigString(@NotNull final String path) {
        return getOrPath(path);
    }

    /**
     * @param path Desired content path
     * @return Desired content or input path
     */
    private String getOrPath(@NotNull final String path) {
        final String content = this.contents.get(path);

        return content == null ? path : content;
    }

}
