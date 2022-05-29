package apps.aw.simplephotos.java.interactors;

public class NavigationOperationDirectory extends NavigationOperation {
    public Direction direction;

    public NavigationOperationDirectory(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        UP,
        DOWN
    }
}
