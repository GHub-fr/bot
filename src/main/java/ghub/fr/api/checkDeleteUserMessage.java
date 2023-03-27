package ghub.fr.api;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class checkDeleteUserMessage {
    public static void deleteAllMessageFromUser(String reason, User whoRun, User Leaved)
            throws ExecutionException, InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10);
                List<ServerTextChannel> channels = main.api.getServerById(IDs.serverID).get().getTextChannels();
                for (ServerTextChannel serverTextChannel : channels) {
                    for (Message message : serverTextChannel.getMessagesAsStream().toList()) {
                        Thread.sleep(100);
                        if (message.getAuthor().getIdAsString().equals(Leaved.getIdAsString())) {
                            ServerTextChannel textChannel = main.api.getChannelById(IDs.LogsCmd).get()
                                    .asServerTextChannel().get();
                            String name = message.getAuthor().getDiscriminatedName();
                            String messageText = message.getContent();
                            textChannel.sendMessage("**Suppression " + reason + "** : " + name + " par __"
                                    + whoRun.getMentionTag() + "__\n||" + messageText + "||");
                            message.delete();
                        }
                    }
                }
                Thread.currentThread().stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void deleteAllMessageFromChannel(ServerTextChannel serverTextChannel, String reason, User whoRun)
            throws ExecutionException, InterruptedException {
        Thread thread = new Thread(() -> {
            main.api.getChannelById(IDs.LogsCmd).get().asServerTextChannel().get()
                    .sendMessage(reason + " : " + serverTextChannel.getMentionTag() + " par " + whoRun.getMentionTag());
            for (Message message : serverTextChannel.getMessagesAsStream().toList()) {
                Thread.interrupted();
                message.delete();
            }
            Thread.currentThread().stop();
        });
        thread.start();
    }
}