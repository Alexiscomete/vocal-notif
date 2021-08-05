package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.message.MessageCreateEvent;

public abstract class CommandBot {

    String description, name, totalDescription;
    String[] perms;

    public CommandBot(String description, String name, String totalDescription, String... perms) {
        this.description = description;
        this.name = name;
        this.totalDescription = totalDescription;
        this.perms = perms;
    }

    abstract void execute(MessageCreateEvent messageCreateEvent, String content, String[] args);
}
