package apps.aw.photoviewer.java.treenavigator;

import java.util.List;

import apps.aw.photoviewer.java.ColumnViewData;
import apps.aw.photoviewer.java.ItemListWithFocus;
import apps.aw.photoviewer.java.Path;

/**
 * Interface for navigating using the three columns,
 */
public interface TreeNavigator {
    // TODO: add methods which should be implemented by a navigator (e.g. TreeNavigatorImpl)
    //  Note: should an implementation of this TreeNavigator navigator be thread-safe? (because it has a state)

    boolean toParent();

    boolean toFocusedChild();

    boolean setFocus(int position);

    ItemListWithFocus getParentItems();

    ItemListWithFocus getItemList();

    ColumnViewData getContentOfFocusedChild();

    Path getPathOfFocusedChild();

    List<FileNode> getImageChildren();

    NodeData getFocusedNodeData();

    void resyncFocusedNode();
}
