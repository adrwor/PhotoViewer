package apps.aw.simplephotos.domain.interactorinterfaces;

public class NavigationChangeFocus extends NavigationChange {
    public int index;

    public NavigationChangeFocus(int index) {
        this.index = index;
    }
}
