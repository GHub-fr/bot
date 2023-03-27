package ghub.fr.commands;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.api.checkDeleteUserMessage;
import ghub.fr.main.main;

public class EmptyChannel {
    public static void onEmptyChannel() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("emptychannel")) {
                try {
                    checkDeleteUserMessage.deleteAllMessageFromChannel(
                            event.getInteraction().getChannel().get().asServerTextChannel().get(), "Commande manuel",
                            event.getInteraction().getUser());
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