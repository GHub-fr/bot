package ghub.fr.main;

import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.permission.Role;

import java.util.ArrayList;

public class IDs {
    public static final String MessageRoleGain = " Vous avez **__reçu le rôle__** : ";
    public static final String MessageRolePerte = " Vous avez **__perdu le rôle__** : ";
    //
    public static final ChannelCategory CategoryGHub = main.api.getChannelCategoryById("1081936131542761634").get();
    public static final ChannelCategory CategoryTicket = main.api.getChannelCategoryById("1081946605680328874").get();
    public static final ChannelCategory CategoryRoles = main.api.getChannelCategoryById("1082074221871644824").get();
    public static final int defaultRoleSizeWithSplitterAndRulesAndEveryOne = RolesSplitterList().size() + 1 + 1;

    public static final Role RoleEveryone = main.api.getRoleById("1081921426333909072").get();
    public static final Role RoleBotsMC = main.api.getRoleById("1081965950905098310").get();
    //
    public static final Role RoleReglesValides = main.api.getRoleById("1081964412480528464").get();
    public static final Role RoleCasinoBypass = main.api.getRoleById("1081955127667212439").get();
    public static final Role RoleFondateur = main.api.getRoleById("1081954508998979625").get();
    public static final Role RoleDirection = main.api.getRoleById("1081954653949923488").get();
    public static final Role RoleModeration = main.api.getRoleById("1081954943491117256").get();
    public static final Role RoleAssistance = main.api.getRoleById("1081954923861790800").get();
    //
    public static final Role RoleVIP = main.api.getRoleById("1081955467623944292").get();
    public static final Role RoleBoost = main.api.getRoleById("1081955280490876948").get();// temporaire
    public static int prixVIP = 100_000;
    //
    public static final Role RoleTwitter = main.api.getRoleById("1081965199382302842").get();
    public static final Role RoleVideoLive = main.api.getRoleById("1081965222669074512").get();
    public static final Role RoleInstagram = main.api.getRoleById("1081965241837031495").get();
    public static final Role RoleMiseAJour = main.api.getRoleById("1081965280818905239").get();
    public static final Role RoleEvents = main.api.getRoleById("1081965300339191818").get();
    public static final Role RoleAnnonces = main.api.getRoleById("1081965259939647508").get();
    public static final Role RoleTournois = main.api.getRoleById("1081965322694828052").get();
    //
    public static final Role RoleGenshinImpact = main.api.getRoleById("1081964512049102858").get();
    public static final Role RoleGTAV = main.api.getRoleById("1081964533612019752").get();
    public static final Role RoleFortnite = main.api.getRoleById("1081964556915589151").get();
    public static final Role RoleCSGO = main.api.getRoleById("1081964578692411422").get();
    public static final Role RoleLeagueOfLegends = main.api.getRoleById("1081964599877836893").get();
    public static final Role RoleAlbionOnline = main.api.getRoleById("1081964685496156260").get();
    public static final Role RoleShopTitans = main.api.getRoleById("1081964836688244786").get();
    public static final Role RoleValorant = main.api.getRoleById("1081964620207624242").get();
    public static final Role RoleRocketLeague = main.api.getRoleById("1081964463906893916").get();
    public static final Role RoleSuperCell = main.api.getRoleById("1081964487822803094").get();
    public static final Role RoleRoblox = main.api.getRoleById("1115390280879837276").get();
    //
    public static final Role RolePC = main.api.getRoleById("1081965472720900147").get();
    public static final Role RoleTelephone = main.api.getRoleById("1081965501498007582").get();
    public static final Role RoleXBox = main.api.getRoleById("1081965530304483379").get();
    public static final Role RolePS = main.api.getRoleById("1081965549392769034").get();
    public static final Role RoleSwitch = main.api.getRoleById("1081965568808198255").get();
    //
    public static final Role RoleHomme = main.api.getRoleById("1081965387320676442").get();
    public static final Role RoleFemme = main.api.getRoleById("1081965406962581595").get();
    public static final Role RoleMineur = main.api.getRoleById("1081965427065880617").get();
    public static final Role RoleMajeur = main.api.getRoleById("1081965448314245193").get();
    //
    public static final Role Roleinvites100 = main.api.getRoleById("1081955509223043072").get();
    public static final Role Roleinvites50 = main.api.getRoleById("1081955554018209892").get();
    public static final Role Roleinvites30 = main.api.getRoleById("1081955596988846170").get();
    public static final Role Roleinvites10 = main.api.getRoleById("1081955638114000956").get();
    public static final Role Rolemsg1000 = main.api.getRoleById("1081955531494785139").get();
    public static final Role Rolemsg200 = main.api.getRoleById("1081955574528352366").get();
    public static final Role Rolemsg50 = main.api.getRoleById("1081955618954420335").get();
    public static final Role Rolemsg10 = main.api.getRoleById("1081955657856581662").get();
    //
    public static final String serverID = "1081921426333909072";
    //
    public static final String MinecraftInfo = "1092924495368564777";
    public static final String MinecraftReboot = "1081945572744892466";
    public static final String MinecraftUptime = "1093591413159112847";
    public static final String MinecraftMAJ = "1093593961597259817";

