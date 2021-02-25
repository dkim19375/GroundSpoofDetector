package me.dkim19375.groundspoofdetector.util;

import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataHolder {
    private final Map<UUID, Location> lastLocations = new HashMap<>();
    private static final DataHolder instance = new DataHolder();

    public static DataHolder getInstance() {
        return instance;
    }

    public Map<UUID, Location> getLastLocations() {
        return lastLocations;
    }
}
