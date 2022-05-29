package apps.aw.simplephotos.java;

import java.io.File;

/**
 * This class contains all data of an image preview, i.e. a small bitmap of the image,
 *  the filename, size, ...
 */
public class Image extends Item {
    //TODO: add datatypes for the preview (bitmap) and infos (strings)

    private final File file;
    private final String date;
//    private Drawable drawable;


    public Image(File file, String date) {
        super();
        // TODO: maybe check here the type of the file (if it is really an image)
        this.date = date;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getDate() {
        return date;
    }
}
