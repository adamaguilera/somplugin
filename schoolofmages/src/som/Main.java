package som;

import org.bukkit.Bukkit;
import som.chat.Log;
import som.command.CommandHandler;
import som.game.Game;
import som.game.passive.PassivePool;
import som.game.spell.SpellPool;
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
    GameListener gameListener;
    SpellPool spellPool;
    PassivePool passivePool;
    Lobby lobby;
    Game game;

    final String ENABLED_PLUGIN = "League of Crafters has been enabled!";
    final String DISABLED_PLUGIN = "League of Crafters has been disabled!";

    public static Main GET_INSTANCE () {
        return instance;
    }

    @Override
    public void onEnable () {
        instance = this;
        Log.UPDATE_INSTANCE();
        this.config = new ConfigLoader();
        this.config.loadAll();
        this.spellPool = new SpellPool();
        this.passivePool = new PassivePool();
        this.playerManager = new PlayerManager(this.spellPool,
                this.passivePool);
        this.game = new Game(config.getGameConfig(), this.playerManager);
        this.lobby = Lobby.builder()
                .game(this.game)
                .playerManager(this.playerManager)
                .build();
        this.lobby.setEnabled(true);
        this.loginManager = LoginManager.builder()
                .lobby(this.lobby)
                .playerManager(this.playerManager)
                .game(this.game)
                .build();
        this.commandHandler = CommandHandler.builder()
                .lobby(this.lobby)
                .playerManager(this.playerManager)
                .build();
        this.gameListener = GameListener.builder()
                .game(this.game)
                .playerManager(this.playerManager)
                .build();
        this.getServer().getPluginManager().registerEvents(loginManager, this);
        Log.INFO(ENABLED_PLUGIN);

    }

    @Override
    public void onDisable () {
        Log.INFO(DISABLED_PLUGIN);
    }

    public void registerGameListener () {
        this.getServer().getPluginManager().registerEvents(gameListener, this);
    }

    @Override
    public boolean onCommand (@NotNull CommandSender sender,
                              @NotNull Command command,
                              @NotNull String label, String[] args) {
        return commandHandler.onCommand(sender, command, label, args);
    }


}
