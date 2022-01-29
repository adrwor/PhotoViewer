package apps.aw.simplephotos.presentation_layer.browser.utils;

import androidx.annotation.NonNull;

/**
 * This class represents either a directory or an image preview.
 */
public class FileContentView {

    private FileList fileList;
    private ImagePreview imagePreview;
    private DisplayType displayType;

    public FileContentView(@NonNull FileList fileList){
        this.fileList = fileList;
        displayType = DisplayType.FILELIST;
    }

    public FileContentView(@NonNull ImagePreview imagePreview) {
        this.imagePreview = imagePreview;
        displayType = DisplayType.IMAGE;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public void setFileList(FileList fileList) {
        this.imagePreview = null;
        this.fileList = fileList;
        displayType = DisplayType.FILELIST;
    }

    public FileList getFileList() {
        return fileList;
    }

    public void setImagePreview(ImagePreview imagePreview) {
        this.fileList = null;
        this.imagePreview = imagePreview;
        displayType = DisplayType.IMAGE;
    }

    public ImagePreview getImagePreview() {
        return imagePreview;
    }
}

