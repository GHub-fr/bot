package ghub.fr.commands;

import org.javacord.api.entity.message.MessageDecoration;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.UserContextMenuInteraction;
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

    public static void onProfilUserContextMenu() {
        main.api.addUserContextMenuCommandListener(event -> {
            UserContextMenuInteraction userContextMenuInteraction = event.getUserContextMenuInteraction();
            if (userContextMenuInteraction.getCommandName().equalsIgnoreCase("profil")) {
                try {
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = userContextMenuInteraction
                            .createImmediateResponder();
                    if (userContextMenuInteraction.getChannel().get().canWrite(userContextMenuInteraction.getUser())) {
                        interactionImmediateResponseBuilder
                                .addEmbed(casinoProfil.profil(userContextMenuInteraction.getTarget()));
                    } else {
                        interactionImmediateResponseBuilder.setFlags(MessageFlag.EPHEMERAL);
                        interactionImmediateResponseBuilder.setFlags(MessageFlag.URGENT);
                        interactionImmediateResponseBuilder.append("Vous ne pouvez pas envoyer de message ici...",
                                MessageDecoration.BOLD);
                    }
                    interactionImmediateResponseBuilder.respond();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}