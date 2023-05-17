package ghub.fr.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
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

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setThumbnail(user.getAvatar());
                    embedBuilder.setTitle("🚪 Expulsion");
                    embedBuilder.addInlineField("Utilisateur", user.getMentionTag());
                    embedBuilder.addInlineField("ID", user.getIdAsString());
                    embedBuilder.addInlineField("Par", sender.getMentionTag());
                    embedBuilder.addInlineField("Raison", raison);

                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(embedBuilder).get();

                    // Vérifier si le demandeur du ban à un rôle supérieur au bani (pas égal,
                    // obligatoir >)

                    user.sendMessage(embedBuilder).get();
                    main.api.getServerTextChannelById(IDs.Sanctions).get().sendMessage(embedBuilder).get();

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