    //
    public static final String Notifications = "1082075867037061210";
    public static final String Profil = "1082076166158037104";
    public static final String AutresJeux = "1082075634374807562";
    //
    public static final String Boost = "1081941784252403742";
    public static final String Ticket = "1081946641268998164";
    public static final String LogsCmd = "1081947278132117504";
    public static final String LogsMessage = "1081947413226455163";
    public static final String Annonces = "1081940940945629194";
    public static final String Regles = "1082078579426345011";
    public static final String Invitation = "1081941748240089158";
    public static final String Sanctions = "1108535295227465798";
    public static final String AutoMod = "1081947188722155601";

    public static final String Statistiques = "1081940710799978546";
    public static final String Tops = "1081940819235319808";
    public static final String AFKVoice = "1081943448514482246";
    public static final String CasinoTextuelResultat = "1081943163985469511";// commandes
    //
    public static final String CasinoBoutique = "1081940864420548668";
    public static final String CasinoDailyClick = "1082775165366177912";
    public static final String CasinoCoinFlip = "1092029640845041694";
    public static final String CasinoBanqueRoute = "1106616355731542151";

    public static int prixCoinFlip = 1000;
    //
    public static final String EmojiChargement = "<:chargement:1083509029332078622>";
    public static final String EmojiProfil = "<:profil:1083508928316444694>";
    public static final String EmojiPin = "<:pin:1083508925485289574>";
    public static final String EmojiAlbionOnline = "<:albiononline:1083509077759508572>";
    public static final String EmojiValorant = "<:valorant:1083508856442859601>";
    public static final String EmojiLeagueOfLegends = "<:leagueoflegends:1083508964001583134>";
    public static final String EmojiCSGO = "<:csgo:1083509031823478804>";
    public static final String EmojiFortnite = "<:fortnite:1083509037338996858>";
    public static final String EmojiShopTitans = "<:shoptitans:1086739036984586260>";
    public static final String EmojiRocketLeague = "<:rocketleague:1083508931327963280>";
    public static final String EmojiSuperCell = "<:supercell:1083508844577165392>";
    public static final String EmojiGenshinImpact = "<:genshinimpact:1083509038702145646>";
    public static final String EmojiGTAV = "<:gta5:1083509024751898684>";
    public static final String EmojiXBox = "<:xbox:1083508857982177390>";
    public static final String EmojiPS = "<:ps:1083508929956425729>";
    public static final String EmojiSwitch = "<:switch:1083508846871449680>";
    //
    public static final String EmojiJava = "<:java:1083508962139312158>";
    public static final String EmojiBedrock = "<:bedrock:1083509091827208272>";
    public static final String EmojiMinecraft = "<:Minecraft:1083508972402782218>";
    public static final String EmojiMinecraftHeart = "<:minecraft_heart:1083508974785146902>";
    //
    public static final String EmojiClock = "\uD83D\uDD59";
    public static final String EmojiTicket = "\uD83C\uDFAB";
    public static final String EmojiOne = "1️⃣";
    public static final String EmojiTwo = "2️⃣";
    public static final String EmojiThree = "3️⃣";
    public static final String EmojiFour = "4️⃣";
    public static final String EmojiFive = "5️⃣";
    public static final String EmojiArrowsClockwise = "\uD83D\uDD03";
    public static final String EmojiMage = "\uD83E\uDDD9\u200D♂️";
    public static final String EmojiCrossedSwords = "⚔️";
    public static final String EmojiWindBlowingFace = "\uD83C\uDF2C️";
    public static final String EmojiMovieCamera = "\uD83C\uDFA5";
    public static final String EmojiFramePhoto = "\uD83D\uDDBC️";
    public static final String EmojiDesktop = "\uD83D\uDDA5️";
    public static final String EmojiLoudSpeaker = "\uD83D\uDCE2";
    public static final String EmojiPageWithCurl = "\uD83D\uDCC3";
    public static final String EmojiComputer = "\uD83D\uDCBB";
    public static final String EmojiMobilePhone = "\uD83D\uDCF1";
    public static final String EmojiMaleSign = "♂️";
    public static final String EmojiFemaleSign = "♀️";
    public static final String EmojiUnderage = "\uD83D\uDD1E";
    public static final String EmojiOlderMan = "\uD83D\uDC74";
    public static final String EmojiGameDie = "\uD83C\uDFB2";
    public static final String EmojiSpeechBalloon = "\uD83D\uDCAC";
    public static final String EmojiDiamondShapeWithADotInside = "\uD83D\uDCA0";
    public static final String EmojiKnife = "\uD83D\uDD2A";
    public static final String EmojiVideoGame = "\uD83C\uDFAE";
    public static final String EmojiCalendar = "📆";
    public static final String EmojiCrown = "👑";
    public static final String EmojiMedicalSymbol = "⚕️";
    public static final String EmojiShield = "\uD83D\uDEE1️";
    public static final String EmojiBowAndArrow = "\uD83C\uDFF9";
    public static final String EmojiPersonTippingHand = "\uD83D\uDC81";
    public static final String EmojiGreenCrossMark = "❎";
    public static final String EmojiWhiteCheckMark = "✅";
    public static final String EmojiNewsPaper = "📰";
    public static final String EmojiMoneyWithWings = "💸";
    public static final String EmojiMouseThreeButton = "🖱️";
    public static final String EmojiCoin = "🪙";
    public static final String EmojiBanque = "🏦";
    public static final String EmojiRocket = "🚀";

    public static ArrayList<String> VoiceChannelsCreator() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("1081943513081577512");// général
        return list;
    }

    public static ArrayList<Role> RolesJeux() {
        ArrayList<Role> list = new ArrayList<Role>();
        list.add(RoleGenshinImpact);
        list.add(RoleAlbionOnline);
        list.add(RoleShopTitans);
        list.add(RoleRocketLeague);
        list.add(RoleSuperCell);
        list.add(RoleGTAV);
        list.add(RoleFortnite);
        list.add(RoleCSGO);
        list.add(RoleLeagueOfLegends);
        list.add(RoleValorant);
        list.add(RoleRoblox);
        return list;
    }

    public static ArrayList<Role> RolesSplitterList() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(main.api.getRoleById("1081955686688247960").get());
        roles.add(main.api.getRoleById("1081965082776449096").get());
        roles.add(main.api.getRoleById("1081965351367102495").get());
        roles.add(main.api.getRoleById("1081965587951014009").get());
        return roles;
    }
}