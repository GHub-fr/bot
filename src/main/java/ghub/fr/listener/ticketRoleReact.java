package ghub.fr.listener;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ticketRoleReact {
    public static void TicketEvent() {
        main.api.addReactionAddListener(event -> {
            try {
                if (event.getChannel().getIdAsString().equals(IDs.Ticket)) {
                    if (event.getEmoji().getMentionTag().equals(IDs.EmojiTicket) && !event.getUser().get().isBot()) {
                        event.removeReaction();
                        for (Channel channel : event.getServer().get().getChannels()) {
                            if (channel.getType().equals(ChannelType.SERVER_TEXT_CHANNEL)) {
                                if (channel.asServerTextChannel().get().getName()
                                        .equals(IDs.EmojiTicket + event.getUserIdAsString())) {
                                    event.getUser().get().sendMessage("Vous avez **__déjà un ticket ouvert__** : "
                                            + channel.asServerTextChannel().get().getMentionTag());
                                    return;
                                }
                            }
                        }
                        ServerTextChannelBuilder serverTextChannelBuilder = new ServerTextChannelBuilder(
                                event.getServer().get());
                        serverTextChannelBuilder.setName("\uD83C\uDFAB" + event.getUserIdAsString());
                        Date date = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        serverTextChannelBuilder.setTopic("\uD83C\uDFAB par : " + event.getUser().get().getMentionTag()
                                + ", le " + dateFormat.format(date));
                        serverTextChannelBuilder.setCategory(IDs.CategoryTicket);
                        Permissions permissions = new PermissionsBuilder().setAllowed(PermissionType.VIEW_CHANNEL)
                                .setAllowed(PermissionType.READ_MESSAGE_HISTORY)
                                .setAllowed(PermissionType.SEND_MESSAGES).build();
                        Permissions permissionsFonda = new PermissionsBuilder().setAllowed(PermissionType.VIEW_CHANNEL)
                                .setAllowed(PermissionType.READ_MESSAGE_HISTORY)
                                .setAllowed(PermissionType.SEND_MESSAGES).setAllowed(PermissionType.MANAGE_CHANNELS)
                                .build();
                        Permissions permissionsFalse = new PermissionsBuilder().setAllDenied().build();
                        Permissions permissionsTrue = new PermissionsBuilder().setAllAllowed().build();
                        serverTextChannelBuilder.addPermissionOverwrite(IDs.RoleEveryone, permissionsFalse);// @all
                        serverTextChannelBuilder.addPermissionOverwrite(event.getUser().get(), permissions);// user
                        serverTextChannelBuilder.addPermissionOverwrite(IDs.RoleAssistance, permissions);// assistance
                        serverTextChannelBuilder.addPermissionOverwrite(IDs.RoleDirection, permissionsFonda);// direction
                                                                                                             // (manage
                                                                                                             // chan)
                        serverTextChannelBuilder.addPermissionOverwrite(IDs.RoleFondateur, permissionsTrue);// fonda
                        ServerTextChannel serverTextChannel = serverTextChannelBuilder.create().join();
                        serverTextChannel.sendMessage("L'" + IDs.RoleAssistance.getMentionTag() + " arrive bientôt\n\n"
                                + event.getUser().get().getMentionTag()
                                + " Pour toutes demandes, **questions**, **recrutements**, **__suggestions__**, ou tout **__bugs__**,\n"
                                + ":scroll: Merci de bien vouloir :warning: **__préciser la raison__** :warning: en premier message\n**__Merci d'envoyer le message avant l'arrivé de l'"+IDs.RoleAssistance.getMentionTag()+"__**\n"
                                + "Merci de consulter les "
                                + main.api.getServerTextChannelById(IDs.Regles).get().getMentionTag()
                                + " et notes sur les "
                                + main.api.getServerTextChannelById(IDs.Ticket).get().getMentionTag()
                                + " avant");
                        event.getUser().get().sendMessage(event.getUser().get().getMentionTag() +
                                " Votre " + main.api.getServerTextChannelById(IDs.Ticket).get().getMentionTag()
                                + " a été crée : " + serverTextChannel.getMentionTag());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
