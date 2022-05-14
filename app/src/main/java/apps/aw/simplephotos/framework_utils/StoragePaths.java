package apps.aw.simplephotos.framework_utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class StoragePaths {

    public File internalDeviceStorage;
    public File externalStorageDirectory;

    public StoragePaths(Context context) {
        this.internalDeviceStorage = context.getFilesDir();
        this.externalStorageDirectory = Environment.getExternalStorageDirectory();
    }

}
