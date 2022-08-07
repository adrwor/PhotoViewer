package apps.aw.simplephotos.java.treenavigator;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Class which represents a directory, i.e. it stores an absolute path.
 */
public class FileNode implements NodeData {

    private final File file;      // file
    private String name;  // displayed name, usually the name of the file
    private boolean isDirectory;
    private FileMetaData fileMetaData;
    private int focus;  // if file is directory, caches last focus position
    private LocalDateTime lastSync; // stores the date and time of last sync with filesystem

    /**
     * Constructor
     * @param file The corresponding file
     * @param fileMetaData Metadata about the file/image
     */
    public FileNode(File file, String name, boolean isDirectory, FileMetaData fileMetaData) {
        assert file != null;
        assert name != null;
        assert fileMetaData != null;
        this.file = file;
        this.name = name;
        this.isDirectory = isDirectory;
        this.fileMetaData = fileMetaData;
    }

    public File getFile() {
        return file;
    }

    public FileMetaData getMetaData() {
        return fileMetaData;
    }

    public void setMetaData(FileMetaData fileMetaData) {
        this.fileMetaData = fileMetaData;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
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
    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getChangeDate() {
        return null;
    }

    @Override
    public void setChangeDate(LocalDateTime date) {

    }


//    private int compareToOtherFileNode(FileNode o) {
//        // TODO: enable multiple different comparison attributes (e.g. name, date, size, ...)
//        if(isDirectory() && !o.isDirectory()) {
//            return -1;
//        } else if(getMetaData().isImageFile) {
//
//        }
//        return this.fileMetaData.compareTo(o.getMetaData());
//    }
//
//    @Override
//    public int compareTo(NodeData o) {
//        if (o instanceof FileNode) {
//            return this.compareToOtherFileNode((FileNode) o);
//        }
//        else {
//            return 1; // FileNodes should come after every other NodeData type
//        }
//    }
}