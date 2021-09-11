package io.github.alexiscomete.vocal_notif;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.VoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.entity.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class VoiceManager {

    static {
        try {
            SaveLocation.create("/voice-save/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void switchUser(long serverID, long userID) {
        SaveLocation<User> server = getServer(serverID);
        if (server == null) {
            System.out.println("null");
            return;
        }
        CompletableFuture<User> op = Main.api.getUserById(userID);
        op.thenAcceptAsync(user -> {
            System.out.println(user.getId());
            if (contains(server.getContent(), user)) { // the bug
                System.out.println("co");
                server.getContent().remove(user);
            } else {
                System.out.println("n");
                server.getContent().add(user);
            }
            server.saveAll();
        });
    }

    public static void notifServer(Server serv, VoiceChannel voiceChannel, User us) {
        System.out.println("notif");
        SaveLocation<User> server = getServer(serv.getId());
        if (server == null) {
            return;
        }
        CompletableFuture<Invite> inviteF = new InviteBuilder((ServerChannel) voiceChannel).setNeverExpire().create();
        System.out.println("Invite ->");
        inviteF.thenAcceptAsync(invite -> {
            System.out.println("<-");
            for (User user : server.getContent()) {
                System.out.println("User");
                if (user != null && voiceChannel.canConnect(user) && user.getId() != us.getId()) {
                    user.sendMessage("Salut ! <@" + us.getId() + "> (" + us.getName() + ")  est en vocal sur un serveur ... clique sur le lien pour le rejoindre : https://discord.gg/" + invite.getCode());
                }
            }
        });
    }


    public static SaveLocation<User> getServer(long server) {
        try {
            SaveLocation<User> saveLocation = new SaveLocation<>(" ", "/voice-save/" + server + ".txt", VoiceManager::toUser, ar -> {
                long id = ar.getId();
                return String.valueOf(id);
            });
            saveLocation.loadAll();
            return saveLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User toUser(String str) {
        System.out.println("to user : " + str);
        CompletableFuture<User> op = Main.api.getUserById(str); //src of the bug ?
        AtomicReference<User> user = new AtomicReference<>();
        op.thenAccept(user::set);
        System.out.println(user.get());
        return user.get();
    }

    public static boolean contains(ArrayList<User> users, User user) {
        System.out.println(users.size());
        for (User u : users) {
            if (u != null) {
                System.out.println(u.getId());
                System.out.println(user.getId());
                System.out.println(u.getId() == user.getId());
                if (u.getId() == user.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
