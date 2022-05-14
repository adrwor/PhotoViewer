package apps.aw.simplephotos.domain.navigator.cachednavigator;


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
    public String toString() {
        return name;
    }
}
