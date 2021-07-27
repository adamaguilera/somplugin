package som.game;

import lombok.Getter;
import som.Main;
import som.chat.Chat;
import som.game.mage.Mage;
import som.game.mage.inventory.InventoryHandler;
import som.game.mage.inventory.StartingInventory;
import som.game.mage.spell.PassiveHandler;
import som.game.passive.PassivePool;
import som.game.scoreboard.Leaderboard;
import som.game.spawn.Platform;
import som.game.spell.SpellPool;
import som.player.PlayerManager;
import som.player.PlayerState;
import som.task.TaskRegister;


public class Game {
    final String ON_GAME_START = "Game has started!";
    @Getter
    final PlayerManager playerManager;
    final Leaderboard leaderboard;
    final Platform platform;
    final TaskRegister taskRegister;
    final SpellPool spellPool;
    final PassivePool passivePool;
    final Chat chat = new Chat();

    public Game (final GameConfig gameConfig,
                 final PlayerManager playerManager) {
        this.spellPool = new SpellPool();
        this.passivePool = new PassivePool();
        this.playerManager = playerManager;
        this.leaderboard = Leaderboard.builder()
                .playerManager(this.playerManager)
                .build();
        this.platform = gameConfig.getPlatform();
        this.platform.generatePlatform();
        this.taskRegister = new TaskRegister();
    }

    public void onStart () {
        leaderboard.updateLeaderboards();
        chat.globalMessage(ON_GAME_START);
        Main.GET_INSTANCE().registerGameListener();
        taskRegister.addTask(passivePool);
        taskRegister.start();
        this.giveStartingItems();
        this.giveStartingPassives();
        this.spawnAllPlayers();
    }

    private void spawnPlayer (final PlayerState playerState) {
        teleportToSpawn(playerState);
    }

    private void giveStartingPassives () {
        playerManager.getAllPlayers().stream()
                .map(PlayerState::getMage)
                .map(Mage::getPassiveHandler)
                .forEach(PassiveHandler::addStartingPassives);
    }

    private void giveStartingItems () {
        playerManager.getAllPlayers().stream()
                .map(PlayerState::getMage)
                .map(Mage::getInventoryHandler)
                .map(InventoryHandler::getStartingInventory)
                .forEach(StartingInventory::giveStartingInventory);
    }

    private void spawnAllPlayers () {
        playerManager.getAllPlayers().forEach(this::spawnPlayer);
    }

    public void teleportToSpawn (final PlayerState playerState) {
        playerState.getPlayer().ifPresent(player ->
                player.teleport(platform.getSpawn()));
    }



}
