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
public class TriFill implements Filler {

    private short colorOne;
    private short colorTwo;
    private short colorThree;

    public TriFill(int colorOne, int colorTwo, int colorThree) {
        this.colorOne = (short) colorOne;
        this.colorTwo = (short) colorTwo;
        this.colorThree = (short) colorThree;
    }

    @Override
    public void fill(Menu menu) {
        Inventory inventory = menu.getInventory();

        int counter = 0;
        for (int i = counter; i < inventory.getSize(); i++) {
            MenuItemBuilder builder = new MenuItemBuilder();
            builder.setMaterial(Material.STAINED_GLASS_PANE);
            builder.clearName();
            builder.addListener(MenuItemClickEvent::cancel);

            switch (counter) {
                case 0:
                    builder.setDurability(colorOne);
                    break;
                case 1:
                    builder.setDurability(colorTwo);
                    break;
                case 2:
                    builder.setDurability(colorThree);
                    break;
                default:
                    counter = 0;
            }

            counter++;
            
            MenuItem menuItem = builder.build();
            menu.addItem(i, menuItem);
        }
    }

}
