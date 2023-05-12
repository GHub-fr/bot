package ghub.fr.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import ghub.fr.api.FileSystem;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class casinoProfil {
    public static EmbedBuilder profil(User user) throws IOException {
        Server server = main.api.getServerById(IDs.serverID).get();
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

        int Gold = fileConfiguration.getInt("Gold");
        int i = fileConfiguration.getInt("Messages");
        int Passifs = GetPlayPassif(user);
        int DayliClaim = fileConfiguration.getInt("DayliClaimCount");
        int totalInvites = 0;
        for (String invites : fileConfiguration.getStringList("ListeInvites")) {
            totalInvites += fileConfiguration.getInt("InvitesCounted." + invites);
        }

        Timestamp timestamp = new Timestamp(user.getJoinedAtTimestamp(server).get().toEpochMilli());
        Date date = new Date(timestamp.getTime());
        Timestamp timestamp2 = new Timestamp(user.getCreationTimestamp().toEpochMilli());
        Date date2 = new Date(timestamp2.getTime());

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail(user.getAvatar());
        embedBuilder.setTitle("\uD83D\uDC8E Profil");
        embedBuilder.setDescription(user.getMentionTag() + "  " + user.getDiscriminatedName()
                + "\n \uD83D\uDE80 a rejoint le : **__" + StatsTimer.DateFormated(date) + "__**"
                + "\nSur Discord depuis le : **__" + StatsTimer.DateFormated(date2) + "__**");
        embedBuilder.addInlineField("Rôle le plus haut",
                user.getRoles(server).get(user.getRoles(server).size() - 1).getMentionTag());
        embedBuilder.addInlineField("    \uD83D\uDCB0 Argent", "" + Gold);
        embedBuilder.addInlineField("    \uD83D\uDCAC Messages", "" + i);
        embedBuilder.addInlineField("    ⏳ Gains passifs", "" + Passifs);
        embedBuilder.addInlineField("    \uD83D\uDD59 Clique journalier", "" + DayliClaim);
        embedBuilder.addInlineField("    \uD83D\uDC8C Invitations", "" + totalInvites);

        List<Role> roles = user.getRoles(main.api.getServerById(IDs.serverID).get());
        ArrayList<Role> rolesEnd = new ArrayList<Role>();

        if (roles.contains(IDs.RoleHomme)) {
            rolesEnd.add(IDs.RoleHomme);
        } else if (roles.contains(IDs.RoleFemme)) {
            rolesEnd.add(IDs.RoleFemme);
        }

        if (roles.contains(IDs.RoleMineur)) {
            rolesEnd.add(IDs.RoleMineur);
        } else if (roles.contains(IDs.RoleMajeur)) {
            rolesEnd.add(IDs.RoleMajeur);
        }

        if (roles.contains(IDs.RoleModeration)) {
            rolesEnd.add(IDs.RoleModeration);
        }

        String rolesStr = "";
        for (Role role : rolesEnd) {
            rolesStr += role.getMentionTag() + ", ";
        }
        embedBuilder.addField("📍 Rôles spéciaux", rolesStr);

        return embedBuilder;
    }

    public static EmbedBuilder gainGold(User user, int montant, String text, ServerTextChannel serverTextChannel)
            throws IOException {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("\uD83C\uDFB0 Casino \uD83C\uDFB2 \uD83C\uDFB3");
        embedBuilder.setDescription(serverTextChannel.getMentionTag() + " " + user.getMentionTag());
        embedBuilder.setThumbnail(user.getAvatar());
        if (montant >= 1) {
            embedBuilder.addInlineField("Gains : " + text, montant + " \uD83D\uDCB0");
        } else {
            embedBuilder.addInlineField("Pertes : " + text, montant + " \uD83D\uDCB0");
        }
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        int Gold = fileConfiguration.getInt("Gold");
        embedBuilder.addInlineField("Total", (montant + Gold) + " \uD83D\uDCB0");
        fileConfiguration.set("Gold", Gold + montant);
        fileConfiguration.save(file);
        return embedBuilder;
    }

    public static EmbedBuilder gainGold(User user, int montant, String text, int montant2, int montant3, String text2,
            ServerTextChannel serverTextChannel)
            throws IOException {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("\uD83C\uDFB0 Casino \uD83C\uDFB2 \uD83C\uDFB3");
        embedBuilder.setDescription(serverTextChannel.getMentionTag() + " " + user.getMentionTag());
        embedBuilder.setThumbnail(user.getAvatar());
        if (montant >= 1) {
            embedBuilder.addInlineField("Gains : " + text, montant + " + " + montant3 + " \uD83D\uDCB0");
        } else {
            embedBuilder.addInlineField("Pertes : " + text, montant + " \uD83D\uDCB0");
        }

        embedBuilder.addInlineField(text + " : ", montant + " \uD83D\uDCB0");

        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        int Gold = fileConfiguration.getInt("Gold");
        embedBuilder.addInlineField("Total", (montant + Gold) + " \uD83D\uDCB0");
        fileConfiguration.set("Gold", Gold + montant);
        fileConfiguration.save(file);
        return embedBuilder;
    }

    public static boolean CanPlay(User user, int min, String gameName) throws IOException {
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if (fileConfiguration.get(gameName) != null) {
            Date date = (Date) fileConfiguration.get(gameName);
            Date dateNow = new Date();
            Date dateCheck = ReturnDateWithXMin(date, min);
            if (dateNow.after(dateCheck)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static void UpdatePlayDate(User user, String gameName) throws IOException {
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.set(gameName, new Date());
        fileConfiguration.set(gameName + "Count", fileConfiguration.getInt(gameName + "Count") + 1);
        fileConfiguration.save(file);
    }

    public static Date getPlayDate(User user, String gameName) throws IOException {
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        return (Date) fileConfiguration.get(gameName);
    }

    public static void clickDoPassif(User user) throws IOException {
        UpdatePlayPassif(user, 10);
        UpdatePlayPassif(user, (int) (GetPlayPassif(user) * 0.15));
    }

    public static void UpdatePlayPassif(User user, int montant) throws IOException {
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.set("GainsPassifs", fileConfiguration.getInt("GainsPassifs") + montant);
        fileConfiguration.save(file);
    }

    public static int GetPlayPassif(User user) throws IOException {
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        return fileConfiguration.getInt("GainsPassifs");
    }

    public static int GetGold(User user) throws IOException {
        File file = FileSystem.file(user);
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        return fileConfiguration.getInt("Gold");
    }

    public static Date ReturnDateWithXMin(Date date, int mins) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, mins);
        return calendar.getTime();
    }

    public static void msgPasAssezArgent(User user, int prix) throws IOException {
        user.sendMessage("__**Vous n'avez pas assez d'argent**__.\nVous avez "
                + casinoProfil.GetGold(user) + " sur " + prix
                + IDs.EmojiMoneyWithWings + " requis");
    }
}