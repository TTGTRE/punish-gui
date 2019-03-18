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

import logan.punishgui.PunishCommand;
import logan.punishgui.PlayerClickListener;

/**
 *
 * @author Tre Logan
 */
public class PunishPlugin extends JavaPlugin {

    private static PunishPlugin instance;
    private static Essentials essentials;

    public static final String PUNISH_PERMISSION = "punishgui.punish";
    public static final String MUTE_PERMISSION = "punishgui.mute";
    public static final String KICK_PERMISSION = "punishgui.kick";
    public static final String BAN_PERMISSION = "punishgui.ban";

    private PunishCommand punishCommand;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

        punishCommand = new PunishCommand();

        // register listeners
        getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);
        
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
            return punishCommand.onCommand(player, command, label, args);
        }

        if (label.equalsIgnoreCase("punishreload")) {

            reloadConfig();
            player.sendMessage("Reloaded " + getName() + ".");

            return true;
        }
        
        if (label.equalsIgnoreCase("mutegui") && args.length == 1) {

            Player punishedPlayer = Bukkit.getPlayer(args[0]);

            if (Objects.isNull(punishedPlayer)) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            MenuLoader.loadMuteMenu(punishedPlayer, getConfig()).show(player);
            
        } else if (args.length != 1) {
            return false;
        }

        if (label.equalsIgnoreCase("kickgui") && args.length == 1) {

            Player punishedPlayer = Bukkit.getPlayer(args[0]);

            if (Objects.isNull(punishedPlayer)) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            MenuLoader.loadKickMenu(punishedPlayer, getConfig()).show(player);
            
        } else if (args.length != 1) {
            return false;
        }

        if (label.equalsIgnoreCase("bangui") && args.length == 1) {

            Player punishedPlayer = Bukkit.getPlayer(args[0]);

            if (Objects.isNull(punishedPlayer)) {
                player.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            MenuLoader.loadBanMenu(punishedPlayer, getConfig()).show(player);
            
        }

        return true;
    }

    public static Essentials getEssentials() {
        return essentials;
    }

    public static PunishPlugin getInstance() {
        return instance;
    }

}
