package bukkit.command;

import bukkit.chat.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler {
    final String COMMAND_PREFIX = "loc";

    public boolean onCommand (CommandSender sender, Command command,
                              String label, String[] args) {
        if (validPrefix(command)) {
            Log.INFO("Command executed!");
        }
        return true;
    }

    private boolean validPrefix (final Command command) {
        return command.getName().equalsIgnoreCase(COMMAND_PREFIX);
    }
}
