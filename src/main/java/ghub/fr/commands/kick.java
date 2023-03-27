package ghub.fr.commands;

import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class kick {
    public static void onKickUser() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("kick")) {
                try {
                    User sender = slashCommandInteraction.getUser();
                    User user = slashCommandInteraction.getOptionUserValueByIndex(0).get();
                    String raison = slashCommandInteraction.getOptionStringValueByIndex(1).get();
                    String Message = user.getMentionTag() + "Vous avez été **expulsé du serveur**\nRaison : **__"
                            + raison + "__**\nPar : " + sender.getMentionTag() + " ( " + sender.getDiscriminatedName()
                            + " )";
                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(Message).get();
                    user.sendMessage(Message).get();
                    slashCommandInteraction.getServer().get().kickUser(user, raison);
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                            .createImmediateResponder();
                    interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E");
                    interactionImmediateResponseBuilder.respond();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}