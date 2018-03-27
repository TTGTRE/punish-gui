package logan.punishgui;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Tre Logan
 */
public class MenuItem {
    
    private String name;
    private ItemStack itemStack;
    private ClickListener clickable;
    
    public MenuItem(String name, ItemStack itemStack) {
        this.name = name;
        this.itemStack = itemStack;
        
        setName(this.name, this.itemStack);
    }

    public String getName() {
        return name;
    }
    
    private void setName(String name, ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }
    
    public ItemStack getItemStack() {
        return itemStack;
    }
    
    public void setOnClick(ClickListener clickable) {
        this.clickable = clickable;
        ClickHandler.addMenuItem(this);
    }
    
    public void onClick(MenuClickEvent event) {
        clickable.onClick(event);
    }

}
