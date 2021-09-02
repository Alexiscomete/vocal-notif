package io.github.alexiscomete.vocal_notif;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.awt.*;

public class Credits extends CommandBot {
    public Credits() {
        super("Affiche les crédits", "credits");
    }

    @Override
    public void execute(SlashCommandCreateEvent event, SlashCommandInteraction interaction) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("Hébergé par <@477122844208267301>").setTitle("Crédits").setColor(Color.green).setDescription("Idée de <@340593676801802250> (moi2985#5522). Il a un bot qui fait la même chose mais il veut le garder privé pour éviter de surcharger son VPS");
        interaction.createImmediateResponder()
                .addEmbed(builder)
                .respond();
    }
}
