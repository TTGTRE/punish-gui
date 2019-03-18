package logan.punishgui;

import com.earth2me.essentials.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logan.guiapi.Menu;
import logan.guiapi.MenuItem;
import logan.guiapi.MenuItemBuilder;
import logan.punishgui.TimeUtil;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static logan.punishgui.PunishPlugin;;

/**
 *
 * @author Tre Logan
 */
public class MenuLoader {

    private static final int OPEN_DELAY = 10; // ticks

    /** Opens a punishment menu containing items
    representing the types of punishments to perform.
    @param player The player that will get punished.
    @return The punishment menu. */
    public static void openPunishMenu(Player punisher, Player player) {

        MenuItemBuilder itemBuilder = new MenuItemBuilder();

        // Mute menu item
        itemBuilder.setMaterial(Material.HOPPER);
        itemBuilder.setName(ChatColor.YELLOW + "Mute Options");
        itemBuilder.addListener(event -> {

            event.getPlayer().closeInventory();
            Menu muteMenu = loadMuteMenu(player, PunishPlugin.getInstance().getConfig());

            new BukkitRunnable() {
                @Override
                public void run() {
                    muteMenu.show(punisher);
                }
            }.runTaskLater(PunishPlugin.getInstance(), OPEN_DELAY);
        });

        MenuItem muteItem = itemBuilder.build();

        // Kick menu item
        itemBuilder.setMaterial(Material.ARROW);
        itemBuilder.setName(ChatColor.YELLOW + "Kick Options");
        itemBuilder.addListener(event -> {

            event.getPlayer().closeInventory();
            
            Menu kickMenu = loadKickMenu(player, PunishPlugin.getInstance().getConfig());

            new BukkitRunnable() {
                @Override
                public void run() {          
                    kickMenu.show(punisher);
                }
            }.runTaskLater(PunishPlugin.getInstance(), OPEN_DELAY);
        });

        MenuItem kickItem = itemBuilder.build();

        // Ban menu item
        itemBuilder.setMaterial(Material.BEDROCK);
        itemBuilder.setName(ChatColor.RED + "Ban Options");
        itemBuilder.addListener(event -> {

            event.getPlayer().closeInventory();
            
            Menu banMenu = loadBanMenu(player, PunishPlugin.getInstance().getConfig());

            new BukkitRunnable() {
                @Override
                public void run() {          
                    banMenu.show(punisher);
                }
            }.runTaskLater(PunishPlugin.getInstance(), OPEN_DELAY);
        });

        MenuItem banItem = itemBuilder.build();

        // Create the menu and add items to it
        Menu menu = new Menu(PunishPlugin.getInstance(), "Punish " + player.getName(), 1);

        // only add menu items the punisher can use
        if (punisher.hasPermission("punishgui.mute")) menu.addItem(0, muteItem);
        if (punisher.hasPermission("punishgui.kick")) menu.addItem(1, kickItem);
        if (punisher.hasPermission("punishgui.ban")) menu.addItem(2, banItem);

        menu.show(punisher);
    }

    public static Menu loadMuteMenu(Player player, FileConfiguration config) {

        Menu menu = new Menu(PunishPlugin.getInstance(), "Mute", 3);

        List<String> stringList = config.getStringList("mute");

        MenuItemBuilder builder = new MenuItemBuilder();

        int slot = 0;
        for (String string : stringList) {

            String[] parts = string.split("->");
            String reason = parts[0].trim();
            String timeString = parts[1].trim();
            final long time = TimeUtil.getTime(timeString);
            final String readableTime = TimeUtil.asReadableTime(time);

            builder.setMaterial(Material.MUSHROOM_STEW);
            builder.setName(ChatColor.GOLD + reason);
            builder.setLore(ChatColor.WHITE + readableTime);
            builder.addListener(e -> {

                User user = PunishPlugin.getEssentials().getUser(player);
                user.setMuteTimeout(System.currentTimeMillis() + time);
                user.setMuted(true);
                user.sendMessage(ChatColor.RED + "You have been muted for " + readableTime);

                e.getPlayer().sendMessage(ChatColor.GOLD + "Muted " + user.getName() + " for " + readableTime);
                e.getPlayer().closeInventory();
            });

            MenuItem menuItem = builder.build();
            menu.addItem(slot, menuItem);

            slot++;
        }

        return menu;
    }

    public static Menu loadKickMenu(Player player, FileConfiguration config) {
        Menu menu = new Menu(PunishPlugin.getInstance(), "Kick", 3);

        List<String> stringList = config.getStringList("kick");

        MenuItemBuilder builder = new MenuItemBuilder();

        int slot = 0;
        for (String reason : stringList) {

            builder.setMaterial(Material.BOW);
            builder.setName(ChatColor.GOLD + reason);
            builder.addListener(e -> {
                player.kickPlayer(reason);
                e.getPlayer().sendMessage(ChatColor.GOLD + "Kicked " + player.getName() + " for " + reason);
                e.getPlayer().closeInventory();
            });

            MenuItem menuItem = builder.build();
            menu.addItem(slot, menuItem);

            slot++;
        }

        return menu;
    }

    public static Menu loadBanMenu(Player player, FileConfiguration config) {
        Menu menu = new Menu(PunishPlugin.getInstance(), "Ban", 3); 

        List<String> stringList = config.getStringList("ban");

        MenuItemBuilder builder = new MenuItemBuilder();

        int slot = 0;
        for (String string : stringList) {

            String[] parts = string.split("->");
            String reason = parts[0].trim();
            String timeString = parts[1].trim();
            final long time = TimeUtil.getTime(timeString);
            final String readableTime = TimeUtil.asReadableTime(time);

            builder.setMaterial(Material.STONE_AXE);
            builder.setName(ChatColor.GOLD + reason);
            builder.setLore(ChatColor.WHITE + TimeUtil.asReadableTime(time));
            builder.addListener(e -> {
                User user = PunishPlugin.getEssentials().getUser(player);
                LocalDateTime ldt = LocalDateTime.now().plus(time, ChronoUnit.MILLIS);
                Bukkit.getBanList(Type.NAME).addBan(player.getName(), reason, Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()), null);
                player.kickPlayer(ChatColor.RED + "Banned: " + reason);
                e.getPlayer().sendMessage(ChatColor.GOLD + "Banned " + user.getName() + " for " + readableTime + " for " + reason);
                e.getPlayer().closeInventory();
            });

            MenuItem menuItem = builder.build();
            menu.addItem(slot, menuItem);

            slot++;
        }

        return menu;
    }
}
