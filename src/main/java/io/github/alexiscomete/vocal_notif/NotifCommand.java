package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class NotifCommand extends CommandBot {

    public NotifCommand() {
        super("Permet de recevoir une notif lors de la connexion d'une personne à un salon vocal sur ce serveur", "notif");
    }

    @Override
    public void execute(SlashCommandCreateEvent event, SlashCommandInteraction interaction) {
        if (interaction.getServer().isPresent()) {
            VoiceManager.switchUser(interaction.getServer().get().getId(), interaction.getUser().getId());
            interaction.createImmediateResponder()
                    .setContent("✅")
                    .respond();
        } else {
            interaction.createImmediateResponder()
                    .setContent("Vous devez être dans un serveur")
                    .respond();
        }
    }
}
