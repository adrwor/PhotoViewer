package apps.aw.simplephotos.java.storagepaths;

import java.io.File;

public class StoragePath {

    public File path;
    public String name;

    public StoragePath(File path, String name) {
        this.path = path;
        this.name = name;
    }
}
