package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public abstract class CommandBot {

    String description, name;

    public CommandBot(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public abstract void execute(SlashCommandCreateEvent event, SlashCommandInteraction interaction);
}
