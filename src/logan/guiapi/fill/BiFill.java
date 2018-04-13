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
public class BiFill implements Filler {

    private short colorOne;
    private short colorTwo;

    public BiFill(int colorOne, int colorTwo) {
        this.colorOne = (short) colorOne;
        this.colorTwo = (short) colorTwo;
    }

    @Override
    public void fill(Menu menu) {
        Inventory inventory = menu.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {
            MenuItemBuilder builder = new MenuItemBuilder();
            builder.setMaterial(Material.STAINED_GLASS_PANE);
            builder.clearName();
            builder.setDurability((i % 2) == 0 ? colorOne : colorTwo);
            builder.addListener(MenuItemClickEvent::cancel);
            
            MenuItem menuItem = builder.build();
            menu.addItem(i, menuItem);
        }
    }

}
