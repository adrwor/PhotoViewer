package apps.aw.simplephotos.domain_layer.model;

import java.io.File;
import java.util.List;

/**
 * This class is used for browsing for Images. When changing to a directory, it (asynchronously?) sorts the images by
 * Exif creation date, returns the sorted list, and also caches it in a datastructure indexed by the path of the directory.
 */
public class SortedImageBrowser {

    private File currentDir;

    /**
     * Constructor
     * @param initialPath initial path
     */
    public SortedImageBrowser(String initialPath) {
        currentDir = new File(initialPath);
    }

    /**
     * Returns the directory currently in.
     * @return File object representing a directory.
     */
    public File getCurrentDir() {
        return currentDir;
    }

    /**
     * Changes the current directory to the given directory
     * @param dir the new directory
     */
    public void setCurrentDir(File dir) {
        this.currentDir = dir;
    }

    /**
     * Lists all files in the current directory. The files are sorted as follows:
     * First all Folders.
     * Then all non-image files.
     * Then all image files, sorted by exif-date.
     * TODO: maybe use custom object to hold the different files, images etc.
     * @return a List of Files.
     */
    public List<File> listFiles() {
        //TODO: check if the files in this directory were already sorted before and stored in cache.
        //If yes, retrieve the list from cache.
        //If not, get list of all files in this directory, sort it, and store it in cache. Then return it.
        return null;
    }








}
