package ghub.fr.api;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

import java.util.concurrent.ExecutionException;

public class messages {
    public static String message() throws ExecutionException, InterruptedException {
        return "";
    }

    public static String pub() {
        return "🚀 GHUB . fr / discord\n\n🎮   Jeux-vidéos rôles et forum pour rencontrer des joueurs et parler de tes jeux favoris\n    ⚔️   Hub de serveurs Minecraft      ~      play.ghub.fr 🔱 (Premium 1.9 -> dernières versions)\n                 RPG, PvP Box, ... (En développement)\n                 SkyBlock Ouvert et public\n                 Survie Survie type HolyCube (Recrutement via ticket) Saison 1 : recrutement\n                 Anarchie Ultra HC (Anarchie avec une seul vie / 24h) Ouverture lorsqu'assez de joueurs seront présents\n    💀   Albion Online ~ Guilde HCE\n    🚙   Rocket League ~ Chill Tournois libre & événements\n\n👥   Hub communautaire et animations\n    📜   Zone & forum pour vos pubs\n    🎰   Casino (Paries & jeux [En développement ouvert !], activités, WatchTogether, ...)\n    🖥️   Cours d'informatique Débutant du hardware au développement de plugin, bot et site Web basiques ! (Bientôt)\n    💬   Bot & serveurs Minecraft codés main ~ Ouverture en cours ! \n\nRejoins-nous 🌀 https://discord.gg/rF25kjuv4v  ~  https://ghub.fr/  ~  https://ghub.fr/storage/img/gif.gif\nRecrutement ✅ (Je recrute toutes personnes un minimum active, même sans connaissances)\nLe projet étant jeune, je suis seul à coder et le maintenir, je cherche donc les premiers fondateurs de cette future communauté";
    }

    public static String JoinMessage(User user) throws ExecutionException, InterruptedException {
        return "\uD83C\uDD95 Bienvenue " + user.getMentionTag();
    }

    public static String BotOnline() throws ExecutionException, InterruptedException {
        return "Le bot est **en ligne** ✅";
    }

    public static String BotOffline() throws ExecutionException, InterruptedException {
        return "Le bot est **hors ligne** ❌";
    }

    public static String BotReconnect() throws ExecutionException, InterruptedException {
        return "Le bot est **reconnecté** ⚡";
    }

    public static String LeaveMessage(User user) throws ExecutionException, InterruptedException {
        return "\uD83D\uDEAB Aurevoir " + user.getMentionTag();
    }

    public static String LevelUP(User user, Role Role, String messageTotal)
            throws ExecutionException, InterruptedException {
        return "\uD83C\uDF87 Bravo " + user.getMentionTag() + " pour tes **" + messageTotal + " ** !\n"
                + "\uD83C\uDF81 Voilà ton nouveau rôle : " + Role.getMentionTag();
    }

    public static void sendmessageOnline() throws ExecutionException, InterruptedException {
        main.api.getServerTextChannelById(IDs.Annonces).get().sendMessage(BotOnline());
    }

    public static void sendmessageOffline() throws ExecutionException, InterruptedException {
        main.api.getServerTextChannelById(IDs.Annonces).get().sendMessage(BotOffline());
    }
}