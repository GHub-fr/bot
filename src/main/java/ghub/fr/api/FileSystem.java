package ghub.fr.api;

import org.javacord.api.entity.user.User;

import java.io.File;
import java.io.IOException;

public class FileSystem {
    public static File file(User user) throws IOException {
        File file = new File(getFolder(), user.getIdAsString());
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static File getFolder() {
        File path = new File("Data");
        path.mkdirs();
        return path;
    }

    public static File fileGlobal() throws IOException {
        File file = new File(getFolder(), "globalInvites");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static File event() throws IOException {
        File file = new File(getFolder(), "event");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}