package ghub.fr.commands;

import org.javacord.api.entity.channel.ChannelType;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.util.concurrent.ExecutionException;

public class addReaction {
    public static void onAddReaction() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("addreaction")) {
                InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                        .createImmediateResponder();
                interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E");
                interactionImmediateResponseBuilder.respond();
                try {
                    addReactionEveryWhere();
                } catch (Exception e) {
                }
            }
        });
    }

    private static void addReactionEveryWhere() throws ExecutionException, InterruptedException {
        for (ServerChannel channel : main.api.getServerById(IDs.serverID).get().getChannels()) {
            if (channel.getType().equals(ChannelType.SERVER_PUBLIC_THREAD)) {
                for (Message message : channel.asServerThreadChannel().get().getMessages(5).get()) {
                    for (Reaction reaction : message.getReactions()) {
                        message.addReaction(reaction.getEmoji());
                    }
                }
            } else if (channel.getType().equals(ChannelType.SERVER_TEXT_CHANNEL)) {
                for (Message message : channel.asServerTextChannel().get().getMessages(5).get()) {
                    for (Reaction reaction : message.getReactions()) {
                        message.addReaction(reaction.getEmoji());
                    }
                }
            }
        }
    }
}
