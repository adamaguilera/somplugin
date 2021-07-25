package som.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Chat {

    private void sendMessage (final UUID playerID,
                              final String formattedMessage) {
        Player player = Bukkit.getPlayer(playerID);
        if (player == null) return;
        player.sendMessage(formattedMessage);
    }

    public void commandMessage (final UUID playerID, final String message) {
        sendMessage(playerID, formatCommandMessage(message));
    }

    private String formatCommandMessage (final String message) {
        return ChatColor.LIGHT_PURPLE + message + ChatColor.WHITE;
    }

    public void globalMessage (final String message) {
        Bukkit.broadcastMessage(formatGlobalMessage(message));
    }

    private String formatGlobalMessage (final String message) {
        return ChatColor.LIGHT_PURPLE + "SOM - " + message + ChatColor.WHITE;
    }
}
