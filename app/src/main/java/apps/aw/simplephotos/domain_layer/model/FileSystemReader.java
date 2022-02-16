package apps.aw.simplephotos.domain_layer.model;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSystemReader {

    public static List<FileModel> getSubFileModels(File file) {
        File[] files = file.listFiles();
        if(files == null) {
            return new ArrayList<>();
        }
        ArrayList<FileModel> fileModels = new ArrayList<>();
        for (File f: files) {
            fileModels.add(new FileModel(f));
        }
        return fileModels;
    }

}
