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
import logan.guiapi.fill.QuadFill;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Tre Logan
 */
public class MenuLoader {

    private JavaPlugin plugin;

    public MenuLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Menu loadBanMenu(Player player, FileConfiguration config) {
        Menu menu = new Menu(plugin, "Ban", 3);
        menu.fill(new QuadFill(7, 8, 1, 14));

        List<String> stringList = config.getStringList("ban");

        int slot = 0;
        for (String string : stringList) {

            String[] parts = string.split("->");
            String reason = parts[0].trim();
            String timeString = parts[1].trim();
            final long time = getTime(timeString);
            final String readableTime = asReadableTime(time);

            MenuItemBuilder builder = new MenuItemBuilder();
            builder.setMaterial(Material.STONE_AXE);
            builder.setName(ChatColor.GOLD + reason);
            builder.setLore(ChatColor.WHITE + asReadableTime(time));
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

    public Menu loadKickMenu(Player player, FileConfiguration config) {
        Menu menu = new Menu(plugin, "Kick", 3);
        menu.fill(new QuadFill(7, 8, 1, 14));

        List<String> stringList = config.getStringList("kick");

        int slot = 0;
        for (String reason : stringList) {

            MenuItemBuilder builder = new MenuItemBuilder();
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

    public Menu loadMuteMenu(Player player, FileConfiguration config) {
        Menu menu = new Menu(plugin, "Mute", 3);
        menu.fill(new QuadFill(7, 8, 1, 14));

        List<String> stringList = config.getStringList("mute");

        int slot = 0;
        for (String string : stringList) {

            String[] parts = string.split("->");
            String reason = parts[0].trim();
            String timeString = parts[1].trim();
            final long time = getTime(timeString);
            final String readableTime = asReadableTime(time);

            MenuItemBuilder builder = new MenuItemBuilder();
            builder.setMaterial(Material.MUSHROOM_SOUP);
            builder.setName(ChatColor.GOLD + reason);
            builder.setLore(ChatColor.WHITE + readableTime);
            builder.addListener(e -> {
                User user = PunishPlugin.getEssentials().getUser(player);
                if (!user.getMuted()) {
                    user.setMuteTimeout(System.currentTimeMillis() + time);
                    user.setMuted(true);
                    user.sendMessage(ChatColor.RED + "You have been muted for " + readableTime);
                } else {

                    long timeLeft = user.getMuteTimeout() - System.currentTimeMillis();

                    e.getPlayer().sendMessage(ChatColor.RED + "That player is already muted. (" + asReadableTime(timeLeft) + " left)");
                    return;
                }

                e.getPlayer().sendMessage(ChatColor.GOLD + "Muted " + user.getName() + " for " + readableTime);
                e.getPlayer().closeInventory();
            });

            MenuItem menuItem = builder.build();
            menu.addItem(slot, menuItem);

            slot++;

        }

        return menu;
    }

    private long getTime(String timeString) {
        Pattern pattern = Pattern.compile("\\d+\\D{1}");
        Matcher matcher = pattern.matcher(timeString);

        long timeout = 0;
        while (matcher.find()) {

            String match = timeString.substring(matcher.start(), matcher.end());

            int time = Integer.parseInt(match.substring(0, match.length() - 1));
            char timeUnit = match.charAt(match.length() - 1);

            switch (timeUnit) {
                case 'd':
                    timeout += TimeUnit.DAYS.toMillis(time);
                    break;
                case 'h':
                    timeout += TimeUnit.HOURS.toMillis(time);
                    break;
                case 'm':
                    timeout += TimeUnit.MINUTES.toMillis(time);
                    break;
                case 's':
                    timeout += TimeUnit.SECONDS.toMillis(time);
                    break;
                default:
                    break;
            }

        }

        return timeout;
    }

    private String asReadableTime(long millis) {

        long x = millis / 1000;
        long seconds = x % 60;
        x /= 60;
        long minutes = x % 60;
        x /= 60;
        long hours = x % 24;
        x /= 24;
        long days = x;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append(" days ");
        }
        if (hours > 0) {
            builder.append(hours).append(" hours ");
        }
        if (minutes > 0) {
            builder.append(minutes).append(" minutes ");
        }
        if (seconds > 0) {
            builder.append(seconds).append(" seconds ");
        }

        return builder.toString().trim();
    }

}
