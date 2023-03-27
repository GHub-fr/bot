package ghub.fr.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

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

                    String raison = slashCommandInteraction.getOptionStringValueByIndex(2).get();
                    String Message = user.getMentionTag() + "Vous avez été **rendu muet pour __" + muteMin
                            + " minute(s) (" + date + "__**\nRaison : **__"
                            + raison + "__**\nPar : " + sender.getMentionTag() + " ( " + sender.getDiscriminatedName()
                            + " )";

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setThumbnail(user.getAvatar());
                    embedBuilder.setTitle("🔇 Mute");
                    embedBuilder.addInlineField("Rendu muet jusqu'au", "" + date);
                    embedBuilder.addInlineField("Par", sender.getMentionTag());
                    embedBuilder.addInlineField("Raison", Message);

                    user.sendMessage(Message).get();

                    main.api.getServerTextChannelById(IDs.LogsCmd).get().sendMessage(embedBuilder);

                    user.timeout(event.getInteraction().getServer().get(), date.toInstant(), raison);

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