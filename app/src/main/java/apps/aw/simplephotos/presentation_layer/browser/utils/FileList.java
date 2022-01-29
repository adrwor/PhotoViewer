package apps.aw.simplephotos.presentation_layer.browser.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of filenames as well as the selection, which includes an index and a state.
 */
public class FileList {

    private List<String> fileList;  //list of filenames
    private int selection;    //index of selected filename, as well as state

    public FileList() {
        this.fileList = new ArrayList<>();
        this.selection = 0;
    }

    public FileList(List<String> fileList, int selection) {
        this.fileList = fileList;
        this.selection = selection;
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

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public int getSelection() {
        return selection;
    }


}
