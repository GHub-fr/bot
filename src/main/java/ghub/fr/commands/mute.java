package ghub.fr.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.api.HigherRole;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mute {
    public static void setMute() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("mute")) {
                try {
                    User sender = slashCommandInteraction.getUser();
                    User user = slashCommandInteraction.getOptionUserValueByIndex(0).get();

                    int muteMin = (int) Math.round(slashCommandInteraction.getOptionDecimalValueByIndex(1).get());
                    Date date = new Date();
                    date.setMinutes(date.getMinutes() + muteMin);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                    String raison = slashCommandInteraction.getOptionStringValueByIndex(2).get();

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setThumbnail(user.getAvatar());
                    embedBuilder.setTitle("🔇 Exclusion");
                    embedBuilder.addInlineField("Utilisateur", user.getMentionTag());
                    embedBuilder.addInlineField("ID", user.getIdAsString());
                    embedBuilder.addInlineField("Rendu muet jusqu'au", "" + dateFormat.format(date));
                    embedBuilder.addInlineField("Par", sender.getMentionTag());
                    embedBuilder.addInlineField("Raison", raison);

                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(embedBuilder);

                    if (HigherRole.isRoleHigher(sender, user)) {
                        user.sendMessage(embedBuilder).get();
                        main.api.getServerTextChannelById(IDs.Sanctions).get().sendMessage(embedBuilder).get();
                        user.timeout(event.getInteraction().getServer().get(), date.toInstant(), raison);
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