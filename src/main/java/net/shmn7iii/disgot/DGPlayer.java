package net.shmn7iii.disgot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class DGPlayer {
    private String name;
    private String uuid;

    public String getName(){
        return name;
    }

    public void setName(String setname){
        this.name = setname;
    }

    /**
     *
     * @return MojangのAPI経由でプレイヤーのUUIDを取得，String型でリターン．エラーの場合はリターン内容を"error"に固定
     */
    public String getUUID(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream()));
            uuid = (((JsonObject)new JsonParser().parse(in)).get("id")).toString().replaceAll("\"", "");
            uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            in.close();
        } catch (Exception e) {
            uuid = "error";
        }
        return uuid;
    }

    public void setUUID(){ }
}