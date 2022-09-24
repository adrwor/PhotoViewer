package apps.aw.photoviewer.java.treenavigator;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class ActionNode implements NodeData {

    final public String name;
    final public int action;

    public ActionNode(String name, int action) {
        this.name = name;
        this.action = action;
    }



    @Override
    public int getFocus() {
        return 0;
    }

    @Override
    public void setFocus(int index) {}

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public LocalDateTime getChangeDate() {
        return null;
    }

    @Override
    public void setChangeDate(LocalDateTime date) {

    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }
}
