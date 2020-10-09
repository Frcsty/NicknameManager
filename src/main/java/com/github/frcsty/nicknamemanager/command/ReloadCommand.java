package com.github.frcsty.nicknamemanager.command;

import com.github.frcsty.nicknamemanager.NicknamePlugin;
import com.github.frcsty.nicknamemanager.util.Message;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Command("nicknamemanager")
@Alias("nm")
public final class ReloadCommand extends CommandBase {

    private NicknamePlugin plugin;

    public ReloadCommand(@NotNull final NicknamePlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("reload")
    @Permission("nicknamemanager.command.reload")
    public void onCommand(final CommandSender sender) {
        plugin.reloadConfig();

        plugin.getConfigStorage().load(plugin);
        Message.send(sender, plugin.getConfigStorage().getConfigString("message.reloaded-plugin"));
    }

}
