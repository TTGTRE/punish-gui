package logan.guiapi;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Tre Logan
 */
public class MenuItemClickEvent {
    
    private ItemStack itemStack;
    private Player player;
    private InventoryClickEvent clickEvent;
    
    public MenuItemClickEvent(InventoryClickEvent event) {
        clickEvent = event;
        this.itemStack = event.getCurrentItem();
        this.player = (Player) event.getWhoClicked();
    }
    
    public ItemStack getItemStack() {
        return itemStack;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public InventoryClickEvent getInventoryClickEvent() {
        return clickEvent;
    }
    
    public void cancel() {
        clickEvent.setCancelled(true);
    }
    
}
