package logan.punishgui;

import java.util.Arrays;
import org.bukkit.inventory.ItemFlag;
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
        
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.itemStack.setItemMeta(meta);
        
        setName(this.name, this.itemStack);
    }

    public void setLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
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
