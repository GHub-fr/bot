package ghub.fr.main;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class main {
    public static DiscordApi api = null;

    public static void SetDiscordApi(token.account account) throws IOException {
        DiscordApiBuilder apiBuilder = new DiscordApiBuilder();
        apiBuilder.setToken(token.getToken(account));
        apiBuilder.setAllIntents();
        api = apiBuilder.login().join();
    }

    public static void main(String[] args)
            throws LoginException, ExecutionException, InterruptedException, IOException {
        startBot(token.account.Bot);
    }

    public static void startBot(token.account account) throws ExecutionException, InterruptedException, IOException {
        SetDiscordApi(account);
        events.startEvents();
    }
}