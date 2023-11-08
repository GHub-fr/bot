package ghub.fr.listener;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import ghub.fr.api.messages;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.util.concurrent.ExecutionException;

public class bonusRoles {
    public static void Member(int i, User user) throws ExecutionException, InterruptedException {
        if (!user.isBot()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            if (i >= 1000) {
                Role role = IDs.Rolemsg1000;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "1000 messages"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                Role role2 = IDs.Rolemsg200;
                if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role2)) {
                    user.removeRole(role2).get();
                }
                return;
            }
            if (i >= 200) {
                Role role = IDs.Rolemsg200;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "200 messages"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                Role role2 = IDs.Rolemsg50;
                if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role2)) {
                    user.removeRole(role2);
                }
                return;
            }
            if (i >= 50) {
                Role role = IDs.Rolemsg50;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "50 messages"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                Role role2 = IDs.Rolemsg10;
                if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role2)) {
                    user.removeRole(role2);
                }
                return;
            }
            if (i >= 10) {
                Role role = IDs.Rolemsg10;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "10 messages"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                return;
            }
        }
    }

    public static void InviteRole(int i, User user) throws ExecutionException, InterruptedException {
        if (!user.isBot()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            if (i >= 100) {
                Role role = IDs.Roleinvites100;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "100 invitations"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                Role role2 = IDs.Roleinvites50;
                if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role2)) {
                    user.removeRole(role2);
                }
                return;
            }
            if (i >= 50) {
                Role role = IDs.Roleinvites50;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "50 invitations"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                Role role2 = IDs.Roleinvites30;
                if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role2)) {
                    user.removeRole(role2);
                }
                return;
            }
            if (i >= 30) {
                Role role = IDs.Roleinvites30;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "30 invitations"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                Role role2 = IDs.Roleinvites10;
                if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role2)) {
                    user.removeRole(role2);
                }
                return;
            }
            if (i >= 10) {
                Role role = IDs.Roleinvites10;
                if (!user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(role)) {
                    user.addRole(role);
                    user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain + role.getName()).get();
                    embedBuilder.setDescription(messages.LevelUP(user, role, "10 invitations"));
                    main.api.getChannelById(IDs.Boost).get().asServerTextChannel().get().sendMessage(embedBuilder);
                }
                return;
            }
        }
    }
}