package apps.aw.simplephotos.android.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import apps.aw.simplephotos.java.storagepaths.StoragePath;
import apps.aw.simplephotos.java.storagepaths.StoragePathProvider;

public class StoragePaths implements StoragePathProvider {

    private final List<StoragePath> storagePaths;

    public StoragePaths(Context context) {
        this.storagePaths = new ArrayList<>();
        storagePaths.add(new StoragePath(context.getFilesDir(), "InternalDeviceStorage"));
        storagePaths.add(new StoragePath(Environment.getExternalStorageDirectory(), "ExternalStorageDirectory"));
        storagePaths.add(new StoragePath(new File("storage"), "storage"));
        storagePaths.add(new StoragePath(new File("storage/emulated/0"), "storage/emulated/0"));

    }

    @Override
    public List<StoragePath> getPaths() {
        return storagePaths;
    }
}
