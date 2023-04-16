package ghub.fr.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.user.User;

import ghub.fr.api.FileSystem;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class Tops {
    public static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<Integer> usersTopVal = new ArrayList<>();

    public static String msg = "";

    public static String msgGold = "\n\n**__Top fortunes__** :euro: :\n";

    public static String msgMsg = "\n\n**__Top messages__** :speech_balloon: :\n";

    public static String msgInvite = "\n\n**__Top invitations__** :envelope: :\n";

    public static String msgFinalCategorie = "";

    public static void Start() {
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    Update(true, false, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000 * 10, 1000 * 60 * 60 * 8);
    }

    public static void Update(boolean IsGold, boolean IsMsg, boolean IsInvite)
            throws IOException, ExecutionException, InterruptedException {
        int top = 0;
        User userTop = main.api.getYourself();
        for (User user : main.api.getServerById(IDs.serverID).get().getMembers()) {
            if (!users.contains(user) && !user.isBot()) {
                File file = FileSystem.file(user);
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

                int valueToCheck = 0;
                if (IsGold) {
                    valueToCheck = fileConfiguration.getInt("Gold");
                } else if (IsMsg) {
                    valueToCheck = fileConfiguration.getInt("Messages");
                } else if (IsInvite) {
                    int invite = 0;
                    for (String invites : fileConfiguration.getStringList("ListeInvites")) {
                        invite += fileConfiguration.getInt("InvitesCounted." + invites);
                    }
                    valueToCheck = invite;
                }

                if (top < valueToCheck) {
                    top = valueToCheck;
                    userTop = user;
                }
            }
        }
        users.add(userTop);
        usersTopVal.add(top);
        if (users.size() <= 4) {
            Update(IsGold, IsMsg, IsInvite);
        } else {
            if (IsGold) {
                long time = Instant.now().getEpochSecond();
                Date date = new Date();
                date.setHours(date.getHours() + 8);
                long time2 = date.toInstant().getEpochSecond();
                msgFinalCategorie += "<t:" + time + ":R>" + ", prochain dans 8h : " + "<t:" + time2 + ":R>" + msgGold;
            } else if (IsMsg) {
                msgFinalCategorie += msgMsg;
            } else if (IsInvite) {
                msgFinalCategorie += msgInvite;
            }

            int i = 0;
            for (User user : users) {

                msgFinalCategorie += messageUser(user, i, IsInvite, usersTopVal.get(i)) + "\n";
                File file = FileSystem.file(user);
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                int gold = fileConfiguration.getInt("Gold") + goldRewardBoost(i, IsInvite);
                fileConfiguration.set("Gold", gold);
                fileConfiguration.save(file);
                i++;
            }
            msg += msgFinalCategorie;
            msgFinalCategorie = "";

            users = new ArrayList<>();
            usersTopVal = new ArrayList<>();

            if (IsGold) {
                Update(false, true, false);
            } else if (IsMsg) {
                Update(false, false, true);
            }
            if (IsInvite) {
                try {
                    main.api.getServerById(IDs.serverID).get().getChannelById(IDs.Tops).get().asServerTextChannel()
                            .get().getMessages(1).get().getOldestMessage().get().edit((msg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg = "";
            }
        }
    }

    public static String messageUser(User user, int i, boolean IsInvite, int valueScore) {
        int goldReward = goldRewardBoost(i, IsInvite);

        switch (i) {
            case 0:
                return ":first_place:" + user.getMentionTag() + "       +" + goldReward
                        + ":moneybag:       Score: __**" + valueScore + "**__";
            case 1:
                return ":second_place:" + user.getMentionTag() + "       +" + goldReward
                        + ":moneybag:       Score: **" + valueScore + "**";
            case 2:
                return ":third_place:" + user.getMentionTag() + "       +" + goldReward + ":moneybag:       Score: "
                        + valueScore + " !";
            case 3:
            case 4:
                return user.getMentionTag() + "       +" + goldReward + ":moneybag:       Score: " + valueScore;
        }
        return "";
    }

    public static int goldRewardBoost(int i, boolean IsInvite) {
        int goldReward = goldReward(i);
        if (IsInvite) {
            goldReward = goldReward * 2;
        }
        return goldReward;
    }

    public static int goldReward(int i) {
        switch (i) {
            case 0:
                return 500;
            case 1:
                return 250;
            case 2:
                return 125;
            case 3:
                return 60;
            case 4:
                return 20;
        }
        return 0;
    }
}