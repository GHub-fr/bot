package ghub.fr.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import ghub.fr.api.FileSystem;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

public class JoinLeaveListener {
    public static void startLeaveJoinEvent() {
        MemberLeaveListener();
        MemberJoinListener();
    }

    public static void MemberLeaveListener() {
        main.api.addServerMemberLeaveListener(event -> {
            try {
                User user = event.getUser();
                File file = FileSystem.file(user);
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                int total = 0;
                int i = fileConfiguration.getInt("Messages");
                for (String invites : fileConfiguration.getStringList("ListeInvites")) {
                    total += fileConfiguration.getInt("InvitesCounted." + invites);
                }
                Timestamp timestamp2 = new Timestamp(user.getCreationTimestamp().toEpochMilli());
                Date date2 = new Date(timestamp2.getTime());
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setThumbnail(user.getAvatar());
                embedBuilder.setTitle("\uD83D\uDEAB Aurevoir");
                embedBuilder.setDescription(user.getMentionTag() + "  " + user.getDiscriminatedName()
                        + "\nSur Discord depuis le : **__" + StatsTimer.DateFormated(date2) + "__**");
                embedBuilder.addInlineField("Messages", "" + i);
                embedBuilder.addInlineField("Invitations", "" + total);
                main.api.getChannelById(IDs.Invitation).get().asServerTextChannel().get().sendMessage(embedBuilder);
                // checkDeleteUserMessage.deleteAllMessageFromUser("Quitte le serveur",
                // main.api.getYourself(), event.getUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void MemberJoinListener() {
        main.api.addServerMemberJoinListener(event -> {
            try {
                CheckInvite.onCheckInvite(event.getUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}