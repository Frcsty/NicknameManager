package com.github.frcsty.nicknamemanager.storage;

import org.jetbrains.annotations.NotNull;

public final class NicknameProfile {

    private static final String DEFAULT_VALUE = "$empty";
    private String nickname = DEFAULT_VALUE;

    void setNickname(final String value) {
        if (value == null || value.length() == 0) {
            this.nickname = DEFAULT_VALUE;
        }

        this.nickname = value;
    }

    public @NotNull String getNickname() {
        return this.nickname;
    }

}
