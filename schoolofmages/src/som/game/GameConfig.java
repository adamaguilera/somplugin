package som.game;

import lombok.Data;
import som.ConfigLoader;
import som.game.spawn.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class GameConfig {
    public static final Path PATH = Paths.get("./plugins/SchoolOfMages/game.json");
    final Platform platform;

    public void saveConfig () {
        try {
            String json = ConfigLoader.getGson().toJson(this);
            Files.write(PATH, json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
