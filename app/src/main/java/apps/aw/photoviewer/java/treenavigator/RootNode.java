package apps.aw.photoviewer.java.treenavigator;


import java.time.LocalDateTime;

public class RootNode implements NodeData {

    final private String name;
    private int focus;

    public RootNode(String name) {
        this.name = name;
    }

    @Override
    public int getFocus() {
        return focus;
    }

    @Override
    public void setFocus(int index) {
        focus = index;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getChangeDate() {
        return null;
    }

    @Override
    public void setChangeDate(LocalDateTime date) { }
}
