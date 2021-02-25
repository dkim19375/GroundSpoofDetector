package me.dkim19375.groundspoofdetector.api;

import me.dkim19375.groundspoofdetector.listener.PlayerMoveListener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerRegisterManager {
    private static final PlayerMoveListener listener = new PlayerMoveListener();

    public static void register(JavaPlugin plugin) {
        boolean registered = false;
        for (RegisteredListener registeredListener : PlayerMoveEvent.getHandlerList().getRegisteredListeners()) {
            if (registeredListener.getListener() instanceof PlayerMoveListener) {
                registered = true;
                break;
            }
        }
        if (!registered) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }
}
