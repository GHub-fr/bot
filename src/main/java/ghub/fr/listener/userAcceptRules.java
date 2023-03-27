package ghub.fr.listener;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class userAcceptRules {
    public static void onUserAcceptRules() {
        main.api.addReactionAddListener(event -> {
            if (event.getChannel().getIdAsString().equals(IDs.Regles) && !event.getUser().get().isBot()) {
                User user = event.getUser().get();

                user.addRole(IDs.RoleReglesValides);

                for (Role role : IDs.RolesSplitterList()) {
                    user.addRole(role);
                }

                user.sendMessage(user.getMentionTag() + IDs.MessageRoleGain
                        + IDs.RoleReglesValides.getName() + "\n" + "**__Pensez à prendre vos "
                        + main.api.getServerTextChannelById(IDs.AutresJeux).get().getMentionTag() + "__**, "
                        + main.api.getServerTextChannelById(IDs.Notifications).get().getMentionTag() + ", et votre "
                        + main.api.getServerTextChannelById(IDs.Profil).get().getMentionTag());
            }
        });

        main.api.addReactionRemoveListener(event -> {
            if (event.getChannel().getIdAsString().equals(IDs.Regles) && !event.getUser().get().isBot()) {
                User user = event.getUser().get();
                user.removeRole(IDs.RoleReglesValides);
                user.sendMessage(user.getMentionTag() + IDs.MessageRolePerte
                        + IDs.RoleReglesValides.getName());
            }
        });
    }
}