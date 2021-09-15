package net.shmn7iii.disgot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class Whitelist {

    /**
     *
     * @param channel リプライするチャンネル
     * @param messageContent 発言者の発言内容(String型)
     * @param rawMessage 発言者の発言(Message型)
     */
    public static void replyWhitelistResult(MessageChannel channel, String messageContent, Message rawMessage){
        DGPlayer dgPlayer = new DGPlayer();
        dgPlayer.setName(messageContent);

        channel.sendMessage(addWhitelist(dgPlayer))
                .reference(rawMessage)
                .queue();
    }

    /**
     *
     * @param dgPlayer 追加するプレイヤーのDGPlayerインスタンス
     * @return リプライ文言をここで生成，リターン
     */
    public static String addWhitelist(DGPlayer dgPlayer){
        String name = dgPlayer.getName();
        String uuid = dgPlayer.getUUID();
        if(uuid.equals("error")) return "Can't get UUID of Player:"+name+". Please check your Player name again.\nプレイヤー:"+name+"のUUIDが見つかりませんでした。プレイヤー名を再度確認してください。";

        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
        if(player.isWhitelisted()){
            return "That Player is already exist in white list!\nそのプレイヤーはすでにホワイトリストに存在しています。";
        }

        writeWhitelist(dgPlayer);

        Bukkit.reloadWhitelist();
        return "Success! Player has added to white list!\n正常にホワイトリストへ追加しました。";
    }

    /**
     *
     * @param dgPlayer 書き込む対象プレイヤー
     */
    public static void writeWhitelist(DGPlayer dgPlayer){
        String name = dgPlayer.getName();
        String uuid = dgPlayer.getUUID();
        try {
            BufferedReader file = new BufferedReader(new FileReader("./whitelist.json"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                if(line.contains("[]")){
                    line = "[" +
                            "\n  {" +
                            "\n    \"uuid\":\""+uuid+"\"," +
                            "\n    \"name\":\""+name+"\"" +
                            "\n  }" +
                            "\n]";
                }
                else if(line.contains("]")){
                    line = "  ," +
                            "\n  {" +
                            "\n    \"uuid\":\""+uuid+"\"," +
                            "\n    \"name\":\""+name+"\"" +
                            "\n  }" +
                            "\n]";
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            FileOutputStream fileOut = new FileOutputStream("./whitelist.json");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Can't read whitelist.json.");
        }
    }
}
