package apps.aw.simplephotos.domain_layer.model;

import androidx.annotation.NonNull;

import java.io.File;

/**
 * Class which represents a directory, i.e. it stores an absolute path.
 */
public class FileModel implements Comparable<FileModel> {

    private File file;      //file
    private FileMetaData fileMetaData;    //contains exif date, can be null


    /**
     * Constructor
     * @param file
     * @param fileMetaData
     */
    public FileModel(@NonNull File file, FileMetaData fileMetaData) {
        this.file = file;
        this.fileMetaData = fileMetaData;
    }

    public FileModel(@NonNull File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public FileMetaData getMetaData() {
        return fileMetaData;
    }

    public boolean isDirectory() throws SecurityException {
        return file.isDirectory();
    }


    @Override
    public int compareTo(FileModel fileModel) {
        // TODO: enable multiple different comparison attributes (e.g. name, date, size, ...)
        return this.fileMetaData.compareTo(fileModel.getMetaData());
    }
}