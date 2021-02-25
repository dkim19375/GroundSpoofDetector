package me.dkim19375.groundspoofdetector.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The event that will fire when a player is suspected of spoofing the ground.
 */
public class GroundSpoofEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final PlayerMoveEvent playerMoveEvent;

    public GroundSpoofEvent(@NotNull Player player, PlayerMoveEvent event) {
        super(player);
        this.playerMoveEvent = event;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public PlayerMoveEvent getPlayerMoveEvent() {
        return playerMoveEvent;
    }
}
