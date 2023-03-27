package ghub.fr.commands;

import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.listener.casinoProfil;
import ghub.fr.main.main;

public class profil {
    public static void onProfil() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("profil")) {
                try {
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                            .createImmediateResponder();
                    interactionImmediateResponseBuilder.addEmbed(casinoProfil.profil(slashCommandInteraction
                            .getOptionUserValueByIndex(0).orElse(slashCommandInteraction.getUser())));
                    interactionImmediateResponseBuilder.respond();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}