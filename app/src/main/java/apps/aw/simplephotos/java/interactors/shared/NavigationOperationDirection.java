package apps.aw.simplephotos.java.interactors.shared;

public class NavigationOperationDirection extends NavigationOperation {
    public Direction direction;

    public NavigationOperationDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        NEUTRAL,
        PREVIOUS,
        NEXT
    }
}
