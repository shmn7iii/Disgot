package net.shmn7iii.disgot.discord;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.shmn7iii.disgot.Config;
import net.shmn7iii.disgot.MessageSync;
import net.shmn7iii.disgot.spigot.whitelist;
import org.bukkit.Bukkit;

public class Event extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;
        if(e.getChannel().getId().equals(Config.MESSAGE_SYNC_CHANNEL)){
            String guild = e.getGuild().getName();
            String name = e.getAuthor().getName();
            String message = e.getMessage().getContentDisplay();
            MessageSync.SyncMessage2spi(guild,name,message);
        }
        else if(e.getChannel().getId().equals(Config.WHITELIST_CHANNEL)){
            String message = e.getMessage().getContentDisplay();
            e.getChannel().sendMessage(whitelist.addWhitelist(message)).reference(e.getMessage()).queue();
        }
    }

    @Override
    public void onReady(ReadyEvent e){
        MessageSync.sendMessage2disc("**[Sync]** Server launched.");
        Bukkit.getLogger().info("[Disgot] BOT is loaded.");
    }
}
