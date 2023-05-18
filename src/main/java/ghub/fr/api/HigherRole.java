package ghub.fr.api;

import java.util.List;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import ghub.fr.main.IDs;
import ghub.fr.main.main;

public class HigherRole {
    public static boolean isRoleHigher(User user, User user2) {
        Server server = main.api.getServerById(IDs.serverID).get();

        List<Role> roles = user.getRoles(server);
        Role role = roles.get(roles.size() - 1);

        List<Role> roles2 = user2.getRoles(server);
        Role role2 = roles2.get(roles2.size() - 1);

        if (role.getPosition() > role2.getPosition()) {
            return true;
        } else {
            return false;
        }
    }
}
