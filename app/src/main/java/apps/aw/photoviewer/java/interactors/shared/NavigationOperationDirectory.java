package apps.aw.photoviewer.java.interactors.shared;

public class NavigationOperationDirectory extends NavigationOperation {
    public Direction direction;

    public NavigationOperationDirectory(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        NEUTRAL,
        PARENT,
        CHILD
    }
}
