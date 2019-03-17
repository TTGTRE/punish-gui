package logan.guiapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Tre Logan
 */
public final class MenuItemBuilder {

    private ItemStack itemStack = new ItemStack(Material.DIRT);
    private MenuItemClickListener listener;

    public MenuItemBuilder() {
        
    }

    public final MenuItemBuilder addListener(MenuItemClickListener listener) {
        this.listener = listener;
        return this;
    }
    
    public final MenuItemBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public final MenuItemBuilder setDurability(int durability) {
        setDurability((short) durability);
        return this;
    }
    
    public final MenuItemBuilder setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }
    
    public final MenuItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public final MenuItemBuilder clearName() {
        setName("");
        return this;
    }
    
    public final MenuItemBuilder setName(String name) {
        setMetaProperty(m -> {
            m.setDisplayName(ChatColor.WHITE + name);
            return m;
        });
        return this;
    }

    public final MenuItemBuilder addItemFlags(ItemFlag... flags) {
        setMetaProperty(m -> {
            m.addItemFlags(flags);
            return m;
        });
        return this;
    }
    
    public final MenuItemBuilder addItemEffect(ItemEffect itemEffect) {
        switch (itemEffect) {
            case ENCHANTED:
                addEnchantment(Enchantment.ARROW_DAMAGE, 0, false);
                addItemFlags(ItemFlag.HIDE_ENCHANTS);
                break;
        }
        return this;
    }

    public final MenuItemBuilder addEnchantment(Enchantment enchantment, int level, boolean bypass) {
        setMetaProperty(m -> {
            m.addEnchant(enchantment, level, bypass);
            return m;
        });
        return this;
    }
    
    public final MenuItemBuilder setLore(ChatColor color, String... lore) {
        // append chat color to beginning of each line of lore
        List<String> loreList = Arrays.stream(lore)
                .map(s -> color + s)
                .collect(Collectors.toList());
        setLore(loreList);
        return this;
    }

    public final MenuItemBuilder setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    public final MenuItemBuilder setLore(List<String> lore) {
        setMetaProperty(m -> {
            m.setLore(lore);
            return m;
        });
        return this;
    }

    public final MenuItem build() {
        return new MenuItem(itemStack.clone(), listener);
    }
    
    private void setMetaProperty(Function<ItemMeta, ItemMeta> function) {
        itemStack.setItemMeta(function.apply(itemStack.getItemMeta()));
    }

    public enum ItemEffect {
        ENCHANTED
    }

}
