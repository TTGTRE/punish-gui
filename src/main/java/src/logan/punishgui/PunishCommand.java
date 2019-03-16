package logan.punishgui;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;

import logan.punishgui.PunishPlugin;
import logan.guiapi.Menu;

public class PunishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase("punish")) return false;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /punish <player>");
            return false;
        }

        if (!sender.hasPermission(PunishPlugin.PUNISH_PERM)) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }

        Player badPlayer = Bukkit.getPlayer(args[0]);
        if (badPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Couldn't find player.");
            return false;
        }

        MenuLoader.openPunishMenu((Player) sender, badPlayer);
        sender.sendMessage("Opened punishment menu for " + badPlayer.getName() + ".");

        return true;
    }
}