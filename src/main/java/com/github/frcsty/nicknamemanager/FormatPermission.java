package com.github.frcsty.nicknamemanager;

import me.mattstudios.mfmsg.base.internal.Format;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum FormatPermission {

    BOLD("nicknamemanager.format.bold", Format.BOLD),
    ITALIC("nicknamemanager.format.italic", Format.ITALIC),
    STRIKETHROUGH("nicknamemanager.format.strikethrough", Format.STRIKETHROUGH),
    UNDERLINED("nicknamemanager.format.underlined", Format.UNDERLINE),
    OBFUSCATED("nicknamemanager.format.obfuscated", Format.OBFUSCATED),
    LEGACY_FORMAT("nicknamemanager.format.legacy",
            Format.LEGACY_BOLD, Format.LEGACY_ITALIC,
            Format.LEGACY_OBFUSCATED, Format.LEGACY_STRIKETHROUGH,
            Format.LEGACY_UNDERLINE
    ),

    LEGACY_COLOR("nicknamemanager.color.legacy", Format.COLOR),
    HEX_COLOR("nicknamemanager.color.hex", Format.HEX),
    GRADIENT_COLOR("nicknamemanager.color.gradient", Format.GRADIENT),
    RAINBOW_COLOR("nicknamemanager.color.rainbow", Format.RAINBOW),
    ;

    private final String permission;
    private final Set<Format> formats;

    FormatPermission(@NotNull final String permission, @NotNull final Format... formats) {
        this.permission = permission;
        this.formats = new HashSet<>(Arrays.asList(formats));
    }

    /**
     * @return The permission for the specific type
     */
    public String getPermission() {
        return this.permission;
    }

    /**
     * @return The formats for the specific type
     */
    public Set<Format> getFormats() { return this.formats; }
}
