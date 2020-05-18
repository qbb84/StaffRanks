package com.rewind.dev.staffranks;

import com.rewind.dev.staffranks.NPCs.MagicNpc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Enumeration;

public final class StaffRanks extends JavaPlugin {

    public static StaffRanks mainInstance;
    public static FileManagerr fileManagerr;

    @Override
    public void onEnable() {



        this.getLogger().info("Ranks enabled");
        getCommand("setrank").setExecutor(new rankCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new chatEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MagicNpc(), this);

        mainInstance = this;

        fileManagerr = new FileManagerr(this);

        /*
        For testing ranks, until uses of a config or db.
         */

        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static StaffRanks getMain(){
        return mainInstance;
    }

    public static  FileManagerr getFileManager() { return fileManagerr; }
}
