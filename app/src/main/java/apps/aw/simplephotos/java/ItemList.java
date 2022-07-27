package apps.aw.simplephotos.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a list of filenames as well as the selection, which includes an index and a state.
 */
public class ItemList extends Item {

    private List<String> fileList;  //list of filenames
    private int focus;    //index of focused filename

    public ItemList() {
        this.fileList = new ArrayList<>();
        this.focus = 0;
    }

    public ItemList(List<String> fileList, int focus) {
        this.fileList = fileList;
        this.focus = focus;
    }

    public static ItemList emptyItemList() {
        return new ItemList();
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
