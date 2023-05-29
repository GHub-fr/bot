package ghub.fr.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
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

    public static String msgVoice = "\n\n**__Top vocal__** :call_me: :\n";

    public static String msgFinalCategorie = "";

    public static void Start() {
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    Update(true, false, false, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000 * 10, 1000 * 60 * 60 * 8);
    }

    public static void Update(boolean isGold, boolean isMsg, boolean isInvite, boolean isVoice)
            throws IOException, ExecutionException, InterruptedException {
        int top = 0;
        User userTop = main.api.getYourself();
        for (User user : main.api.getServerById(IDs.serverID).get().getMembers()) {
            if (!users.contains(user) && !user.isBot()) {
                File file = FileSystem.file(user);
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

                int valueToCheck = 0;
                if (isGold) {
                    valueToCheck = fileConfiguration.getInt("Gold");
                } else if (isMsg) {
                    valueToCheck = fileConfiguration.getInt("Messages");
                } else if (isInvite) {
                    int invite = 0;
                    for (String invites : fileConfiguration.getStringList("ListeInvites")) {
                        invite += fileConfiguration.getInt("InvitesCounted." + invites);
                    }
                    valueToCheck = invite;
                } else if (isVoice) {
                    valueToCheck = fileConfiguration.getInt("voiceTime");
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
            Update(isGold, isMsg, isInvite, isVoice);
        } else {
            if (isGold) {
                long time = Instant.now().getEpochSecond();
                Date date = new Date();
                date.setHours(date.getHours() + 8);
                long time2 = date.toInstant().getEpochSecond();
                msgFinalCategorie += "<t:" + time + ":R>" + ", prochain : " + "<t:" + time2 + ":R>" + msgGold;
            } else if (isMsg) {
                msgFinalCategorie += msgMsg;
            } else if (isInvite) {
                msgFinalCategorie += msgInvite;
            } else if (isVoice) {
                msgFinalCategorie += msgVoice;
            }

            int i = 0;
            for (User user : users) {
                msgFinalCategorie += messageUser(user, i, isGold, isMsg, isInvite, isVoice, usersTopVal.get(i)) + "\n";
                File file = FileSystem.file(user);
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                int gold = fileConfiguration.getInt("Gold") + goldRewardBoost(i, isInvite, isVoice);
                fileConfiguration.set("Gold", gold);
                fileConfiguration.save(file);
                i++;
            }
            msg += msgFinalCategorie;
            msgFinalCategorie = "";

            users = new ArrayList<>();
            usersTopVal = new ArrayList<>();

            if (isGold) {
                Update(false, true, false, false);
            } else if (isMsg) {
                Update(false, false, true, false);
            } else if (isInvite) {
                Update(false, false, false, true);
            }

            if (isVoice) {
                try {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setDescription(msg);

                    main.api.getServerById(IDs.serverID).get().getChannelById(IDs.Tops).get().asServerTextChannel()
                            .get().getMessages(1).get().getOldestMessage().get().edit(embedBuilder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg = "";
            }
        }
    }

    public static String messageUser(User user, int i, boolean isGold, boolean isMsg, boolean isInvite, boolean isVoice,
            int valueScore) {
        int goldReward = goldRewardBoost(i, isInvite, isVoice);

        String endMsg = "";
        if (isGold) {
            endMsg += " ";
        } else if (isMsg) {
            endMsg += " messages";
        } else if (isInvite) {
            endMsg += " invitations";
        } else if (isVoice) {
            endMsg += " (" + VoiceCounter.voiceSecFormat(valueScore) + ")";
        }

        switch (i) {
            case 0:
                return ":first_place:" + user.getMentionTag() + "   +" + goldReward
                        + ":moneybag:   Score: __**" + valueScore + endMsg + "**__";
            case 1:
                return ":second_place:" + user.getMentionTag() + "   +" + goldReward
                        + ":moneybag:   Score: **" + valueScore + endMsg + "**";
            case 2:
                return ":third_place:" + user.getMentionTag() + "   +" + goldReward + ":moneybag:   Score: "
                        + valueScore + endMsg + " !";
            case 3:
            case 4:
                return user.getMentionTag() + "   +" + goldReward + ":moneybag:   Score: " + valueScore + endMsg;
        }
        return "";
    }

    public static int goldRewardBoost(int i, boolean IsInvite, boolean isVoice) {
        int goldReward = goldReward(i);
        if (IsInvite) {
            goldReward = goldReward * 5;
        } else if (isVoice) {
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