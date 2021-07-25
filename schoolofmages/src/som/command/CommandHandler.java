package som.command;

import lombok.Builder;
import som.chat.Log;
import som.lobby.Lobby;
import som.player.PlayerManager;
import som.player.PlayerState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@Builder
public class CommandHandler {
    final Lobby lobby;
    final PlayerManager playerManager;

    final String COMMAND_PREFIX = "som";

    public boolean onCommand (CommandSender sender, Command command,
                              String label, String[] args) {
        if (validPrefix(command)) {
            if (sender instanceof Player) {
                UUID playerID = ((Player) sender).getUniqueId();
                if (args.length > 0) {
                    parseCommand(playerID, args);
                }
            }
        }

        return true;
    }



    private void parseCommand (final UUID playerID, String args[]) {
        switch (args[0]) {
            case "ready":
                readyPlayer(playerID);
                break;
            default:
        }
    }

    private void readyPlayer (final UUID playerID) {
        PlayerState playerState = playerManager.getPlayerState(playerID);
        playerState.getLobbyPlayer().ready();
        lobby.startCountdownIfReady();
    }

    private boolean validPrefix (final Command command) {
        return command.getName().equalsIgnoreCase(COMMAND_PREFIX);
    }
}
