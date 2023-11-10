package ghub.fr.listener;

import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class reactionRole {
    public static Role RoleFromEmojis(String ChannelID, String EmojiMentionTag) {
        if (ChannelID.equals(IDs.Notifications)) {
            switch (EmojiMentionTag) {
                case IDs.EmojiArrowsClockwise:
                    return IDs.RoleTwitter;
                case IDs.EmojiMovieCamera:
                    return IDs.RoleVideoLive;
                case IDs.EmojiFramePhoto:
                    return IDs.RoleInstagram;
                case IDs.EmojiDesktop:
                    return IDs.RoleMiseAJour;
                case IDs.EmojiLoudSpeaker:
                    return IDs.RoleAnnonces;
                case IDs.EmojiCalendar:
                    return IDs.RoleEvents;
                case IDs.EmojiCrown:
                    return IDs.RoleTournois;
            }
        } else if (ChannelID.equals(IDs.Profil)) {
            switch (EmojiMentionTag) {
                case IDs.EmojiMaleSign:
                    return IDs.RoleHomme;
                case IDs.EmojiFemaleSign:
                    return IDs.RoleFemme;
                case IDs.EmojiUnderage:
                    return IDs.RoleMineur;
                case IDs.EmojiOlderMan:
                    return IDs.RoleMajeur;
                case IDs.EmojiComputer:
                    return IDs.RolePC;
                case IDs.EmojiMobilePhone:
                    return IDs.RoleTelephone;
                case IDs.EmojiXBox:
                    return IDs.RoleXBox;
                case IDs.EmojiPS:
                    return IDs.RolePS;
                case IDs.EmojiSwitch:
                    return IDs.RoleSwitch;
            }
        } else if (ChannelID.equals(IDs.AutresJeux)) {
            switch (EmojiMentionTag) {
                case IDs.EmojiRocketLeague:
                    return IDs.RoleRocketLeague;
                case IDs.EmojiSuperCell:
                    return IDs.RoleSuperCell;
                case IDs.EmojiGenshinImpact:
                    return IDs.RoleGenshinImpact;
                case IDs.EmojiGTAV:
                    return IDs.RoleGTAV;
                case IDs.EmojiFortnite:
                    return IDs.RoleFortnite;
                case IDs.EmojiCSGO:
                    return IDs.RoleCSGO;
                case IDs.EmojiLeagueOfLegends:
                    return IDs.RoleLeagueOfLegends;
                case IDs.EmojiValorant:
                    return IDs.RoleValorant;
                case IDs.EmojiAlbionOnline:
                    return IDs.RoleAlbionOnline;
                case IDs.EmojiShopTitans:
                    return IDs.RoleShopTitans;
            }
        }
        return null;
    }

    public static void startListener() {
        main.api.addReactionAddListener(event -> {
            if (event.getChannel().getType().equals(ChannelType.SERVER_TEXT_CHANNEL)
                    && event.getServerTextChannel().get().getCategory().get().equals(IDs.CategoryRoles)
                    && !event.getUser().get().isBot()) {
                if (RoleFromEmojis(event.getServerTextChannel().get().getIdAsString(),
                        event.getEmoji().getMentionTag()) != null) {
                    Role role = RoleFromEmojis(event.getServerTextChannel().get().getIdAsString(),
                            event.getEmoji().getMentionTag());
                    User user = event.getUser().get();
                    user.addRole(role);
                    user.sendMessage(IDs.MessageRoleGain + role.getName()).join();
                }
            }
        });

        main.api.addReactionRemoveListener(event -> {
            if (event.getChannel().getType().equals(ChannelType.SERVER_TEXT_CHANNEL)
                    && event.getServerTextChannel().get().getCategory().get().getIdAsString().equals(IDs.CategoryRoles)
                    && !event.getUser().get().isBot()) {
                if (RoleFromEmojis(event.getServerTextChannel().get().getIdAsString(),
                        event.getEmoji().getMentionTag()) != null) {
                    Role role = RoleFromEmojis(event.getServerTextChannel().get().getIdAsString(),
                            event.getEmoji().getMentionTag());
                    User user = event.getUser().get();
                    user.removeRole(role);
                    user.sendMessage(IDs.MessageRolePerte + role.getName()).join();
                }
            }
        });
    }
}