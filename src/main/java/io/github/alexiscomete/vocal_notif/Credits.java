package io.github.alexiscomete.vocal_notif;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class Credits extends CommandBot {
    public Credits() {
        super("Affiche les crédits", "credits", "Faites cette commande pour voir ce qu'elle fait !!");
    }

    @Override
    void execute(MessageCreateEvent messageCreateEvent, String content, String[] args) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("Code de base en 1 jour !").setTitle("Crédits").setColor(Color.green).setDescription("Idée de <@340593676801802250> (moi2985#5522). Il a un bot qui fait la même chose mais il veut le garder privé pour éviter de surcharger son VPS");
        messageCreateEvent.getMessage().reply(builder);
    }
}
