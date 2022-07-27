package apps.aw.simplephotos.java.treenavigator;

import java.util.List;

import apps.aw.simplephotos.java.Item;
import apps.aw.simplephotos.java.ItemList;
import apps.aw.simplephotos.java.Path;

/**
 * Interface for navigating using the three columns,
 */
public interface TreeNavigator {
    // TODO: add methods which should be implemented by a navigator (e.g. NodeTreeNavigator)
    //  Note: should an implementation of this TreeNavigator navigator be thread-safe? (because it has a state)

    boolean toParent();

    boolean toFocusedChild();

    boolean setFocus(int position);

    ItemList getParentItems();

    ItemList getItemList();

    Item getContentOfFocusedChild();

    Path getPathOfFocusedChild();

    List<Item> getContentOfImageChildren();
}
