package ghub.fr.commands;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.server.invite.Invite;
import org.javacord.api.entity.server.invite.InviteBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.main;

public class createInvite {
    public static void onCreateInvite() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("createInvite")) {
                InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                        .createImmediateResponder();
                interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E");
                interactionImmediateResponseBuilder.respond();
                try {
                    String id = slashCommandInteraction.getOptionStringValueByIndex(0).get();
                    ServerTextChannel serverTextChannel = main.api.getServerTextChannelById(id).get();
                    InviteBuilder inviteBuilder = new InviteBuilder(serverTextChannel);
                    inviteBuilder.setMaxUses(0);
                    inviteBuilder.setMaxAgeInSeconds(0);
                    Invite invite = inviteBuilder.create().join();
                    slashCommandInteraction.getUser().sendMessage("Invitation crée par "
                            + main.api.getYourself().getMentionTag() + " : **__" + invite.getUrl() + "__**");
                } catch (Exception e) {
                }
            }
        });
    }
}
