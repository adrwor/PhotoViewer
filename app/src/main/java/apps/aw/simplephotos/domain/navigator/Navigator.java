package apps.aw.simplephotos.domain.navigator;

import java.io.File;

import apps.aw.simplephotos.domain.FileContent;
import apps.aw.simplephotos.domain.FileList;

public interface Navigator {
    // TODO: add methods which should be implemented by a navigator (e.g. NodeTreeNavigator)
    //  Note: the implementation of a navigator should be thread-safe! (because it has a state)

    void addSubRoot(File file);

    boolean toParent();

    boolean toChild();

    boolean setFocus(int position);

    FileList getParentSubFiles();

    FileList getCurrentSubFiles();

    FileContent getContentOfFocusedChildNode();

    String getPath();



}
