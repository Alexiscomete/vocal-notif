package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.message.MessageCreateEvent;

public class NotifCommand extends CommandBot {

    public NotifCommand() {
        super("Permet de recevoir une notif lors de la connexion d'une personne à un salon vocal sur ce serveur", "notif", "Permet de recevoir une notif lors de la connexion d'une personne à un salon vocal sur ce serveur, vous ne recevrez pas de message si le salon n'est pas visible pour vous, vous pouvez vous désabonner avec cette commande");
    }

    @Override
    void execute(MessageCreateEvent messageCreateEvent, String content, String[] args) {
        if (messageCreateEvent.isServerMessage()) {
            VoiceManager.switchUser(messageCreateEvent.getServer().get().getId(), messageCreateEvent.getMessageAuthor().getId());
            messageCreateEvent.getMessage().reply("✅");
        } else {
            messageCreateEvent.getMessage().reply("Vous devez être dans un serveur");
        }
    }
}
