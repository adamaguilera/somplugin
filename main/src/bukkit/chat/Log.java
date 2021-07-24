package bukkit.chat;

import bukkit.Main;

import java.util.logging.Level;

public class Log {
    static Main instance;

    public static void UPDATE_INSTANCE () {
        instance = Main.GET_INSTANCE();
    }

    public static void INFO (final String message) {
        instance.getLogger().log(Level.INFO, message);
    }

    public static void ERROR (final String message) {
        instance.getLogger().log(Level.SEVERE, message);
    }

    public static void WARN (final String message) {
        instance.getLogger().log(Level.WARNING, message);
    }
}
