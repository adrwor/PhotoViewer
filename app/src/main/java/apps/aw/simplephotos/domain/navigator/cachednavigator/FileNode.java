package apps.aw.simplephotos.domain.navigator.cachednavigator;

import java.io.File;

/**
 * Class which represents a directory, i.e. it stores an absolute path.
 */
public class FileNode implements Comparable<FileNode>, NodeData {

    private final File file;      // file
    private FileMetaData fileMetaData;    // contains exif date, can be null
    private int focus;  // if file is directory, caches last focus position


    /**
     * Constructor
     * @param file The corresponding file
     * @param fileMetaData Metadata about the file/image
     */
    public FileNode(File file, FileMetaData fileMetaData) {
        this.file = file;
        this.fileMetaData = fileMetaData;
    }

    public FileNode(File file) {
        this.file = file;
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

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public int compareTo(FileNode fileNode) {
        // TODO: enable multiple different comparison attributes (e.g. name, date, size, ...)
        return this.fileMetaData.compareTo(fileNode.getMetaData());
    }
}