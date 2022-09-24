package apps.aw.photoviewer.java.treenavigator;

import java.io.File;

public interface FileMetaDataReader {
    public FileMetaData readFileMetaData(File file);
    public boolean isImageFile(File file);
}
