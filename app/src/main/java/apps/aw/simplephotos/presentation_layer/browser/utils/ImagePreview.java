package apps.aw.simplephotos.presentation_layer.browser.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.File;

import apps.aw.simplephotos.domain_layer.model.ImageFileExifReader;

/**
 * This class contains all data of an image preview, i.e. a small bitmap of the image,
 *  the filename, size, ...
 */
public class ImagePreview {
    //TODO: add datatypes for the preview (bitmap) and infos (strings)

    private @NonNull File file;
    private String date;
//    private Drawable drawable;


    public ImagePreview(@NonNull File file) {
        // TODO: maybe check here the type of the file (if it is really an image)
        date = ImageFileExifReader.getExifData(file);
        this.file = file;
    }

    @NonNull
    public File getFile() {
        return file;
    }

    public String getDate() {
        return date;
    }
}
