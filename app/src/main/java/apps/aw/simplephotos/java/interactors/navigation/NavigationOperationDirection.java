package apps.aw.simplephotos.java.interactors.navigation;

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
