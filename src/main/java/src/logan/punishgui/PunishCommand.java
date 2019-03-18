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

        if (!label.equalsIgnoreCase("punishgui")) return true;

        // display commands
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "PunishGUI:");
            sender.sendMessage("/punishgui <player> - Open general punish menu");
            sender.sendMessage("/mutegui <player> - Open the mute menu");
            sender.sendMessage("/kickgui <player> - Open the kick menu");
            sender.sendMessage("/bangui <player> - Open the ban menu");
            sender.sendMessage("/punishreload - Reload config");
            return true;
        }

        Player badPlayer = Bukkit.getPlayer(args[0]);
        if (badPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Couldn't find player.");
            return true;
        }

        MenuLoader.openPunishMenu((Player) sender, badPlayer);
        return true;
    }
}