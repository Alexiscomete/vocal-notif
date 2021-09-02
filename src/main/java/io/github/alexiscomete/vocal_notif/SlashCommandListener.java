package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.HashMap;

public class SlashCommandListener implements SlashCommandCreateListener {

    public static HashMap<String, CommandBot> commands = new HashMap<>();

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent slashCommandCreateEvent) {
        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();
        commands.get(interaction.getCommandName()).execute(slashCommandCreateEvent, interaction);
    }
}
