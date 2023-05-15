package ghub.fr.commands;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.interaction.ApplicationCommandBuilder;
import org.javacord.api.interaction.MessageContextMenuBuilder;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.interaction.UserContextMenuBuilder;

import ghub.fr.listener.casinoCoinFlip;
import ghub.fr.listener.createButton;
import ghub.fr.main.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class commands {
        public static void onCommands() throws ExecutionException, InterruptedException {
                List<ApplicationCommandBuilder<?, ?, ?>> builders = new ArrayList<>();
                builders.addAll(arrays());
                builders.addAll(arraysUserContextMenu());
                builders.addAll(arraysMessageContextMenu());
                main.api.bulkOverwriteGlobalApplicationCommands(builders).join();

                EmptyChannel.onEmptyChannel();
                testping.onTestPing();
                profil.onProfil();
                profil.onProfilUserContextMenu();
                mute.setMute();
                kick.onKickUser();
                ban.banUser();
                addReaction.onAddReaction();
                copyMessage.onCopyMessage();
                createInvite.onCreateInvite();
                help.onHelp();
                casinoCoinFlip.coinFlipCmd();
                createButton.onCreateButton();
                ip.onIP();
        }

        public static List<SlashCommandBuilder> arrays() {
                ArrayList<SlashCommandBuilder> arrays = new ArrayList<SlashCommandBuilder>();
                arrays.add(EmptyChannel());
                arrays.add(Test());
                arrays.add(Ping());
                arrays.add(Profil());
                arrays.add(Mute());
                arrays.add(Kick());
                arrays.add(addReaction());
                arrays.add(Ban());
                arrays.add(copyMessage());
                arrays.add(createInvite());
                arrays.add(Help());
                arrays.add(CoinFlip());
                arrays.add(CreateButton());
                arrays.add(IP());
                return arrays.stream().toList();
        }

        public static List<UserContextMenuBuilder> arraysUserContextMenu() {
                ArrayList<UserContextMenuBuilder> arrays = new ArrayList<UserContextMenuBuilder>();
                arrays.add(profilUserContext());
                return arrays.stream().toList();
        }

        public static List<MessageContextMenuBuilder> arraysMessageContextMenu() {
                ArrayList<MessageContextMenuBuilder> arrays = new ArrayList<MessageContextMenuBuilder>();
                return arrays.stream().toList();
        }

        /*
         * 
         */

        public static SlashCommandBuilder EmptyChannel() {
                return new SlashCommandBuilder().setName("emptychannel")
                                .setDescription("Vide le channel de tout ses messages")
                                .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR);
        }

        public static SlashCommandBuilder Test() {
                return new SlashCommandBuilder().setName("test").setDescription("Test")
                                .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR);
        }

        public static SlashCommandBuilder addReaction() {
                return new SlashCommandBuilder().setName("addreaction")
                                .setDescription("Ajoute une réaction sur tout les messages")
                                .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR);
        }

        public static SlashCommandBuilder CreateButton() {
                return new SlashCommandBuilder().setName("createbutton")
                                .setDescription("Crée un bouton de test")
                                .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR)

                                .addOption(option(SlashCommandOptionType.CHANNEL, "Channel",
                                                "Le salon où envoyer le bouton",
                                                true))
                                .addOption(option(SlashCommandOptionType.STRING, "Button_ID",
                                                "L'identifiant du bouton",
                                                true))
                                .addOption(option(SlashCommandOptionType.STRING, "Button_Text",
                                                "Le texte du bouton",
                                                true));
        }

        public static SlashCommandBuilder copyMessage() {
                return new SlashCommandBuilder().setName("copymessage")
                                .setDescription("Copie et republie un message")
                                .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR)
                                .addOption(option(SlashCommandOptionType.STRING, "Message_ID",
                                                "L'identifiant du message à recopier",
                                                true))
                                .addOption(option(SlashCommandOptionType.STRING, "Channel_ID",
                                                "L'identifiant du salon où envouyer le message",
                                                true));
        }

        public static SlashCommandBuilder createInvite() {
                return new SlashCommandBuilder().setName("createinvite")
                                .setDescription("Créer une invitation")
                                .setDefaultEnabledForPermissions(PermissionType.ADMINISTRATOR)
                                .addOption(option(SlashCommandOptionType.STRING, "Channel_ID",
                                                "L'identifiant du salon pour l'invitation",
                                                true));
        }

        public static SlashCommandBuilder Ping() {
                return new SlashCommandBuilder().setName("ping").setDescription("Ping le bot ghub.fr");
        }

        public static SlashCommandBuilder IP() {
                return new SlashCommandBuilder().setName("ip").setDescription("Affiche les adresses serveurs");
        }

        public static SlashCommandBuilder Help() {
                return new SlashCommandBuilder().setName("help").setDescription("Liste des commandes");
        }

        public static SlashCommandBuilder Profil() {
                return new SlashCommandBuilder().setName("profil")
                                .setDescription("Affiche le profil discord d'un membre")
                                .addOption(option(SlashCommandOptionType.USER, "Utilisateur",
                                                "Un utilisateur du serveur à vérifier",
                                                false));
        }

        public static UserContextMenuBuilder profilUserContext() {
                return new UserContextMenuBuilder().setName("profil")
                                .setDefaultEnabledForEveryone();
        }

        public static SlashCommandBuilder CoinFlip() {
                return new SlashCommandBuilder().setName("coinflip")
                                .setDescription("Faire un lancé de pièce")
                                .addOption(option(SlashCommandOptionType.DECIMAL, "Argent",
                                                "Argent à parier (0 par défaut)",
                                                false));
        }

        public static SlashCommandBuilder Mute() {
                return new SlashCommandBuilder().setName("mute").setDescription("Rend muet un utilisateur")
                                .addOption(option(SlashCommandOptionType.USER, "Utilisateur",
                                                "Un utilisateur du serveur à rendre muet",
                                                true))
                                .addOption(option(SlashCommandOptionType.DECIMAL, "Minutes",
                                                "Le temps en minutes pour le mute (1 à 43200 minutes [30j])", true))
                                .addOption(option(SlashCommandOptionType.STRING, "Raison", "Raison du mute", true))
                                .setDefaultEnabledForPermissions(PermissionType.MUTE_MEMBERS);
        }

        public static SlashCommandBuilder Ban() {
                return new SlashCommandBuilder().setName("ban").setDescription("Banni un utilisateur")
                                .addOption(option(SlashCommandOptionType.USER, "Utilisateur",
                                                "Un utilisateur du serveur à bannir",
                                                true))
                                .addOption(option(SlashCommandOptionType.DECIMAL, "Jours",
                                                "Le temps en jours pour la suppression des messages (1 à 7 jours)",
                                                true))
                                .addOption(option(SlashCommandOptionType.STRING, "Raison", "Raison du ban", true))
                                .setDefaultEnabledForPermissions(PermissionType.BAN_MEMBERS);
        }

        public static SlashCommandBuilder Kick() {
                return new SlashCommandBuilder().setName("kick").setDescription("Exclure un utilisateur")
                                .addOption(
                                                option(SlashCommandOptionType.USER, "Utilisateur",
                                                                "Un utilisateur du serveur à exclure", true))
                                .addOption(option(SlashCommandOptionType.STRING, "Raison", "Raison de l'exclusion",
                                                true))
                                .setDefaultEnabledForPermissions(PermissionType.KICK_MEMBERS);
        }

        public static SlashCommandOption option(SlashCommandOptionType slashCommandOptionType, String name,
                        String Description, boolean required) {
                return SlashCommandOption.create(slashCommandOptionType, name, Description, required);
        }
}
