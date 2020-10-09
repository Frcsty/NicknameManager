package com.github.frcsty.nicknamemanager.placeholder;

import com.github.frcsty.nicknamemanager.NicknamePlugin;
import com.github.frcsty.nicknamemanager.storage.NicknameProfile;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class NicknamePlaceholder extends PlaceholderExpansion {

    private final NicknamePlugin plugin;

    public NicknamePlaceholder(@NotNull final NicknamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nicknamemanager";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Frcsty";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.0.1-Alpha";
    }

    @Override
    public String onPlaceholderRequest(@NotNull final Player player, @NotNull final String params) {
        final NicknameProfile profile = plugin.getProfileStorage().getProfile(player.getUniqueId());

        if (params.equalsIgnoreCase("nickname")) {
            return profile.getNickname().equalsIgnoreCase("$empty")
                    ? player.getDisplayName()
                    : profile.getNickname();
        }

        return null;
    }
}
