package apps.aw.simplephotos.domain.interactorinterfaces;

public class NavigationChangeDirectory extends NavigationChange {
    public Direction direction;

    public NavigationChangeDirectory(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        UP,
        DOWN
    }
}
