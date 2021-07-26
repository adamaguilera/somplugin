package som.game.spell.cooldown;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import som.chat.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class CooldownHandler {
    // player ID attached to this cooldown
    @Setter @Getter
    private UUID playerID;
    // cooldown in milliseconds
    private final int cooldown;
    // optional cooldown message, NOT FORMATTED but underscores are replaced
    // with the duration left
    private final String cooldownMessage;
    // cooldown for sending messages to avoid spamming player
    private static final int MESSAGE_COOLDOWN = 500;
    // name of ability using this cooldown object
    @NotNull
    @Getter
    private final String spellName;

    // last time the ability was cast
    private long lastCast = Long.MIN_VALUE;
    // last time a cooldown message was sent
    private long lastCooldownMessage;

    private final Chat chat = new Chat();

    /**
     * @return next time the ability is off cooldown in milliseconds
     */
    private long getNextCast () {
        return lastCast + (long) (cooldown);
    }

    /**
     * SIDE EFFECT NOTICE THAT THIS WILL SEND COOLDOWN MESSAGE IF NOT NULL
     * @return whether the ability is on cooldown
     */
    public boolean onCooldown () {
        long currentTime = System.currentTimeMillis();
        long nextCast = getNextCast();
        if (nextCast > currentTime) {
            // send cooldown message if it exists
            sendCooldownMessage ();
            return true;
        } else {
            return false;
        }
    }

    /**
     * sends cooldown message to the player
     */
    private void sendCooldownMessage () {
        if (cooldownMessage != null) {
            // check if not on cooldown
            if (lastCooldownMessage + MESSAGE_COOLDOWN <= System.currentTimeMillis()) {
                // update lastCooldownMessage
                lastCooldownMessage = System.currentTimeMillis();
                // send message
                // get the cooldown remaining
                double cooldownRemaining = (getNextCast() - System.currentTimeMillis()) / 1000.0;
                // possible bug if .replace all has side effect ??
                String message = cooldownMessage.replaceAll("_", String.format("%.2f", cooldownRemaining));
                chat.abilityMessage(playerID, message);
            }
        }
    }

    /**
     * Sets the last cast equal to the current time
     */
    public void putOnCooldown () {
        lastCooldownMessage = System.currentTimeMillis();
        lastCast = System.currentTimeMillis();
    }
    /**
     * Sets the last time to a specific time
     * @param time the time to set
     */
    public void setLastCast (long time) {
        lastCast = time;
    }

    public void removeCooldown () {
        lastCast = Long.MIN_VALUE;
    }
}
