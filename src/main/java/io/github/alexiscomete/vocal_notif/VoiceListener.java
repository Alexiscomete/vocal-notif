package io.github.alexiscomete.vocal_notif;

import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;

public class VoiceListener implements ServerVoiceChannelMemberJoinListener {
    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent serverVoiceChannelMemberJoinEvent) {
        VoiceManager.notifServer(serverVoiceChannelMemberJoinEvent.getServer(), serverVoiceChannelMemberJoinEvent.getChannel(), serverVoiceChannelMemberJoinEvent.getUser());
    }
}
