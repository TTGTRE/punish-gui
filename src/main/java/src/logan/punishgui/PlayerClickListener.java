package logan.punishgui;

import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import logan.punishgui.PunishPlugin;

public class PlayerClickListener implements Listener {

    private Set<UUID> clickedSet = new HashSet<>();

    // this event actually triggers twice per click (for each hand)
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {

        Player whoClicked = event.getPlayer();

        // prevent double event triggering
        if (clickedSet.contains(whoClicked.getUniqueId())) {
            clickedSet.remove(whoClicked.getUniqueId());
            return;
        }

        clickedSet.add(whoClicked.getUniqueId());

        // a player wasn't right-clicked, so return
        if (!(event.getRightClicked() instanceof Player)) return;

        Player clickedPlayer = (Player) event.getRightClicked();

        // make sure the player who clicked has permission to punish
        if (!whoClicked.hasPermission(PunishPlugin.PUNISH_PERMISSION)) {
            whoClicked.sendMessage(ChatColor.RED + "No permission");
            return;
        }

        MenuLoader.openPunishMenu(whoClicked, clickedPlayer);
    }
}