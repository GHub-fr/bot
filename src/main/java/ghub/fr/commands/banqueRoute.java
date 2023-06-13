package ghub.fr.commands;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.listener.casinoBanqueroute;
import ghub.fr.main.main;

public class banqueRoute {
    public static void banquerouteCMD() {
        main.api.addSlashCommandCreateListener(event -> {
            try {
                SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
                if (slashCommandInteraction.getCommandName().equalsIgnoreCase("banqueroute")) {
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                            .createImmediateResponder();
                    int tryAmount = (int) Math.round(slashCommandInteraction.getOptionDecimalValueByIndex(0).get());
                    interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E Merci de patienter");
                    interactionImmediateResponseBuilder.respond();

                    casinoBanqueroute.doBanqueRoute(slashCommandInteraction.getUser(),
                            slashCommandInteraction.getChannel().get().asServerTextChannel().get(), tryAmount);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
