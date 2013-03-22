/*
 * Parts of this file are from https://github.com/acstache/MobArenaGod/blob/master/src/com/ACStache/ArenaGodPlus/PluginListeners/MobArenaListener.java.
 */
package me.axxonyara.minecraft.bukkit.plugins.mobarenainfo;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.garbagemule.MobArena.events.ArenaEndEvent;
import com.garbagemule.MobArena.events.ArenaPlayerDeathEvent;
import com.garbagemule.MobArena.events.ArenaPlayerJoinEvent;
import com.garbagemule.MobArena.events.ArenaPlayerLeaveEvent;
import com.garbagemule.MobArena.events.ArenaStartEvent;
import com.garbagemule.MobArena.events.NewWaveEvent;
import com.garbagemule.MobArena.framework.Arena;

public class MobArenaListener implements Listener
{
    private MobArenaInfo plugin;
    
    public MobArenaListener(MobArenaInfo plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onMAPlayerJoin(ArenaPlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        Arena arena = event.getArena();
        plugin.log("The player " + p.getName() + " has joined the arena " + arena.arenaName() + "!");
    }
    
    @EventHandler
    public void onMAPlayerDeath(ArenaPlayerDeathEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        plugin.log("The player " + p.getName() + " has died in the arena " + arena.arenaName() + "!");
    	SpoutPlayer spoutie = (SpoutPlayer) p;
    	spoutie.getMainScreen().removeWidgets(plugin);        
    }
    
    @EventHandler
    public void onMAPlayerLeave(ArenaPlayerLeaveEvent event)
    {
        Arena arena = event.getArena();
        Player p = event.getPlayer();
        plugin.log("The player " + p.getName() + " has left the arena " + arena.arenaName() + "!");
    	SpoutPlayer spoutie = (SpoutPlayer) p;
    	spoutie.getMainScreen().removeWidgets(plugin);
    }
    
    @EventHandler
    public void onMANewWave(NewWaveEvent event)
    {
        Arena arena = event.getArena();
        Integer newWave = event.getWaveNumber(); 
        plugin.log("The arena " + arena.arenaName() + " has moved on to wave " + newWave.toString() + "!");
        redrawWaveLabels(arena.getPlayersInArena(), newWave);
    }
    
    @EventHandler
    public void onMAArenaEnd(ArenaEndEvent event)
    {
        Arena arena = event.getArena();
        plugin.log("The arena " + arena.arenaName() + " has ended!");
    }
    
    @EventHandler
    public void onMAArenaStart(ArenaStartEvent event)
    {
        Arena arena = event.getArena();
        plugin.log("The arena " + arena.arenaName() + " has started!");
    }
    
    public void redrawPlayers(Set<Player> redrawFor) {
    	String pText = "";
        for (Player toShow : redrawFor) {
        	pText = toShow.getDisplayName() + "\n" + ((Integer)(toShow.getHealth() / 2)).toString() + " hearts\n";
        }
        for (Player toShow : redrawFor) {
        	GenericLabel waveLabel = new GenericLabel();
            waveLabel.setAnchor(WidgetAnchor.TOP_LEFT);
            waveLabel.setText(pText);
        	((SpoutPlayer) toShow).getMainScreen().removeWidgets(plugin);
        	((SpoutPlayer) toShow).getMainScreen().attachWidget(plugin, waveLabel);
        }
    }
    
    public void redrawWaveLabels(Set<Player> redrawFor, Integer newWave) {
        for (Player toShow : redrawFor) {
        	redrawWaveLabel((SpoutPlayer) toShow, newWave);
        }
    }
    
    public void redrawWaveLabel(SpoutPlayer toRedraw, Integer newWave) {
    	GenericLabel waveLabel = new GenericLabel();
        waveLabel.setAnchor(WidgetAnchor.TOP_CENTER);
        waveLabel.setText("Wave " + newWave.toString());
    	toRedraw.getMainScreen().removeWidgets(plugin);
    	toRedraw.getMainScreen().attachWidget(plugin, waveLabel);
    }
}
