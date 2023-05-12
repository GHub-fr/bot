package ghub.fr.listener;

import java.io.IOException;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class casinoDailyClicker {
    public static void onCasinoDailyClicker() {
        main.api.addReactionAddListener(event -> {
            if (event.getChannel().getIdAsString().equals(IDs.CasinoDailyClick)
                    && !event.getUser().get().isBot()) {
                try {
                    String mentionTagEmoji = event.getEmoji().getMentionTag();
                    event.removeReaction();

                    if (mentionTagEmoji.equals(IDs.EmojiProfil)) // profil
                    {
                        main.api.getServerTextChannelById(IDs.CasinoTextuelResultat).get()
                                .sendMessage(casinoProfil.profil(event.getUser().get()));
                    }

                    else if (mentionTagEmoji.equals(IDs.EmojiMouseThreeButton)) // dailyClick
                    {
                        if (casinoProfil.CanPlay(event.getUser().get(), 24 * 60, "DayliClaim") || event.getUser().get()
                                .getRoles(event.getServer().get()).contains(IDs.RoleCasinoBypass)) {
                            int passifs = casinoProfil.GetPlayPassif(event.getUser().get());
                            casinoProfil.clickDoPassif(event.getUser().get());
                            casinoProfil.UpdatePlayDate(event.getUser().get(), "DayliClaim");
                            int passifGains = casinoProfil.GetPlayPassif(event.getUser().get()) - passifs;
                            main.api.getServerTextChannelById(IDs.CasinoTextuelResultat).get().sendMessage(
                                    casinoProfil.gainGold(event.getUser().get(),
                                            250, "Clique journalier",
                                            passifs, passifGains, "Passifs",
                                            event.getChannel().asServerTextChannel().get()));
                        } else {
                            event.getUser().get().sendMessage("Vous avez **__déjà joué dans les dernières 24H__** au "
                                    + event.getServerTextChannel().get().getMentionTag());
                        }
                    }

                    else if (mentionTagEmoji.equals(IDs.EmojiCalendar)) // passif
                    {
                        int playPassifAmount = casinoProfil.GetPlayPassif(event.getUser().get());
                        if (playPassifAmount >= 1) {
                            casinoProfil.UpdatePlayPassif(event.getUser().get(), -playPassifAmount);
                            main.api.getServerTextChannelById(IDs.CasinoTextuelResultat).get()
                                    .sendMessage(casinoProfil.gainGold(event.getUser().get(), playPassifAmount,
                                            "Gains passifs",
                                            event.getChannel().asServerTextChannel().get()));
                        } else {
                            event.getUser().get().sendMessage("Vous n'avez **__pas de gains passifs à récupérer__** au "
                                    + event.getServerTextChannel().get().getMentionTag());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
