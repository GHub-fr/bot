package ghub.fr.commands;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class help {
    public static void onHelp() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("help")) {
                try {
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                            .createImmediateResponder();
                    interactionImmediateResponseBuilder
                            .addEmbed(commandes(event.getSlashCommandInteraction().getUser()));
                    interactionImmediateResponseBuilder.respond();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static EmbedBuilder commandes(User user) throws IOException, InterruptedException, ExecutionException {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail(main.api.getYourself().getAvatar());
        embedBuilder.setTitle("📚 Commandes d'" + main.api.getYourself().getName());

        for (SlashCommand slashCommand : main.api.getGlobalSlashCommands().get()) {
            if (slashCommand.getDefaultRequiredPermissions().isEmpty() || (!slashCommand.getDefaultRequiredPermissions()
                    .isEmpty()
                    && user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(IDs.RoleModeration))) {
                embedBuilder.addField(slashCommand.getName(), slashCommand.getDescription());
            }
        }

        return embedBuilder;
    }
}
