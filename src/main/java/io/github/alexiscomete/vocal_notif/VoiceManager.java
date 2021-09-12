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

    public static void switchUser(long serverID, long userID) {
        CompletableFuture<User> op = Main.api.getUserById(userID);
        op.thenAcceptAsync(user -> {
            SaveLocation<Long> server = getServer(serverID);
            if (server == null) {
                System.out.println("null");
                return;
            }
            System.out.println(user.getId());
            if (server.getContent().contains(user.getId())) { // the bug
                System.out.println("co");
                server.getContent().remove(user.getId());
            } else {
                System.out.println("n");
                server.getContent().add(user.getId());
            }
            server.saveAll();
        });
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
                    if (user != null && voiceChannel.canConnect(user) && userId != us.getId()) {
                        user.sendMessage("Salut ! <@" + us.getId() + "> (" + us.getName() + ")  est en vocal sur un serveur ... clique sur le lien pour le rejoindre : https://discord.gg/" + invite.getCode());
                    }
                });
            }
        });
    }


    public static SaveLocation<Long> getServer(long server) {
        try {
            SaveLocation<Long> saveLocation = new SaveLocation<>(" ", "/voice-save/" + server + ".txt", Long::parseLong, String::valueOf);
            saveLocation.loadAll();
            return saveLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
