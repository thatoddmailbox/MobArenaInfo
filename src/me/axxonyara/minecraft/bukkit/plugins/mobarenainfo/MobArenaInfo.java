package me.axxonyara.minecraft.bukkit.plugins.mobarenainfo;
/*
 * Plugin checking code from https://github.com/acstache/MobArenaGod/blob/master/src/com/ACStache/ArenaGodPlus/ArenaGodPlus.java
 */

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.garbagemule.MobArena.MobArena;

public class MobArenaInfo extends JavaPlugin {
    @Override
    public void onEnable(){
        log("Searching for MobArena...");
    	PluginManager pm = this.getServer().getPluginManager();
        MobArena maPlugin = (MobArena)pm.getPlugin("MobArena");
        if(maPlugin != null && maPlugin.isEnabled()) {
            log("Found MobArena!");
            pm.registerEvents(new MobArenaListener(this), this);
        } else {
        	error("MobArena not found!");
        	error("This plugin requires MobArena!");
        	error("Disabling plugin.");
        	pm.disablePlugin(this);
        }
        /*log("Searching for SpoutPlugin...");
        Plugin spPlugin = pm.getPlugin("SpoutPlugin");
        if(spPlugin != null && spPlugin.isEnabled()) {
            log("Found SpoutPlugin!");
        } else {
        	error("SpoutPlugin not found!");
        	error("This plugin requires SpoutPlugin!");
        	error("Disabling plugin.");
        	pm.disablePlugin(this);
        }*/
        log("Enabled MobArenaInfo v1.0!");
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }
    
    public void log(String text) {
    	getLogger().info(text);
    }
    
    public void error(String text) {
    	getLogger().severe(text);
    }
}
