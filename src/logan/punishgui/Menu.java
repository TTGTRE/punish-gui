package logan.punishgui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Tre Logan
 */
public class Menu {

    private String title;
    private Inventory inventory;
    private List<MenuItem> menuItems;
    
    public Menu(String title) {
        this.title = title;
        inventory = Bukkit.createInventory(null, InventoryType.PLAYER, title);
        menuItems = new ArrayList<>();
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
    
    public void addMenuItem(MenuItem menuItem, int slot) {
        menuItems.add(menuItem);
        inventory.setItem(slot, menuItem.getItemStack());
    }
    
    public void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
        inventory.remove(menuItem.getItemStack());
    }
    
    public String getTitle() {
        return title;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
}
