package com.github.frcsty.nicknamemanager;

import com.github.frcsty.nicknamemanager.command.NicknameCommand;
import com.github.frcsty.nicknamemanager.command.ReloadCommand;
import com.github.frcsty.nicknamemanager.command.ResetCommand;
import com.github.frcsty.nicknamemanager.placeholder.NicknamePlaceholder;
import com.github.frcsty.nicknamemanager.storage.ConfigStorage;
import com.github.frcsty.nicknamemanager.storage.ProfileStorage;
import com.github.frcsty.nicknamemanager.util.Message;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.MessageHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;

public final class NicknamePlugin extends JavaPlugin {

    private final ProfileStorage profileStorage = new ProfileStorage();
    private final ConfigStorage configStorage = new ConfigStorage();

    @Override
    public void onEnable() {
        final NicknamePlugin plugin = this;
        saveDefaultConfig();

        try {
            this.profileStorage.load(this);
            this.configStorage.load(this);
        } catch (final IOException ex) {
            this.getLogger().log(Level.WARNING, "Failed to load data from 'data.yml'!");
        }

        final CommandManager commandManager = new CommandManager(this);
        commandManager.register(
                new NicknameCommand(this),
                new ReloadCommand(this),
                new ResetCommand(this)
        );
        registerCommandMessage(commandManager);

        new NicknamePlaceholder(this).register();

        final long time = Long.valueOf(this.configStorage.getConfigString("settings.auto-save-interval")) * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    profileStorage.save(plugin);
                } catch (final IOException ex) {
                    plugin.getLogger().log(Level.WARNING, "Failed to save data from ProfileStorage.java!", ex);
                }
            }
        }.runTaskTimer(this, time, time);
    }

    @Override
    public void onDisable() {
        reloadConfig();

        try {
            this.profileStorage.save(this);
        } catch (final IOException ex) {
            this.getLogger().log(Level.WARNING, "Failed to save data from ProfileStorage.java!", ex);
        }
    }

    /**
     * @return Returns a loaded instance of {@link ProfileStorage}
     */
    public ProfileStorage getProfileStorage() {
        return this.profileStorage;
    }

    /**
     * @return Returns a loaded instance of {@link ConfigStorage}
     */
    public ConfigStorage getConfigStorage() {
        return this.configStorage;
    }

    /**
     * Registers our command messages
     *
     * @param manager Our loaded {@link CommandManager} instance
     */
    private void registerCommandMessage(@NotNull final CommandManager manager) {
        final MessageHandler handler = manager.getMessageHandler();

        for (final MessageIdentifier message : MessageIdentifier.values()) {
            handler.register(message.getIdentifier(), sender ->
                    Message.send(sender, this.getConfigStorage().getConfigString("message." + message.getPath()))
            );
        }
    }

    /**
     * Provides identifiers for all our custom command messages,
     * and their config paths
     */
    private enum MessageIdentifier {
        PLAYER_ONLY("cmd.no.console", "player-only"),
        WRONG_USAGE("cmd.wrong.usage", "wrong-usage"),
        NO_PERMISSION("cmd.no.permission", "no-permission"),
        UNKNOWN_COMMAND("cmd.no.exists", "unknown-command");

        private final String identifier;
        private final String path;

        MessageIdentifier(@NotNull final String identifier, @NotNull final String path) {
            this.identifier = identifier;
            this.path = path;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public String getPath() {
            return this.path;
        }
    }

}
