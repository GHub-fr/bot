package ghub.fr.commands;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class ip {
    public static void onIP() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("ip")) {
                try {
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                            .createImmediateResponder();
                    interactionImmediateResponseBuilder
                            .addEmbed(commandes());
                    interactionImmediateResponseBuilder.respond();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static EmbedBuilder commandes() throws IOException, InterruptedException, ExecutionException {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail(main.api.getYourself().getAvatar());
        embedBuilder.setTitle("📚 IP/DNS d'" + main.api.getYourself().getName());
        embedBuilder.addField("@", "GHub.fr  ou  play.ghub.fr");

        for (User user : main.api.getServerById(IDs.serverID).get().getMembers()) {
            if (user.getRoles(main.api.getServerById(IDs.serverID).get()).contains(IDs.RoleBotsMC)) {
                embedBuilder.addField(user.getName(), user.getMentionTag());
            }
        }

        return embedBuilder;
    }
}
