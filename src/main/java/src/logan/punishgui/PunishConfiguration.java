package logan.punishgui;

import java.util.List;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import logan.punishgui.TimeUtil;
import logan.punishgui.MuteAction;

public class PunishConfiguration {

    private FileConfiguration configuration;

    public PunishConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void reload(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<Punishment> getMuteActions() {

        List<Punishment> muteActionList = new ArrayList<>();
        List<String> muteActionStrings = configuration.getStringList("mute");

        muteActionStrings.forEach(string -> {

            String[] parts = string.split("->");
            String reason = parts[0].trim();
            long duration = TimeUtil.getTime(parts[1].trim());

            Punishment muteAction = new Punishment(reason, duration);
            muteActionList.add(muteAction);
        });

        return muteActionList;
    }

    public List<Punishment> getKickActions() {

        List<Punishment> kickActionList = new ArrayList<>();
        List<String> kickActionStrings = configuration.getStringList("kick");

        kickActionStrings.forEach(string -> {

            String[] parts = string.split("->");
            String reason = parts[0].trim();

            Punishment kickAction = new Punishment(reason, 0);
            kickActionList.add(kickAction);
        });

        return kickActionList;
    }

    public List<Punishment> getBanActions() {

        List<Punishment> banActionList = new ArrayList<>();
        List<String> banActionStrings = configuration.getStringList("ban");

        banActionStrings.forEach(string -> {

            String[] parts = string.split("->");
            String reason = parts[0].trim();
            long duration = TimeUtil.getTime(parts[1].trim());

            Punishment banAction = new Punishment(reason, duration);
            banActionList.add(banAction);
        });

        return banActionList;
    }
}