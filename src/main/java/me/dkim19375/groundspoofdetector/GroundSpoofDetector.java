package me.dkim19375.groundspoofdetector;

import me.dkim19375.dkim19375core.CoreJavaPlugin;
import me.dkim19375.groundspoofdetector.api.ListenerRegisterManager;

public class GroundSpoofDetector extends CoreJavaPlugin {

    @Override
    public void onEnable() {
        register();
    }

    @Override
    public void onDisable() {

    }

    public void register() {
        ListenerRegisterManager.register(this);
    }
}
