package logan.punishgui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Tre Logan
 */
public class ClickHandler implements Listener {
    
    private static final List<MenuItem> menuItems = new ArrayList<>();
    
    public static void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    
    public static void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
    }
    
    public static void removeAll() {
        menuItems.clear();
    }
    
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) {
            return;
        }
        
        menuItems.forEach(menuItem -> {
            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta == null) {
                return;
            }
            
            if(itemMeta.getDisplayName().equals(menuItem.getName())) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                MenuClickEvent menuClickEvent = new MenuClickEvent(player);
                menuItem.onClick(menuClickEvent);
            } 
        });
    }
    
}
