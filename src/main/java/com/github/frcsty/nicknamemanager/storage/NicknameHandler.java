package com.github.frcsty.nicknamemanager.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public final class NicknameHandler {

    private final Map<UUID, NicknameProfile> profiles;

    NicknameHandler(@NotNull final Map<UUID, NicknameProfile> profiles) {
        this.profiles = profiles;
    }

    public void setNickname(@NotNull final UUID identifier, final String nickname) {
        final NicknameProfile profile = this.profiles.getOrDefault(identifier, new NicknameProfile());

        profile.setNickname(nickname);
        this.profiles.put(identifier, profile);
    }

    public void resetNickname(@NotNull final UUID identifier) {
        final NicknameProfile profile = this.profiles.getOrDefault(identifier, new NicknameProfile());

        profile.setNickname("");
        this.profiles.put(identifier, profile);
    }
}
