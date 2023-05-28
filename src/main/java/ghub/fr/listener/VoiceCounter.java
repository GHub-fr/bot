package ghub.fr.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
                    if (!user.getConnectedVoiceChannel(main.api.getServerById(IDs.serverID).get()).isEmpty()) {
                        File file = FileSystem.file(user);
                        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                        fileConfiguration.set("voiceTime", (fileConfiguration.getInt("voiceTime") + 1));
                        fileConfiguration.save(file);
                    } else {
                        this.cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0,  1000);
    }
}
