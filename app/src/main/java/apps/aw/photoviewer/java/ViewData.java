package apps.aw.photoviewer.java;

public class ViewData {
    public ItemListWithFocus column1;
    public ItemListWithFocus column2;
    public ColumnViewData columnViewData;
    public Path path;

    public ViewData(ItemListWithFocus column1, ItemListWithFocus column2, ColumnViewData columnViewData, Path path) {
        this.column1 = column1;
        this.column2 = column2;
        this.columnViewData = columnViewData;
        this.path = path;
    }
}
