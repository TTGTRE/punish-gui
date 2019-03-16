package logan.guiapi;

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Tre Logan
 */
public final class MenuItem implements MenuItemClickListener {

    private MenuItemClickListener listener;
    private final ItemStack itemStack;
    private String name;

    MenuItem(ItemStack itemStack, MenuItemClickListener listener) {
        this.itemStack = itemStack;
        this.listener = listener;
        name = itemStack.getItemMeta().getDisplayName();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onClick(MenuItemClickEvent event) {
        if (listener != null) {
            listener.onClick(event);
        }
    }

}
