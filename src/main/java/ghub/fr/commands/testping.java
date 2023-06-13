package ghub.fr.commands;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.api.FileSystem;
import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class testping {
    public static void onTestPing() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("test")
                    || slashCommandInteraction.getCommandName().equalsIgnoreCase("ping")) {
                InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                        .createImmediateResponder();
                interactionImmediateResponseBuilder.setContent("\uD83D\uDC8E");
                interactionImmediateResponseBuilder.respond();
                if (slashCommandInteraction.getUser().isBotOwner()) {
                    try {
                        for (User user : main.api.getServerById(IDs.serverID).get().getMembers()) {
                            if (!user.isBot()) {
                                slashCommandInteraction.getChannel().get()
                                        .sendMessage(user.getDiscriminatedName())
                                        .get();
                                File file = FileSystem.file(user);
                                if (file == null) {
                                    slashCommandInteraction.getChannel().get()
                                            .sendMessage("null")
                                            .get();
                                } else {
                                    FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

                                    slashCommandInteraction.getChannel().get()
                                            .sendMessage("" +
                                                    fileConfiguration.get("Gold"))
                                            .get();
                                }
                            }
                        }

                        slashCommandInteraction.getChannel().get()
                                .sendMessage(
                                        "ghub.fr (Soon?)" + slashCommandInteraction.getChannel().get().getType())
                                .get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}