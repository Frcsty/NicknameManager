package com.github.frcsty.nicknamemanager.storage;

import com.github.frcsty.nicknamemanager.NicknamePlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("ResultOfMethodCallIgnored")
public final class ProfileStorage {

    private static final String FILE_IDENTIFIER = "data.yml";
    private static final String DATA_SECTION = "data";

    private final Map<UUID, NicknameProfile> profiles = new HashMap<>();
    private final NicknameHandler nicknameHandler;

    public ProfileStorage() {
        this.nicknameHandler = new NicknameHandler(profiles);
    }

    /**
     * Loads our "data.yml" file contents into our profiles HashMap
     *
     * @param plugin Our plugin instance {@link NicknamePlugin}
     * @throws IOException Thrown exception
     */
    public void load(@NotNull final NicknamePlugin plugin) throws IOException {
        final Object[] contents = getFileContents(plugin);
        final ConfigurationSection userData = (ConfigurationSection) contents[2];

        if (userData == null) {
            return;
        }

        for (final String user : userData.getKeys(false)) {
            final NicknameProfile profile = new NicknameProfile();

            profile.setNickname(userData.getString(DATA_SECTION + "." + user + ".nickname"));
            profiles.put(UUID.fromString(user), profile);
        }
    }

    /**
     * Saves our HashMap data into our "data.yml" file
     *
     * @param plugin Our plugin instance {@link NicknamePlugin}
     * @throws IOException Thrown exception
     */
    public void save(@NotNull final NicknamePlugin plugin) throws IOException {
        final Object[] contents = getFileContents(plugin);
        final ConfigurationSection userData = (ConfigurationSection) contents[2];

        if (userData == null) {
            return;
        }

        for (final UUID user : profiles.keySet()) {
            final NicknameProfile profile = profiles.get(user);

            if (profile == null) continue;

            userData.set(DATA_SECTION + "." + user + ".nickname", profile.getNickname());
        }

        ((FileConfiguration) contents[1]).save((File) contents[0]);
    }

    /**
     * Returns a {@link Object[]} of our user data file contents
     *
     * @param plugin Our plugin instance {@link NicknamePlugin}
     * @return User data file contents from file "data.yml"
     * @throws IOException Thrown exception
     */
    private Object[] getFileContents(@NotNull final NicknamePlugin plugin) throws IOException {
        final File file = new File(plugin.getDataFolder() + "/" + FILE_IDENTIFIER);
        Object[] result = new Object[]{null, null, null};

        if (!file.exists()) {
            file.createNewFile();
        }

        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final ConfigurationSection section = configuration.getConfigurationSection(DATA_SECTION);

        result[0] = file;
        result[1] = configuration;
        result[2] = section;

        return result;
    }

    /**
     * Returns an existing profile, or creates one if one is not already present.
     *
     * @param identifier Desired profile's player UUID
     * @return A {@link NicknameProfile} object for desired player
     */
    public NicknameProfile getProfile(@NotNull final UUID identifier) {
        return this.profiles.getOrDefault(identifier, new NicknameProfile());
    }

    /**
     * @return Returns a {@link NicknameHandler} to manage player profiles
     */
    public NicknameHandler getNicknameHandler() {
        return this.nicknameHandler;
    }
}
