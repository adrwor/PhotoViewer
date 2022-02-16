package apps.aw.simplephotos;


import java.io.File;
import java.util.List;

import apps.aw.simplephotos.domain_layer.model.FilePreviewModel;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileContentView;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;

public interface Interactor {

    String getCurrentPath();

    void toParent();

    void toFocusedChild();

    void focusChild(int position);

    int getFocusedChildIndex();

    void focusNextChild();

    void focusPreviousChild();

    FileList getParentSubFiles();

    FileList getSubFiles();

    FileContentView getContentOfFocusedChild();

}
