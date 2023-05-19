package ghub.fr.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.invite.RichInvite;
import org.javacord.api.entity.user.User;

import ghub.fr.api.FileSystem;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckInvite {
    public static Collection<RichInvite> before = null;

    public static void start() throws ExecutionException, InterruptedException {
        before = main.api.getServerById(IDs.serverID).get().getInvites().get();
        main.api.addServerChannelInviteCreateListener(event -> {
            try {
                before = main.api.getServerById(IDs.serverID).get().getInvites().get();
                String code = event.getInvite().getCode();
                for (RichInvite richInvite : main.api.getServerById(IDs.serverID).get().getInvites().get()) {
                    if (richInvite.getCode().equals(code)) {
                        if (richInvite.getMaxUses() == 0
                                && richInvite.getMaxAgeInSeconds() == 0) {
                            richInvite.getInviter().get().sendMessage(
                                    "\uD83D\uDE80Tu a crée une **invitation pour rejoindre le serveur sans limite**, elle sera **compté par le bot**, la voici : "
                                            + richInvite.getUrl());
                        } else {
                            richInvite.getInviter().get().sendMessage(
                                    "\uD83D\uDE80Tu a crée une **invitation pour rejoindre le serveur**, elle sera **compté par le bot**, la voici : "
                                            + richInvite.getUrl()
                                            + "\n__**Attention, vous n'avez pas généré une invitation définitive**__ !");
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void onCheckInvite(User user) throws ExecutionException, InterruptedException, IOException {
        Collection<RichInvite> after = main.api.getServerById(IDs.serverID).get().getInvites().get();
        for (RichInvite richInvite : before) {
            for (RichInvite richInvite1 : after) {
                if (richInvite.getUses() < richInvite1.getUses()
                        && richInvite.getCode().equals(richInvite1.getCode())) {
                    before = main.api.getServerById(IDs.serverID).get().getInvites().get();
                    User inviter = richInvite.getInviter().get();

                    File file = FileSystem.file(inviter);
                    File fileGlobal = FileSystem.fileGlobal();
                    FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                    FileConfiguration fileConfigurationGlobal = YamlConfiguration.loadConfiguration(fileGlobal);

                    /*
                     * fileConfiguration.set("Invites." + richInvite1.getCode(),
                     * richInvite1.getUses());
                     * fileConfiguration.save(file);
                     * fileConfiguration = YamlConfiguration.loadConfiguration(file);
                     */

                    List<String> listGlobal = fileConfigurationGlobal.getStringList("globalInvites");
                    if (!listGlobal.contains(user.getIdAsString())) {
                        listGlobal.add(user.getIdAsString());
                        fileConfigurationGlobal.set("globalInvites", listGlobal);
                        fileConfigurationGlobal.save(fileGlobal);

                        int gold = fileConfiguration.getInt("Gold") + 250;
                        fileConfiguration.set("Gold", gold);
                        fileConfiguration.set("InvitesCounted." + richInvite1.getCode(),
                                fileConfiguration.getInt("InvitesCounted." + richInvite1.getCode()) + 1);
                        List<String> list = fileConfiguration.getStringList("ListeInvites");
                        if (!list.contains(richInvite.getCode())) {
                            list.add(richInvite.getCode());
                            fileConfiguration.set("ListeInvites", list);
                        }
                        fileConfiguration.save(file);

                        fileConfiguration = YamlConfiguration.loadConfiguration(file);
                        int linkCountedUses = fileConfiguration.getInt("InvitesCounted." + richInvite1.getCode());
                        int total = 0;
                        for (String invites : fileConfiguration.getStringList("ListeInvites")) {
                            total += fileConfiguration.getInt("InvitesCounted." + invites);
                        }
                        bonusRoles.InviteRole(total, inviter);

                        File fileEvent = FileSystem.event();
                        FileConfiguration fileConfigurationEvent = YamlConfiguration.loadConfiguration(fileEvent);
                        boolean started = fileConfigurationEvent.getBoolean("Started");
                        String eventMsg = "";
                        if (started) {
                            Date date = new Date();
                            int day = fileConfigurationEvent.getInt("DayEnd");
                            int month = fileConfigurationEvent.getInt("MonthEnd") - 1;
                            int year = fileConfigurationEvent.getInt("YearEnd");
                            Date dateEventEnd = new GregorianCalendar(year, month, day).getTime();
                            if (date.before(dateEventEnd)) {
                                file = FileSystem.file(inviter);
                                fileConfiguration = YamlConfiguration.loadConfiguration(file);
                                fileConfiguration.set("event", fileConfiguration.getInt("event") + 1);
                                fileConfiguration.save(file);
                                eventMsg = "\n\nTu est à " + fileConfiguration.getInt("event")
                                        + " invitations pour le concours ! \uD83C\uDF81 \uD83D\uDCC8";
                                user.sendMessage(eventMsg);
                            }
                        }

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setThumbnail(user.getAvatar());
                        embedBuilder.setTitle("\uD83C\uDD95 Arrivée");
                        Timestamp timestamp2 = new Timestamp(user.getCreationTimestamp().toEpochMilli());
                        Date date2 = new Date(timestamp2.getTime());
                        embedBuilder.setDescription("Bienvenue à toi " + user.getMentionTag() + "  "
                                + user.getDiscriminatedName() + "\nSur Discord depuis le : **__"
                                + StatsTimer.DateFormated(date2) + "__**\n\n");
                        embedBuilder.addInlineField("Invité par", inviter.getMentionTag());
                        embedBuilder.addInlineField("Lien", ".gg/" + richInvite1.getCode());
                        embedBuilder.addInlineField("Cliques", "" + richInvite1.getUses());
                        embedBuilder.addInlineField("Invitations", "" + linkCountedUses);
                        embedBuilder.addInlineField("Totaux", "" + total);
                        main.api.getChannelById(IDs.Invitation).get().asServerTextChannel().get()
                                .sendMessage(embedBuilder);
                    } else {
                        int linkCountedUses = fileConfiguration.getInt("InvitesCounted." + richInvite1.getCode());
                        int total = 0;
                        for (String invites : fileConfiguration.getStringList("ListeInvites")) {
                            total += fileConfiguration.getInt("InvitesCounted." + invites);
                        }

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setThumbnail(user.getAvatar());
                        embedBuilder.setTitle("\uD83C\uDD95 Retour");
                        Timestamp timestamp2 = new Timestamp(user.getCreationTimestamp().toEpochMilli());
                        Date date2 = new Date(timestamp2.getTime());
                        embedBuilder.setDescription("Bon retour à toi " + user.getMentionTag() + "  "
                                + user.getDiscriminatedName() + "\nSur Discord depuis le : **__"
                                + StatsTimer.DateFormated(date2) + "__**\n\n");
                        embedBuilder.addInlineField("Invité par", inviter.getMentionTag());
                        embedBuilder.addInlineField("Lien", ".gg/" + richInvite1.getCode());
                        embedBuilder.addInlineField("Cliques", "" + richInvite1.getUses());
                        embedBuilder.addInlineField("Invitations", "" + linkCountedUses);
                        embedBuilder.addInlineField("Totaux", "" + total);
                        main.api.getChannelById(IDs.Invitation).get().asServerTextChannel().get()
                                .sendMessage(embedBuilder);
                    }

                    Timestamp timestampUserJoinDiscord = new Timestamp(user.getCreationTimestamp().toEpochMilli());
                    Date dateUserJoinDiscord = new Date(timestampUserJoinDiscord.getTime());

                    Date dateNow = new Date();
                    int monthToCheck = 3;
                    dateNow.setMonth(dateNow.getMonth() - monthToCheck);

                    Date dateNow2 = new Date();
                    int monthToCheck2 = 9;
                    dateNow2.setMonth(dateNow2.getMonth() - monthToCheck2);

                    if (dateUserJoinDiscord.after(dateNow)) {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setThumbnail(user.getAvatar());
                        embedBuilder.setTitle("🚨 Refus automatique");
                        embedBuilder.addInlineField("Utilisateur", user.getMentionTag());
                        embedBuilder.addInlineField("ID", user.getIdAsString());
                        embedBuilder.addInlineField("Raison", "Compte crée il y a moins de " + monthToCheck + " mois");
                        embedBuilder.addInlineField("Date du compte",
                                "<t:" + timestampUserJoinDiscord.toInstant().getEpochSecond() + ":R>");

                        main.api.getServerById(IDs.serverID).get().kickUser(user,
                                "Auto kick " + monthToCheck + " mois");

                        user.sendMessage(embedBuilder);
                        main.api.getServerTextChannelById(IDs.Sanctions).get().sendMessage(embedBuilder);
                        main.api.getServerTextChannelById(IDs.AutoMod).get().sendMessage(embedBuilder);
                    } else if (dateUserJoinDiscord.after(dateNow2)) {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setThumbnail(user.getAvatar());
                        embedBuilder.setTitle("🚨 Compte récent");
                        embedBuilder.addInlineField("Utilisateur", user.getMentionTag());
                        embedBuilder.addInlineField("ID", user.getIdAsString());
                        embedBuilder.addInlineField("Raison", "Compte crée il y a moins de " + monthToCheck2 + " mois");
                        embedBuilder.addInlineField("Date du compte",
                                "<t:" + timestampUserJoinDiscord.toInstant().getEpochSecond() + ":R>");

                        main.api.getServerTextChannelById(IDs.AutoMod).get().sendMessage(embedBuilder);
                    }
                    return;
                }
            }
        }
        before = main.api.getServerById(IDs.serverID).get().getInvites().get();
        return;
    }
}