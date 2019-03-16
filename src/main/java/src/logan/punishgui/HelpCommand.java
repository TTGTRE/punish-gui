package logan.punishgui;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import logan.punishgui.PunishPlugin;

public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase("punishgui")) return false;

        if (sender.hasPermission(PunishPlugin.HELP_PERM) && args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Punish GUI Help Menu");
            sender.sendMessage(ChatColor.GRAY + "/mutegui <player> (Opens the mute GUI)");
            sender.sendMessage(ChatColor.GRAY + "/kickgui <player> (Opens the kick GUI)");
            sender.sendMessage(ChatColor.GRAY + "/bangui <player> (Opens the ban GUI)");
            sender.sendMessage(ChatColor.GRAY + "/punishreload (Reload config)");
            return true;
        }

        return false;
    }
}