package som;

import som.chat.Log;
import som.command.CommandHandler;
import som.lobby.Lobby;
import som.player.LoginManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import som.player.PlayerManager;

public class Main extends JavaPlugin {
    static Main instance;

    ConfigLoader config;
    CommandHandler commandHandler;
    LoginManager loginManager;
    PlayerManager playerManager;
    Lobby lobby = new Lobby();

    final String ENABLED_PLUGIN = "League of Crafters has been enabled!";
    final String DISABLED_PLUGIN = "League of Crafters has been disabled!";

    @Override
    public void onEnable () {
        instance = this;
        this.config = new ConfigLoader();
        this.config.loadAll();
        this.lobby.setEnabled(true);
        this.playerManager = new PlayerManager();
        this.loginManager = LoginManager.builder()
                .lobby(this.lobby)
                .playerManager(this.playerManager)
                .build();
        this.commandHandler = CommandHandler.builder()
                .lobby(this.lobby)
                .playerManager(this.playerManager)
                .build();
        this.getServer().getPluginManager().registerEvents(loginManager, this);
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
