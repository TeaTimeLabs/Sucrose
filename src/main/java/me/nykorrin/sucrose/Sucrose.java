package me.nykorrin.sucrose;

import me.nykorrin.sucrose.commands.ImageCommand;
import me.nykorrin.sucrose.data.ImageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Sucrose extends JavaPlugin {

    @Override
    public void onEnable() {
        long timeStart = System.currentTimeMillis();

        ImageManager.getInstance().init();

        getCommand("image").setExecutor(new ImageCommand(this));

        long timeEnd = System.currentTimeMillis();
        getLogger().info("Successfully enabled. (" + (timeEnd - timeStart) + "ms)");
    }

    @Override
    public void onDisable() {
        long timeStart = System.currentTimeMillis();

        ImageManager.getInstance().saveData();

        long timeEnd = System.currentTimeMillis();
        getLogger().info("Successfully disabled. (" + (timeEnd - timeStart) + "ms)");
    }
}
