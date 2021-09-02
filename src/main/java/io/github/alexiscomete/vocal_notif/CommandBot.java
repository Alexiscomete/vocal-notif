package io.github.alexiscomete.vocal_notif;

import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public abstract class CommandBot implements SlashCommandCreateListener {

    String description, name;

    public CommandBot(String description, String name) {
        this.description = description;
        this.name = name;
    }
}
