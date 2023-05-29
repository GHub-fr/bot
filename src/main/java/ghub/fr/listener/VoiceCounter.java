package ghub.fr.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import ghub.fr.api.FileSystem;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class VoiceCounter {
    public static void startVoiceEvent() throws IOException, ExecutionException, InterruptedException {
        VoiceChannelCreator();
    }

    public static void VoiceChannelCreator() throws IOException, ExecutionException, InterruptedException {
        main.api.addServerVoiceChannelMemberJoinListener(event -> {
            try {
                checkUserTime(event.getUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void checkUserTime(User user) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    Server server = main.api.getServerById(IDs.serverID).get();
                    if (!user.getConnectedVoiceChannel(server).isEmpty()) {
                        if (server.getAfkChannel().isPresent() && user.getConnectedVoiceChannel(server).get()
                                .getIdAsString().equals(server.getAfkChannel().get().getIdAsString())) {
                            this.cancel();
                        } else {
                            if (!user.isMuted(server) && !user.isSelfMuted(server) && !user.isDeafened(server)
                                    && !user.isSelfDeafened(server)) {
                                if (user.getConnectedVoiceChannel(server).get().getConnectedUsers().size() >= 2) {
                                    File file = FileSystem.file(user);
                                    FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                                    fileConfiguration.set("voiceTime", (fileConfiguration.getInt("voiceTime") + 1));
                                    fileConfiguration.save(file);
                                }
                            }
                        }
                    } else {
                        this.cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static String voiceSecFormat(int duration) {
        if (duration < 60) {
            return duration + " secondes";
        } else if (duration >= 60 && duration < 3_600) {
            return (duration / 60) + " minutes";
        } else if (duration >= 3_600 && duration < 86_400) {
            return (duration / 3_600) + " heures";
        } else if (duration >= 86_400 && duration < 604_800) {
            return (duration / 86_400) + " heures";
        } else if (duration >= 604_800) {
            return (duration / 604_800) + " jours";
        }
        return "erreur";
    }
}
