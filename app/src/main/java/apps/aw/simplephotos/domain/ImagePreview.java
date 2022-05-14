package apps.aw.simplephotos.domain;

import java.io.File;

import apps.aw.simplephotos.data.ImageFileExifReader;

/**
 * This class contains all data of an image preview, i.e. a small bitmap of the image,
 *  the filename, size, ...
 */
public class ImagePreview extends FileContent {
    //TODO: add datatypes for the preview (bitmap) and infos (strings)

    private File file;
    private String date;
//    private Drawable drawable;


    public ImagePreview(File file) {
        super();
        // TODO: maybe check here the type of the file (if it is really an image)
        date = ImageFileExifReader.getExifData(file);
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getDate() {
        return date;
    }
}
