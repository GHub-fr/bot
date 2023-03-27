package ghub.fr.listener;

import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.main;

public class createButton {
    public static void onCreateButton() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("createButton")) {
                InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                        .createImmediateResponder();
                interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E");
                interactionImmediateResponseBuilder.respond();

                Channel channel = slashCommandInteraction.getOptionChannelValueByIndex(0).get();
                String id = slashCommandInteraction.getOptionStringValueByIndex(1).get();
                String text = slashCommandInteraction.getOptionStringValueByIndex(2).get();

                new MessageBuilder()
                        .addComponents(
                                ActionRow.of(Button.secondary(id, text)))
                        .send(channel.asServerTextChannel().get());
                try {
                } catch (Error e) {

                }
            }
        });
    }
}
