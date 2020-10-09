package com.github.frcsty.nicknamemanager.command;

import com.github.frcsty.nicknamemanager.FormatPermission;
import com.github.frcsty.nicknamemanager.NicknamePlugin;
import com.github.frcsty.nicknamemanager.storage.ProfileStorage;
import com.github.frcsty.nicknamemanager.util.Message;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mfmsg.base.MessageOptions;
import me.mattstudios.mfmsg.base.internal.Format;
import me.mattstudios.mfmsg.base.internal.MessageComponent;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Command("nickname")
@Alias("nick")
public final class NicknameCommand extends CommandBase {

    private final NicknamePlugin plugin;

    public NicknameCommand(@NotNull final NicknamePlugin plugin) {
        this.plugin = plugin;
    }

    @Default
    @Permission("nicknamemanager.command.use")
    public void onCommand(final Player player, final String nickname) {
        final UUID uuid = player.getUniqueId();
        final ProfileStorage storage = plugin.getProfileStorage();
        final MessageOptions.Builder optionsBuilder = MessageOptions.builder();

        getPlayerRestrictedFormats(player).forEach(optionsBuilder::removeFormat);
        final MessageOptions options = optionsBuilder.build();

        final BukkitMessage message = BukkitMessage.create(options);
        final MessageComponent component = message.parse(nickname);

        storage.getNicknameHandler().setNickname(uuid, component.toString());
        Message.send(player, plugin.getConfigStorage().getConfigString("message.changed-nickname"));
    }

    /**
     * @param player Desired player instance
     * @return A set of restricted formats
     */
    private Set<Format> getPlayerRestrictedFormats(@NotNull final Player player) {
        final Set<Format> formats = new HashSet<>();

        for (final FormatPermission formatPermission : FormatPermission.values()) {
            if (player.hasPermission(formatPermission.getPermission())) {
                continue;
            }

            formats.addAll(formatPermission.getFormats());
        }

        return formats;
    }

}
