package io.github.alexiscomete.vocal_notif;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.VoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.entity.user.User;

import java.io.IOException;
import java.util.Optional;
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
        SaveLocation<User> server = getServer(serverID);
        Optional<User> op = Main.api.getCachedUserById(userID);
        if (!op.isPresent()) {
            return;
        }
        if (server == null) {
            return;
        }
        User user = op.get();
        if (server.getContent().contains(user)) {
            server.getContent().remove(user);
        } else {
            server.getContent().add(user);
        }
        server.saveAll();
    }

    public static void notifServer(Server serv, VoiceChannel voiceChannel, User us) {
        SaveLocation<User> server = getServer(serv.getId());
        if (server == null) {
            return;
        }
        CompletableFuture<Invite> inviteF = new InviteBuilder((ServerChannel) voiceChannel).setNeverExpire().create();
        inviteF.thenAcceptAsync(invite -> {
            for (User user : server.getContent()) {
                if (voiceChannel.canConnect(user)) {
                    user.sendMessage("Salut ! <@" + us.getId() + "> (" + us.getName() + ")  est en vocal sur un server ... clique sur le lien pour le rejoindre : " + invite.getCode());
                }
            }
        });
    }


    public static SaveLocation<User> getServer(long server) {
        try {
            SaveLocation<User> saveLocation = new SaveLocation<>(" ", "/voice-save/" + server + ".txt", VoiceManager::toUser, ar -> String.valueOf(ar.getId()));
            saveLocation.loadAll();
            return saveLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User toUser(String str) {
        Optional<User> op = Main.api.getCachedUserById(str);
        return op.orElse(null);
    }
}
