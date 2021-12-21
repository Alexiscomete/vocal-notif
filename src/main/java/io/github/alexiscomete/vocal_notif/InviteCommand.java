package io.github.alexiscomete.vocal_notif;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;

public class InviteCommand extends CommandBot {
    public InviteCommand() {
        super("Donne l'invitation du bot", "invit");
    }

    @Override
    public void execute(SlashCommandCreateEvent event, SlashCommandInteraction interaction) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("L'invitation comprend le bot + les / commands").setTitle("Invitation du bot").setColor(Color.BLUE).setDescription("https://discord.com/oauth2/authorize?client_id=872481358944026624&scope=applications.commands%20bot&permissions=8");
        interaction.createImmediateResponder()
                .addEmbed(builder)
                .respond();
    }
}
