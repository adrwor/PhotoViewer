package apps.aw.simplephotos.domain.navigator.cachednavigator;

public interface NodeData {

    public int getFocus();

    public void setFocus(int index);

    public boolean isDirectory();

    public String toString();
}
