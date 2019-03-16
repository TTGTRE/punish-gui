package logan.punishgui;

import com.earth2me.essentials.Essentials;
import java.util.Objects;
import logan.guiapi.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import logan.punishgui.HelpCommand;

/**
 *
 * @author Tre Logan
 */
public class PunishPlugin extends JavaPlugin {

    private MenuLoader menuLoader;
    private static Essentials essentials;

    public static final String HELP_PERM = "punishgui.help";
    public static final String MUTE_PERM = "punishgui.mute";
    public static final String KICK_PERM = "punishgui.kick";
    public static final String BAN_PERM = "punishgui.ban";
    public static final String RELOAD_PERM = "punishgui.reload";
    
    private HelpCommand helpCommand;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        menuLoader = new MenuLoader(this);

        helpCommand = new HelpCommand();
        
        getLogger().info(getName() + " enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info(getName() + " disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to perform this command");
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("punishgui")) {
            helpCommand.onCommand(player, command, label, args);
        }

        if (label.equalsIgnoreCase("punishreload")) {

            if (!player.hasPermission(RELOAD_PERM)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            reloadConfig();

            player.sendMessage("Reloaded " + getName() + ".");
            return true;
        }
        
        if (label.equalsIgnoreCase("mutegui") && args.length == 1) {

            if (!player.hasPermission(MUTE_PERM)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            Player punishedPlayer = Bukkit.getPlayer(args[0]);

            if (Objects.isNull(punishedPlayer)) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            menuLoader.loadMuteMenu(punishedPlayer, getConfig()).show(player);
            
        } else if (args.length != 1) {
            return false;
        }

        if (label.equalsIgnoreCase("kickgui") && args.length == 1) {

            if (!player.hasPermission(KICK_PERM)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            Player punishedPlayer = Bukkit.getPlayer(args[0]);

            if (Objects.isNull(punishedPlayer)) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            menuLoader.loadKickMenu(punishedPlayer, getConfig()).show(player);
            
        } else if (args.length != 1) {
            return false;
        }

        if (label.equalsIgnoreCase("bangui") && args.length == 1) {

            if (!player.hasPermission(BAN_PERM)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            Player punishedPlayer = Bukkit.getPlayer(args[0]);

            if (Objects.isNull(punishedPlayer)) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            menuLoader.loadBanMenu(punishedPlayer, getConfig()).show(player);
            
        }

        return true;
    }

    public static Essentials getEssentials() {
        return essentials;
    }

}
