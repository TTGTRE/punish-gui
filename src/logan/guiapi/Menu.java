package logan.guiapi;

import java.util.HashMap;
import java.util.Map;
import logan.guiapi.fill.Filler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Tre Logan
 */
public class Menu implements Listener {

    private String title;
    private Inventory inventory;
    private int slots;

    private Map<Integer, MenuItem> menuItems = new HashMap<>();

    public Menu(JavaPlugin plugin, String title, int rows) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.title = title;
        slots = rows * 9;
        inventory = Bukkit.createInventory(null, slots, title);
    }

    public void show(Player player) {
        menuItems.forEach((s, mi) -> inventory.setItem(s, mi.getItemStack()));
        player.openInventory(inventory);
    }

    public void fill(Filler fillPattern) {
        fillPattern.fill(this);
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

}
