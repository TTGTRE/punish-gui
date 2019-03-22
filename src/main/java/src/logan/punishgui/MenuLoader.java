package logan.punishgui;

import com.earth2me.essentials.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import logan.guiapi.Menu;
import logan.guiapi.MenuItem;
import logan.guiapi.MenuItemBuilder;
import logan.punishgui.TimeUtil;
import logan.punishgui.PunishPlugin;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Tre Logan
 */
public class MenuLoader {

    private static final int OPEN_DELAY = 5; // ticks

    private static Menu muteMenu = new Menu(PunishPlugin.getInstance(), "Mute", 3);
    private static Menu kickMenu = new Menu(PunishPlugin.getInstance(), "Kick", 3);
    private static Menu banMenu = new Menu(PunishPlugin.getInstance(), "Ban", 3);

    static {
        muteMenu.fill(Material.WHITE_STAINED_GLASS_PANE);
        kickMenu.fill(Material.WHITE_STAINED_GLASS_PANE);
        banMenu.fill(Material.WHITE_STAINED_GLASS_PANE);
    }

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

            new BukkitRunnable() {
                @Override
                public void run() {
                    openMuteMenu(punisher, player);
                }
            }.runTaskLater(PunishPlugin.getInstance(), OPEN_DELAY);
        });

        MenuItem muteItem = itemBuilder.build();

        // Kick menu item
        itemBuilder.setMaterial(Material.ARROW);
        itemBuilder.setName(ChatColor.YELLOW + "Kick Options");
        itemBuilder.addListener(event -> {

            event.getPlayer().closeInventory();

            new BukkitRunnable() {
                @Override
                public void run() {          
                    openKickMenu(punisher, player);
                }
            }.runTaskLater(PunishPlugin.getInstance(), OPEN_DELAY);
        });

        MenuItem kickItem = itemBuilder.build();

        // Ban menu item
        itemBuilder.setMaterial(Material.BEDROCK);
        itemBuilder.setName(ChatColor.RED + "Ban Options");
        itemBuilder.addListener(event -> {

            event.getPlayer().closeInventory();

            new BukkitRunnable() {
                @Override
                public void run() {          
                    openBanMenu(punisher, player);
                }
            }.runTaskLater(PunishPlugin.getInstance(), OPEN_DELAY);
        });

        MenuItem banItem = itemBuilder.build();

        // Create the menu and add items to it
        Menu menu = new Menu(PunishPlugin.getInstance(), "Punish " + player.getName(), 1);
        menu.fill(Material.WHITE_STAINED_GLASS_PANE);

        // only add menu items the punisher can use
        if (punisher.hasPermission(PunishPlugin.MUTE_PERMISSION)) menu.addItem(0, muteItem);
        if (punisher.hasPermission(PunishPlugin.KICK_PERMISSION)) menu.addItem(1, kickItem);
        if (punisher.hasPermission(PunishPlugin.BAN_PERMISSION)) menu.addItem(2, banItem);

        menu.show(punisher);
    } 

    public static void openMuteMenu(Player punisher, Player receiver) {

        MenuItemBuilder builder = new MenuItemBuilder();

        List<Punishment> punishments = PunishPlugin.getPunishConfiguration().getMuteActions();

        int slot = 0;
        for (Punishment punishment : punishments) {

            builder.setMaterial(Material.MUSHROOM_STEW);
            builder.setName(ChatColor.GOLD + punishment.getReason());
            builder.setLore(ChatColor.WHITE + punishment.getReadableDuration());

            builder.addListener(event -> {

                Player player = event.getPlayer();

                User user = PunishPlugin.getEssentials().getUser(receiver);
                user.setMuteTimeout(System.currentTimeMillis() + punishment.getDuration());
                user.setMuted(true);
                user.sendMessage(ChatColor.RED + "You have been muted for " + punishment.getReadableDuration());

                player.sendMessage(ChatColor.GOLD + "Muted " + user.getName() + " for " + punishment.getReadableDuration());
                player.closeInventory();
            });

            MenuItem menuItem = builder.build();
            muteMenu.addItem(slot, menuItem);

            slot++;
        }

        muteMenu.show(punisher);
    }

    public static void openKickMenu(Player punisher, Player receiver) {

        MenuItemBuilder builder = new MenuItemBuilder();

        List<Punishment> punishments = PunishPlugin.getInstance().getPunishConfiguration().getKickActions();

        int slot = 0;
        for (Punishment punishment : punishments) {

            builder.setMaterial(Material.BOW);
            builder.setName(ChatColor.GOLD + punishment.getReason());
            builder.addListener(event -> {

                Player player = event.getPlayer();

                receiver.kickPlayer(punishment.getReason());
                player.sendMessage(ChatColor.GOLD + "Kicked " + player.getName() + " for " + punishment.getReason());
                player.closeInventory();
            });

            MenuItem menuItem = builder.build();
            kickMenu.addItem(slot, menuItem);

            slot++;
        }

        kickMenu.show(punisher);
    }

    public static void openBanMenu(Player punisher, Player receiver) {

        MenuItemBuilder builder = new MenuItemBuilder();

        List<Punishment> punishments = PunishPlugin.getInstance().getPunishConfiguration().getBanActions();

        int slot = 0;
        for (Punishment punishment : punishments) {

            builder.setMaterial(Material.STONE_AXE);
            builder.setName(ChatColor.GOLD + punishment.getReason());
            builder.setLore(ChatColor.WHITE + punishment.getReadableDuration());
            builder.addListener(event -> {

                Player player = event.getPlayer();

                User user = PunishPlugin.getEssentials().getUser(receiver);
                LocalDateTime ldt = LocalDateTime.now().plus(punishment.getDuration(), ChronoUnit.MILLIS);
                Bukkit.getBanList(Type.NAME).addBan(receiver.getName(), punishment.getReason(), Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()), null);
                receiver.kickPlayer(ChatColor.RED + "Banned: " + punishment.getReason());
                player.sendMessage(ChatColor.GOLD + "Banned " + user.getName() + " for " + punishment.getReadableDuration() + " for " + punishment.getReason());
                player.closeInventory();
            });

            MenuItem menuItem = builder.build();
            banMenu.addItem(slot, menuItem);

            slot++;
        }

        banMenu.show(punisher);
    }
}
