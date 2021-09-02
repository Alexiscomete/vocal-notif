package io.github.alexiscomete.vocal_notif;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommand;

import java.io.IOException;

public class Main {

    public static DiscordApi api;
    public static SaveLocation<String> config;

    static {
        try {
            config = new SaveLocation<>(";", "/config.txt", a -> a, b -> b);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        config.loadAll();
        api = new DiscordApiBuilder().setToken(config.getContent().get(0)).login().join();
        api.updateActivity("Prefix : /");

        api.addListener(new VoiceListener());

        addCommand(new NotifCommand());
        addCommand(new Credits());
    }

    public static void addCommand(CommandBot commandBot) {
        SlashCommand command = SlashCommand.with(commandBot.name, commandBot.description)
                .createGlobal(api)
                .join();
        api.addSlashCommandCreateListener(commandBot);
    }

}
