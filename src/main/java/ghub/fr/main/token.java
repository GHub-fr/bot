package ghub.fr.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class token {
    public enum account {
        Bot, TestBot
    }

    public static File DiscordBotTokens() {
        String FolderName = "/DiscordBot/data/";
        // String FolderName = "C:\\Users\\user\\Desktop";
        String FileName = "tokens.yml";
        File file = new File(FolderName, FileName);
        return file;
    }

    public static String token(account bot) throws IOException {
        if (DiscordBotTokens().exists()) {
            FileConfiguration fileConfiguration = YamlConfiguration
                    .loadConfiguration(DiscordBotTokens());

            return fileConfiguration.getString(bot.toString());
        } else {
            DiscordBotTokens().createNewFile();
        }
        return "";
    }
}