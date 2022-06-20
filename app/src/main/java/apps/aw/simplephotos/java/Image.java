package apps.aw.simplephotos.java;

import android.os.Parcelable;

import java.io.File;

/**
 * This class contains all data of an image, i.e.
 *  the filename, size, ...
 */
public class Image extends Item {

    private final File file;
    private final String date;

    public Image(File file, String date) {
        super();
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
