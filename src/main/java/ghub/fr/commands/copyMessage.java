package ghub.fr.commands;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.main;

public class copyMessage {
    public static void onCopyMessage() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("copyMessage")) {
                InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                        .createImmediateResponder();
                interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E");
                interactionImmediateResponseBuilder.respond();
                try {
                    String id = slashCommandInteraction.getOptionStringValueByIndex(0).get();
                    String channel = slashCommandInteraction.getOptionStringValueByIndex(1).get();

                    Message message = main.api.getMessageById(id, event.getInteraction().getChannel().get()).get();
                    Channel channel2 = main.api.getChannelById(channel).get();

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setDescription(message.getContent());
                    Message newMessage = channel2.asServerTextChannel().get().sendMessage(embedBuilder).get();

                    // Message newMessage =
                    // channel2.asServerTextChannel().get().sendMessage(message.getContent()).get();

                    for (Reaction reaction : message.getReactions()) {
                        newMessage.addReaction(reaction.getEmoji());
                    }

                } catch (Exception e) {
                }
            }
        });
    }
}
