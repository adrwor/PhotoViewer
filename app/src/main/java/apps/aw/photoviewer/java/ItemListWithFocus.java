package apps.aw.photoviewer.java;

import java.util.ArrayList;
import java.util.List;

// used for displaying a list of items (filenames, directorynames, actions, root, ...)
public class ItemListWithFocus extends ColumnViewData {

    private List<ListItem> itemList;  //list of items
    private int focus;    //index of focused item

    public ItemListWithFocus() {
        this.itemList = new ArrayList<>();
        this.focus = 0;
    }

    public ItemListWithFocus(List<ListItem> itemList, int focus) {
        this.itemList = itemList;
        this.focus = focus;
    }

    public static ItemListWithFocus emptyItemList() {
        return new ItemListWithFocus();
    }

    public int getListSize() {
        return itemList.size();
    }

    public void setItemList(List<ListItem> itemList) {
        this.itemList = itemList;
    }

    public List<ListItem> getItemList() {
        return itemList;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }

    public int getFocus() {
        return focus;
    }

    public ListItem getFocusedItem() {
        return itemList.get(focus);
    }

}
