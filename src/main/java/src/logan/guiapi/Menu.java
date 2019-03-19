package logan.guiapi;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author Tre Logan
 */
public class Menu implements Listener {

    private JavaPlugin plugin;

    private String title;
    private int slots;
    private Inventory inventory;

    private Map<Integer, MenuItem> menuItems = new HashMap<>();

    public Menu(JavaPlugin plugin, String title, int rows) {
        this.plugin = plugin;
        this.title = title;
        slots = rows * 9;
        inventory = Bukkit.createInventory(null, slots, title);
    }

    public void show(Player player) {
        menuItems.forEach((s, mi) ->  inventory.setItem(s, mi.getItemStack()));
        player.openInventory(inventory);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    public void addItem(int slot, MenuItem menuItem) {
        menuItems.put(slot, menuItem);
    }

    public void removeItem(int slot, MenuItem menuItem) {
        menuItems.remove(slot);
    }

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        
        String invName = event.getInventory().getTitle();

        if (!invName.equals(title)) {
            return;
        }

        menuItems.keySet().stream()
                .filter(s -> s == event.getSlot())
                .forEach(s -> menuItems.get(s).onClick(new MenuItemClickEvent(event)));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        String invName = event.getInventory().getTitle();

        if (!invName.equals(title)) {
            return;
        }

        HandlerList.unregisterAll(this);
    }
}
