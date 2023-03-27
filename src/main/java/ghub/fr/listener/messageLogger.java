package ghub.fr.listener;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class messageLogger {

    public static EmbedBuilder embed(String description) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("📠 Logs");
        embedBuilder.setDescription(description);
        return embedBuilder;
    }

    public static void onMessageLogger() {
        main.api.addMessageCreateListener(event -> {
            if (event.getMessageAuthor().asUser().get().isBot()) {
                return;
            } else {
                main.api.getServerTextChannelById(IDs.LogsMessage).get()
                        .sendMessage(embed("__**Message crée**__ : "
                                + event.getMessageAuthor().asUser().get().getMentionTag()
                                + ", __**ID**__ : "
                                + event.getMessageAuthor().getIdAsString()

                                + "\n"
                                + event.getMessage().getChannel().asServerTextChannel().get().getMentionTag()
                                + ", __**message ID**__ : " + event.getMessageId() + " , __**message link**__ : "
                                + event.getMessageLink()

                                + "\n\n"
                                + event.getMessageContent()));
            }
        });

        main.api.addMessageDeleteListener(event -> {
            if (event.getMessageAuthor().isPresent() && event.getMessageAuthor().get().asUser().isPresent()
                    && event.getMessageAuthor().get().asUser().get().isBot()) {
                return;
            } else {
                main.api.getServerTextChannelById(IDs.LogsMessage).get()
                        .sendMessage(embed("__**Message supprimé**__ : "
                                + event.getMessageAuthor().get().asUser().get().getMentionTag()
                                + ", __**ID**__ : " + event.getMessageAuthor().get().asUser().get().getIdAsString()

                                + "\n"
                                + event.getMessage().get().getChannel().asServerTextChannel().get().getMentionTag()
                                + ", __**message ID**__ : " + event.getMessageId() + " , __**message link**__ : "
                                + event.getMessageLink().get()

                                + "\n\n"
                                + event.getMessageContent().get()));
            }
        });

        main.api.addMessageEditListener(event -> {
            if (event.getMessageAuthor().get().asUser().get().isBot()) {
                return;
            } else {
                main.api.getServerTextChannelById(IDs.LogsMessage).get()
                        .sendMessage(embed("__**Message modifié**__ : "
                                + event.getMessageAuthor().get().asUser().get().getMentionTag()
                                + ", __**ID**__ : " + event.getMessageAuthor().get().getIdAsString()

                                + "\n"
                                + event.getMessage().get().getChannel().asServerTextChannel().get().getMentionTag()
                                + ", __**message ID**__ : " + event.getMessageId() + " , __**message link**__ : "
                                + event.getMessageLink().get()

                                + "\n\n" + event.getOldContent().get()

                                + "\n\n**__Nouveau Message__** :\n\n"
                                + event.getNewContent()));
            }
        });
    }
}
