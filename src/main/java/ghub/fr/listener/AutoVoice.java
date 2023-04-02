package ghub.fr.listener;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AutoVoice {
    public static ArrayList<String> voice = new ArrayList<>();

    public static void startVoiceEvent() throws IOException, ExecutionException, InterruptedException {
        VoiceChannelCreator();
        LeaveChannelDestructor();
    }

    public static void VoiceChannelCreator() throws IOException, ExecutionException, InterruptedException {
        main.api.addServerVoiceChannelMemberJoinListener(event -> {
            try {
                if (IDs.VoiceChannelsCreator().contains(event.getChannel().getIdAsString())) {
                    Permissions permissions = new PermissionsBuilder().setAllowed(PermissionType.MANAGE_CHANNELS)
                            .build();
                    ServerVoiceChannel channel = new ServerVoiceChannelBuilder(event.getServer())
                            .setName("\uD83D\uDCDE" + event.getUser().getDiscriminatedName())
                            .setCategory(event.getChannel().getCategory().get())
                            .addPermissionOverwrite(event.getUser(), permissions).create().join();
                    voice.add(channel.getIdAsString());
                    event.getUser().move(channel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void LeaveChannelDestructor() {
        main.api.addServerVoiceChannelMemberLeaveListener(event -> {
            if (voice.contains(event.getChannel().getIdAsString())) {
                if (event.getChannel().getConnectedUsers().isEmpty()) {
                    voice.remove(event.getChannel().getIdAsString());
                    event.getChannel().delete();
                }
            }
        });
    }
}