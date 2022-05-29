package apps.aw.simplephotos.java;

public class ViewData {
    public ItemList column1;
    public ItemList column2;
    public Item item;
    public Path path;

    public ViewData(ItemList column1, ItemList column2, Item item, Path path) {
        this.column1 = column1;
        this.column2 = column2;
        this.item = item;
        this.path = path;
    }
}
