package ghub.fr.listener;

import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//Ajouter nombre channel (Voc / text / Forum / Thread), et user en cours de vocal

public class StatsTimer {
    public static void StatsTimerRunner() {
        TimerTask task = new TimerTask() {
            public void run() {
                String MessageRole = "";
                for (Role role : roles()) {
                    if (role.equals(IDs.RoleGenshinImpact) || role.equals(IDs.RoleHomme) || role.equals(IDs.RolePC)) {
                        MessageRole += "\n";
                    }
                    MessageRole += rolesString(role) + "\n";
                }

                int DND = 0;
                int Online = 0;
                int IDLE = 0;
                int Offline = 0;
                int Bot = 0;
                int roleLess = 0;
                int onlyRules = 0;
                for (User user : main.api.getServerById(IDs.serverID).get().getMembers()) {
                    if (user.isBot()) {
                        Bot++;
                    } else {
                        if (user.getRoles(main.api.getServerById(IDs.serverID).get()).size() == 1) {
                            roleLess++;
                        }
                        if (user.getRoles(main.api.getServerById(IDs.serverID).get())
                                .size() == IDs.defaultRoleSizeWithSplitterAndRulesAndEveryOne) {
                            if (user.getRoles(main.api.getServerById(IDs.serverID).get())
                                    .contains(IDs.RoleReglesValides)) {
                                onlyRules++;
                            }
                        }
                        if (user.getStatus().equals(UserStatus.DO_NOT_DISTURB)) {
                            DND++;
                        } else if (user.getStatus().equals(UserStatus.ONLINE)) {
                            Online++;
                        } else if (user.getStatus().equals(UserStatus.IDLE)) {
                            IDLE++;
                        } else if (user.getStatus().equals(UserStatus.OFFLINE)) {
                            Offline++;
                        }
                    }
                }
                String MessageUser = ":green_circle: **En ligne** : " + Online + "\n" + ":red_circle: **Hors ligne** : "
                        + Offline + "\n" + ":no_entry: **Ne pas déranger** : " + DND + "\n"
                        + ":crescent_moon: **Absent** : " + IDLE + "\n" + ":robot: Bot : " + Bot;

                Role everyone = IDs.RoleEveryone;

                String everHereOne = rolesString(everyone);
                String StringroleLess = IDs.EmojiRocket + " Sans rôle : " + roleLess;
                String RulesOnly = IDs.RoleReglesValides.getMentionTag() + " uniquement : " + onlyRules;

                long time = Instant.now().getEpochSecond();
                Date date = new Date();
                date.setMinutes(date.getMinutes() + 15);
                long time2 = date.toInstant().getEpochSecond();
                String finalMessage = "<t:" + time + ":R>" + ", prochain : " + "<t:" + time2 + ":R>" + "\n\n"
                        + MessageUser + "\n\n" + StringroleLess
                        + "\n" + everHereOne + "\n" + RulesOnly + "\n\n" + MessageRole;
                try {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setDescription(finalMessage);
                    main.api.getServerById(IDs.serverID).get().getChannelById(IDs.Statistiques).get()
                            .asServerTextChannel().get().getMessages(1).get().getOldestMessage().get()
                            .edit(embedBuilder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 1000 * 5, 1000 * 60 * 15);
    }

    public static ArrayList<Role> roles() {
        ArrayList<Role> role = new ArrayList<>();
        role.add(IDs.RoleBoost);
        role.add(IDs.RoleVIP);
        role.add(IDs.RoleReglesValides);
        //
        for (Role role2 : IDs.RolesJeux()) {
            role.add(role2);
        }
        //
        role.add(IDs.RoleHomme);
        role.add(IDs.RoleFemme);
        role.add(IDs.RoleMineur);
        role.add(IDs.RoleMajeur);
        //
        role.add(IDs.RolePC);
        role.add(IDs.RoleTelephone);
        role.add(IDs.RoleXBox);
        role.add(IDs.RolePS);
        role.add(IDs.RoleSwitch);
        return role;
    }

    public static String rolesString(Role role) {
        return role.getMentionTag() + " : " + role.getUsers().size();
    }

    public static DateFormat dateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    public static String DateFormated(Date date) {
        return dateFormat().format(date);
    }

    public static void setCounterOnCategory(int users) {
        int nextCap = (int) (Math.ceil(users / 10.0) * 10);
        IDs.CategoryGHub.updateName("●▬▬๑ " + users + "/" + nextCap + " 🙋๑▬▬▬●").join();
    }
}