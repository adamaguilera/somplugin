package som;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class ConfigLoader {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // private GameConfig gameConfig;

    public void loadAll() {
        // gameConfig = loadGameConfig();
        // lobbyConfig = loadLobbyConfig();
    }

    /*
    private GameConfig loadGameConfig() {
        try {
            String json = new String(Files.readAllBytes(GameConfig.PATH));
            // validateConfig ()
            return GSON.fromJson(json, GameConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
     */


    public static Gson getGson () { return GSON; }
}

