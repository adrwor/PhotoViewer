package apps.aw.simplephotos.domain_layer.model;

import androidx.annotation.NonNull;

/**
 * Represents a node in the filesystem navigation cache.
 */
public class FileSystemNode {
    private @NonNull FileModel fileModel;
    private int selectedChildPosition;

    FileSystemNode(@NonNull FileModel fileModel) {
        this.fileModel = fileModel;
        this.selectedChildPosition = 0;
    }

    FileSystemNode(@NonNull FileModel fileModel, int selectedChildPosition) {
        this.fileModel = fileModel;
        this.selectedChildPosition = selectedChildPosition;
    }

    @NonNull
    public FileModel getFileModel() {
        return fileModel;
    }

    public int getFocusedChildPosition() {
        return selectedChildPosition;
    }

    public void setFocusedChildPosition(int position) {
        this.selectedChildPosition = position;
    }

}
