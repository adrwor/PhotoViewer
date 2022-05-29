package apps.aw.simplephotos.java.treenavigator;

import java.time.LocalDateTime;

public interface NodeData {

    int getFocus();

    void setFocus(int index);

    boolean isDirectory();

    String toString();

    LocalDateTime getChangeDate();

    void setChangeDate(LocalDateTime date);
}
