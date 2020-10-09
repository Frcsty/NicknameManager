package com.github.frcsty.nicknamemanager.command;

import com.github.frcsty.nicknamemanager.NicknamePlugin;
import com.github.frcsty.nicknamemanager.util.Message;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command("nickname")
public final class ResetCommand extends CommandBase {

    private final NicknamePlugin plugin;

    public ResetCommand(@NotNull final NicknamePlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("reset")
    @Permission("nicknamemanager.command.reset")
    public void onCommand(final Player player) {
        plugin.getProfileStorage().getNicknameHandler().resetNickname(player.getUniqueId());
        Message.send(player, plugin.getConfigStorage().getConfigString("message.reset-nickname"));
    }

}
