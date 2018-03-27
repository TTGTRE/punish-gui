package logan.punishgui;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Tre Logan
 */
public class PunishPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new ClickHandler(), this);
        
        getLogger().info(getName() + " enabled.");
    }
    
    @Override
    public void onDisable() {
        getLogger().info(getName() + " disabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to perform this command");
            return true;
        }
        
        Player player = (Player) sender;
        
        if(label.equalsIgnoreCase("testgui")) {
            
            MenuItem menuItem = new MenuItem("My item", new ItemStack(Material.ARROW));
            menuItem.setOnClick((e) -> {
                player.sendMessage("You clicked an item");
            });
            Menu menu = new Menu("My title");
            menu.addMenuItem(menuItem, 0);
            menu.open(player);
        }
        
        return true;
    }
    
}
