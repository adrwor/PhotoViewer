package apps.aw.simplephotos.presentation_layer.browser.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * This class contains all data of an image preview, i.e. a small bitmap of the image,
 *  the filename, size, ...
 */
public class ImagePreview {
    //TODO: add datatypes for the preview (bitmap) and infos (strings)

    private @NonNull File file;
//    private Drawable drawable;


    public ImagePreview(@NonNull File file) {
        this.file = file;
    }

    @NonNull
    public File getFile() {
        return file;
    }
}
