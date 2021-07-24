package bukkit;

import bukkit.chat.Log;
import bukkit.command.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {
    static Main instance;

    CommandHandler commandHandler = new CommandHandler();

    final String ENABLED_PLUGIN = "League of Crafters has been enabled!";
    final String DISABLED_PLUGIN = "League of Crafters has been disabled!";

    @Override
    public void onEnable () {
        instance = this;
        Log.UPDATE_INSTANCE();
        Log.INFO(ENABLED_PLUGIN);
    }

    @Override
    public void onDisable () {
        Log.INFO(DISABLED_PLUGIN);
    }

    public static Main GET_INSTANCE () {
        return instance;
    }

    @Override
    public boolean onCommand (@NotNull CommandSender sender,
                              @NotNull Command command,
                              @NotNull String label, String[] args) {
        return commandHandler.onCommand(sender, command, label, args);
    }


}
