package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class NotifCommand extends CommandBot {

    public NotifCommand() {
        super("Permet de recevoir une notif lors de la connexion d'une personne à un salon vocal sur ce serveur", "notif");
    }

    @Override
    public void execute(SlashCommandCreateEvent event, SlashCommandInteraction interaction) {
        if (interaction.getServer().isPresent()) {
            short answer = VoiceManager.switchUser(interaction.getServer().get().getId(), interaction.getUser());
            if (answer == -1) {
                interaction.createImmediateResponder()
                        .setContent("Une erreur est survenue")
                        .respond();
            } else if (answer == 1) {
                interaction.createImmediateResponder()
                        .setContent("✅ Notifications activées")
                        .respond();
            } else {
                interaction.createImmediateResponder()
                        .setContent("❌ notifications désactivées")
                        .respond();
            }

        } else {
            interaction.createImmediateResponder()
                    .setContent("Vous devez être dans un serveur")
                    .respond();
        }
    }
}
