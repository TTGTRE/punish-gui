package logan.guiapi.fill;

import logan.guiapi.Menu;
import logan.guiapi.MenuItem;
import logan.guiapi.MenuItemBuilder;
import logan.guiapi.MenuItemClickEvent;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author Tre Logan
 */
public class UniFill implements Filler {

    private short color;
    
    public UniFill(int color) {
        this.color = (short) color;
    }
    
    @Override
    public void fill(Menu menu) {
        Inventory inventory = menu.getInventory();
        for(int i = 0; i < inventory.getSize(); i++) {
            
            MenuItemBuilder builder = new MenuItemBuilder();
            builder.setMaterial(Material.STAINED_GLASS_PANE);
            builder.clearName();
            builder.setDurability(color);
            builder.addListener(MenuItemClickEvent::cancel);
            
            MenuItem menuItem = builder.build();
            menu.addItem(i, menuItem);
            
        }
    }
    
}
