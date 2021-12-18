package io.github.alexiscomete.vocal_notif;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.VoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.entity.user.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class VoiceManager {

    static {
        try {
            SaveLocation.create("/voice-save/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static short switchUser(long serverID, User user) {
        if (user == null) {
            System.out.println("user null");
            return -1;
        }
        System.out.println("...");
        SaveLocation<Long> server = getServer(serverID);
        System.out.println("---");
        if (server == null) {
            System.out.println("null");
            return -1;
        }
        System.out.println("User id :");
        System.out.println(user.getId());
        short answer;
        if (server.getContent().contains(user.getId())) { // the bug
            System.out.println("co");
            server.getContent().remove(user.getId());
            answer = 0;
        } else {
            System.out.println("n");
            server.getContent().add(user.getId());
            answer = 1;
        }
        server.saveAll();
        System.out.println("Save !");
        return answer;
    }

    public static void notifServer(Server serv, VoiceChannel voiceChannel, User us) {
        CompletableFuture<Invite> inviteF = new InviteBuilder((ServerChannel) voiceChannel).setNeverExpire().create();
        System.out.println("Invite ->");
        inviteF.thenAcceptAsync(invite -> {
            System.out.println("<-");
            SaveLocation<Long> server = getServer(serv.getId());
            if (server == null) {
                return;
            }
            System.out.println("notif");
            for (Long userId : server.getContent()) {
                CompletableFuture<User> coUser = Main.api.getUserById(userId);
                coUser.thenAcceptAsync(user -> {
                    System.out.println("User");
                    if (user == null) {
                        System.out.println("null");
                        return;
                    }
                    System.out.println(voiceChannel.canConnect(user));
                    System.out.println(userId != us.getId());
                    if (voiceChannel.canConnect(user) && userId != us.getId()) {
                        user.sendMessage("Salut ! <@" + us.getId() + "> (" + us.getName() + ")  est en vocal sur un serveur ... clique sur le lien pour le rejoindre : https://discord.gg/" + invite.getCode());
                    }
                });
            }
        });
    }


    public static SaveLocation<Long> getServer(long server) {
        try {
            SaveLocation<Long> saveLocation = new SaveLocation<>(" ", "/voice-save/" + server + ".txt", str -> {
                try {
                    return Long.parseLong(str);
                } catch (NumberFormatException e) {
                    System.out.println("err");
                    return null;
                }
            }, String::valueOf);
            saveLocation.loadAll();
            return saveLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
