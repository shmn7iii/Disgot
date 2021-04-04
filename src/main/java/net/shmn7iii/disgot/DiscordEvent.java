package net.shmn7iii.disgot;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class DiscordEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor().isBot()) return;

        String guildName = e.getGuild().getName();
        String channelName = e.getChannel().getName();
        String authorName = e.getAuthor().getName();
        String messageContent = e.getMessage().getContentDisplay();

        MessageChannel channel = e.getChannel();
        String channelID = channel.getId();

        if(channelID.equals(Config.MESSAGE_SYNC_CHANNEL)){
            MessageSync.SyncMessage2spi(guildName,authorName,messageContent);
        }
        else if(channelID.equals(Config.WHITELIST_CHANNEL)){
            Whitelist.replyWhitelistResult(channel,messageContent,e.getMessage());
        }
    }

    @Override
    public void onReady(ReadyEvent e){
        MessageSync.sendMessage2disc("**[Sync]** Server launched.");
        Bukkit.getLogger().info("[Disgot] BOT is loaded.");
    }
}
