package net.shmn7iii.disgot;

import net.shmn7iii.disgot.Disgot;
import net.shmn7iii.disgot.MessageSync;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpigotEvent implements Listener {
    public static Disgot plugin;
    public SpigotEvent(Disgot instance) { plugin = instance; }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        String playerName = e.getPlayer().getDisplayName();
        String message = e.getMessage();
        MessageSync.SyncMessage2disc(playerName,message);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        String playerName = e.getPlayer().getDisplayName();
        MessageSync.sendMessage2disc("**[Sync]** " + playerName + " joined the game.");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        String playerName = e.getPlayer().getDisplayName();
        MessageSync.sendMessage2disc("**[Sync]** " + playerName + " left the game.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        e.getDeathMessage();
        MessageSync.sendMessage2disc("**[Sync]** " + e.getDeathMessage());
    }
}
