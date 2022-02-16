package apps.aw.simplephotos.presentation_layer.browser.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of filenames as well as the selection, which includes an index and a state.
 */
public class FileList {

    private List<String> fileList;  //list of filenames
    private int focus;    //index of focused filename

    public FileList() {
        this.fileList = new ArrayList<>();
        this.focus = 0;
    }

    public FileList(List<String> fileList, int focus) {
        this.fileList = fileList;
        this.focus = focus;
    }

    public int getListSize() {
        return fileList.size();
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getFocus() {
        return focus;
    }


}
