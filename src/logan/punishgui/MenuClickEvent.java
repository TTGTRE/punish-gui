package logan.punishgui;

import org.bukkit.entity.Player;

/**
 *
 * @author Tre Logan
 */
public class MenuClickEvent {
    
    private final Player player;
    
    public MenuClickEvent(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
    
}
