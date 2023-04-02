package ghub.fr.listener;

import java.io.IOException;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class casinoCoinFlip {
    public static void startCoinFlip() {
        main.api.addReactionAddListener(event -> {
            try {
                if (event.getChannel().getIdAsString().equals(IDs.CasinoCoinFlip) && !event.getUser().get().isBot()) {

                    String mentionTagEmoji = event.getEmoji().getMentionTag();
                    event.removeReaction();

                    if (mentionTagEmoji.equals(IDs.EmojiCoin))// CoinFlip
                    {
                        doCoinFlip(event.getUser().get(), event.getServerTextChannel().get(), IDs.prixCoinFlip);
                    }
                }
            } catch (Exception e) {
            }
        });
    }

    public static int doCoinFlip(User user, ServerTextChannel serverTextChannel, int prix) throws IOException {
        int goldAmount = casinoProfil.GetGold(user);
        if (goldAmount >= prix) {
            double rng = Math.random();
            if (rng > 0.5) {
                main.api.getServerTextChannelById(IDs.TextuelGeneral).get()
                        .sendMessage(casinoProfil.gainGold(user, -prix,
                                "Coin flip perdu",
                                serverTextChannel));
                return 0;
            } else {
                main.api.getServerTextChannelById(IDs.TextuelGeneral).get()
                        .sendMessage(casinoProfil.gainGold(user, prix,
                                "Coin flip gagné",
                                serverTextChannel));
                return 1;
            }
        } else {
            casinoProfil.msgPasAssezArgent(user, prix);
            return 2;
        }
    }

    public static void coinFlipCmd() {
        main.api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("coinflip")) {
                try {
                    InteractionImmediateResponseBuilder interactionImmediateResponseBuilder = slashCommandInteraction
                            .createImmediateResponder();
                    int prix = (int) Math
                            .round(slashCommandInteraction.getOptionDecimalValueByIndex(0)
                                    .orElse(0.0));
                    if (prix < 0) {
                        prix = 0;
                    }

                    int coinflipRes = doCoinFlip(event.getSlashCommandInteraction().getUser(),
                            event.getSlashCommandInteraction().getChannel().get().asServerTextChannel().get(), prix);

                    String str = "";
                    if (coinflipRes == 0) {
                        str = "**__Perdu__**";
                    } else if (coinflipRes == 1) {
                        str = "**__Gagné !__**";
                    } else {
                        str = "**__Erreur, pas assez d'argent__**";
                    }

                    interactionImmediateResponseBuilder
                            .setContent(event.getSlashCommandInteraction().getUser().getMentionTag()
                                    + " coin flip lancé : "
                                    + main.api.getServerTextChannelById(IDs.TextuelGeneral).get().getMentionTag()
                                    + ", " + str);
                    interactionImmediateResponseBuilder.respond();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}