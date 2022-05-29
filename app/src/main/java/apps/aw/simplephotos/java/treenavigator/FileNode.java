package apps.aw.simplephotos.java.treenavigator;

import androidx.annotation.NonNull;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Class which represents a directory, i.e. it stores an absolute path.
 */
public class FileNode implements Comparable<FileNode>, NodeData {

    private final File file;      // file
    private String name;  // displayed name, usually the name of the file
    private FileMetaData fileMetaData;    // contains exif date, can be null
    private int focus;  // if file is directory, caches last focus position
    private LocalDateTime lastSync; // stores the date and time of last sync with filesystem

    /**
     * Constructor
     * @param file The corresponding file
     * @param fileMetaData Metadata about the file/image
     */
    public FileNode(File file, String name, FileMetaData fileMetaData) {
        this.file = file;
        this.name = name;
        this.fileMetaData = fileMetaData;
    }

    public FileNode(File file, String name) {
        this.file = file;
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public FileMetaData getMetaData() {
        return fileMetaData;
    }

    @Override
    public boolean isDirectory() throws SecurityException {
        return file.isDirectory();
    }

    @Override
    public int getFocus() {
        return focus;
    }

    @Override
    public void setFocus(int index) {
        focus = index;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public LocalDateTime getChangeDate() {
        return null;
    }

    @Override
    public void setChangeDate(LocalDateTime date) {

    }

    @Override
    public int compareTo(FileNode fileNode) {
        // TODO: enable multiple different comparison attributes (e.g. name, date, size, ...)
        return this.fileMetaData.compareTo(fileNode.getMetaData());
    }
}