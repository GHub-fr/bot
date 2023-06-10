package ghub.fr.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.api.HigherRole;
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
                        interactionImmediateResponseBuilder.setContent(IDs.EmojiShield);
                        interactionImmediateResponseBuilder.respond();
                    } else {
                        InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                                .createImmediateResponder();
                        interactionImmediateResponseBuilder.setContent(IDs.EmojiSpeechBalloon
                                + " Erreur sur la durée (1 à 7 jours de suppression des messages)");
                        interactionImmediateResponseBuilder.respond();
                        return;
                    }

                    String raison = slashCommandInteraction.getOptionStringValueByIndex(2).get();

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setThumbnail(user.getAvatar());
                    embedBuilder.setTitle("🔨 Ban");
                    embedBuilder.addInlineField("Utilisateur", user.getMentionTag());
                    embedBuilder.addInlineField("ID", user.getIdAsString());
                    embedBuilder.addInlineField("Par", sender.getMentionTag());
                    embedBuilder.addInlineField("Raison", raison);

                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(embedBuilder);

                    if (HigherRole.isRoleHigher(sender, user)) {
                        user.sendMessage(embedBuilder);
                        main.api.getServerTextChannelById(IDs.Sanctions).get().sendMessage(embedBuilder);
                        slashCommandInteraction.getServer().get().banUser(user, days, raison).join();
                    }

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
    
    public static void unbanUser() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("unban")) {
                try {
                    User sender = slashCommandInteraction.getUser();
                    User user = slashCommandInteraction.getOptionUserValueByIndex(0).get();

                    String raison = slashCommandInteraction.getOptionStringValueByIndex(1).get();

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setThumbnail(user.getAvatar());
                    embedBuilder.setTitle("🔨 UnBan");
                    embedBuilder.addInlineField("Utilisateur", user.getMentionTag());
                    embedBuilder.addInlineField("ID", user.getIdAsString());
                    embedBuilder.addInlineField("Par", sender.getMentionTag());
                    embedBuilder.addInlineField("Raison", raison);

                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(embedBuilder);

                    if (HigherRole.isRoleHigher(sender, user)) {
                        user.sendMessage(embedBuilder);
                        main.api.getServerTextChannelById(IDs.Sanctions).get().sendMessage(embedBuilder);
                        slashCommandInteraction.getServer().get().unbanUser(user, raison).join();
                    }

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
