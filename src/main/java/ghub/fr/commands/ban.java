package ghub.fr.commands;

import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class ban {
    public static void banUser() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("ban")) {
                try {
                    User sender = slashCommandInteraction.getUser();
                    User user = slashCommandInteraction.getOptionUserValueByIndex(0).get();
                    int days = (int) Math.round(slashCommandInteraction.getOptionDecimalValueByIndex(1).get());

                    if (days >= 1 && days <= 7) {
                        InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                                .createImmediateResponder();
                        interactionImmediateResponseBuilder.setContent(IDs.EmojiGreenCrossMark);
                        interactionImmediateResponseBuilder.respond();
                    } else {
                        InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                                .createImmediateResponder();
                        interactionImmediateResponseBuilder.setContent(IDs.EmojiSpeechBalloon + " Erreur sur la durée");
                        interactionImmediateResponseBuilder.respond();
                        return;
                    }

                    String raison = slashCommandInteraction.getOptionStringValueByIndex(2).get();
                    String Message = user.getMentionTag() + "Vous avez été **banni du serveur**\nRaison : **__"
                            + raison + "__**\nPar : " + sender.getMentionTag() + " ( " + sender.getDiscriminatedName()
                            + " )";

                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(Message).get();

                    // Vérifier si le demandeur du ban à un rôle supérieur au bani (pas égal,
                    // obligatoir >)

                    user.sendMessage(Message).get();
                    slashCommandInteraction.getServer().get().banUser(user, days, raison);
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
