package apps.aw.simplephotos.android.storage;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.java.storagepath.StoragePath;
import apps.aw.simplephotos.java.storagepath.StoragePathProvider;

public class StoragePaths implements StoragePathProvider {

    private final List<StoragePath> storagePaths;

    public StoragePaths(Context context) {
        this.storagePaths = new ArrayList<>();
        storagePaths.add(new StoragePath(Environment.getExternalStorageDirectory(), "storage/emulated/0"));
        storagePaths.add(new StoragePath(new File("/"), "/"));
//        storagePaths.add(new StoragePath(new File("/mnt/media_rw/"), "/mnt/media_rw/"));

    }

    @Override
    public List<StoragePath> getPaths() {
        return storagePaths;
    }
}
