package ghub.fr.listener;

import java.io.IOException;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class boutique {
    public static void onBoutique() {
        main.api.addReactionAddListener(event -> {
            if (event.getChannel().getIdAsString().equals(IDs.CasinoBoutique)
                    && !event.getUser().get().isBot()) {
                try {
                    String mentionTagEmoji = event.getEmoji().getMentionTag();
                    event.removeReaction();
                    if (mentionTagEmoji.equals(IDs.EmojiCrown))// VIP
                    {
                        int goldAmount = casinoProfil.GetGold(event.getUser().get());
                        if (goldAmount >= IDs.prixVIP) {
                            if (event.getUser().get().getRoles(main.api.getServerById(IDs.serverID).get())
                                    .contains(IDs.RoleVIP)) {
                                event.getUser().get().sendMessage("Vous êtes déjà " + IDs.RoleVIP.getName());
                            } else {
                                main.api.getServerTextChannelById(IDs.Boost).get()
                                        .sendMessage(casinoProfil.gainGold(event.getUser().get(), -IDs.prixVIP,
                                                "Achat du VIP",
                                                main.api.getServerTextChannelById(IDs.CasinoBoutique).get()));
                                event.getUser().get().addRole(IDs.RoleVIP);
                                event.getUser().get().sendMessage(event.getUser().get().getMentionTag()
                                        + " est désormais " + IDs.RoleVIP.getName());
                            }
                        } else {
                            casinoProfil.msgPasAssezArgent(event.getUser().get(), IDs.prixVIP);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}