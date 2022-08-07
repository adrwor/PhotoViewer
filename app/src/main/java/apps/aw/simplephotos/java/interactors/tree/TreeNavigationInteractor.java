package apps.aw.simplephotos.java.interactors.tree;

import java.util.ArrayList;

import apps.aw.simplephotos.java.FileListWithIndex;
import apps.aw.simplephotos.java.ListItem;
import apps.aw.simplephotos.java.ViewData;
import apps.aw.simplephotos.java.interactors.Interactor;
import apps.aw.simplephotos.java.interactors.shared.Navigation;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperation;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationDirectory;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationFocus;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationEnter;
import apps.aw.simplephotos.java.treenavigator.ActionNode;
import apps.aw.simplephotos.java.treenavigator.FileNode;
import apps.aw.simplephotos.java.treenavigator.TreeNavigator;
import apps.aw.simplephotos.java.executor.Executor;
import apps.aw.simplephotos.java.executor.MainThread;

public class TreeNavigationInteractor implements Interactor, Navigation {

    NavigationOperation navigationOperation;
    private Callback callback;
    private final TreeNavigator treeNavigator;
    private final Executor executor;
    private final MainThread mainThread;

    public TreeNavigationInteractor(TreeNavigator treeNavigator, Executor executor, MainThread mainThread) {
        this.treeNavigator = treeNavigator;
        this.executor = executor;
        this.mainThread = mainThread;
    }

    @Override
    public void execute(NavigationOperation navigationOperation, Callback callback) {
        // This execute function is specified by the ChangeDirectory class
        if (callback == null) {
            throw new IllegalArgumentException(
                    "Callback can't be null, the client of this interactor " +
                            "needs to get the response in the callback");
        }
        // store navigationChange, it is used in the run() function of this class
        this.navigationOperation = navigationOperation;
        // store the callback object, used in the run() function of this class
        this.callback = callback;
        // Executor calls the run() function of this Interactor, probably in a background thread
        this.executor.run(this);
    }

    @Override
    public void run() {
        // do the work (using the navigator) which will be executed in the background thread,
        //  and the work depends on the navigationChange,
        //  and then call callback.onSuccess() resp. onError()
        if(navigationOperation instanceof NavigationOperationDirectory) {
            boolean error = false;
            switch (((NavigationOperationDirectory) navigationOperation).direction) {
                case CHILD:
                    error = !treeNavigator.toFocusedChild(); // TODO: is this really an error?
                    break;
                case PARENT:
                    error = !treeNavigator.toParent(); // TODO: is this really an error?
                    break;
                case NEUTRAL:
                    // no change
                    break;
            }
            // we execute the callback on the mainThread
            final boolean finalError = error;
            mainThread.post(new Runnable() {
                @Override public void run() {
                    if(finalError) {
                        callback.onError();
                    } else {
                        callback.onSuccess(
                                navigationOperation, // if this is executed asynchronously, this navigationOperation may have changed in the meantime, leading to the wrong callback!!
                                new ViewData(
                                        treeNavigator.getParentItems(),
                                        treeNavigator.getItemList(),
                                        treeNavigator.getContentOfFocusedChild(),
                                        treeNavigator.getPathOfFocusedChild()
                                )
                        );
                    }
                }
            });
        } else if(navigationOperation instanceof NavigationOperationFocus) {
            boolean error = !treeNavigator.setFocus(((NavigationOperationFocus) navigationOperation).index);
            // we execute the callback on the mainThread
            final boolean finalError = error;
            mainThread.post(new Runnable() {
                @Override public void run() {
                    // TODO: Note: we cannot access navigationOperation here, because it has changed in the meantime!!!!
                    assert navigationOperation instanceof NavigationOperationFocus;
                    if(finalError) {
                        callback.onError();
                    } else {
                        callback.onSuccess(
                                navigationOperation,
                                new ViewData(
                                        null,
                                        null,
                                        treeNavigator.getContentOfFocusedChild(),
                                        treeNavigator.getPathOfFocusedChild()
                                )
                        );
                    }
                }
            });
        }
        else if(navigationOperation instanceof NavigationOperationEnter) {
            ListItem focusedListItem = treeNavigator.getItemList().getFocusedItem();
            if(focusedListItem.type == ListItem.Type.IMAGE) {
                // TODO: it would be nice to have an argument in the NavigationOperationOpen
                //      object which specifies which images we want to see, e.g. only the current folder,
                //      or all images in all subfolders etc.
                FileNode currentFileNode = (FileNode) treeNavigator.getFocusedNodeData();
                FileListWithIndex fileListWithIndex = null;
                ArrayList<String> fileList = new ArrayList<>();
                int currentImageIndex = 0;
                int i = 0;
                for (FileNode fileNode : treeNavigator.getImageChildren()) {
                    fileList.add(fileNode.getFile().getAbsolutePath());
                    if (currentFileNode.getFile() == fileNode.getFile()) {
                        currentImageIndex = i;
                    }
                    i++;
                }
                fileListWithIndex = new FileListWithIndex(fileList, currentImageIndex);
                final FileListWithIndex finalFileListWithIndex = fileListWithIndex;
                mainThread.post(new Runnable() {
                    @Override public void run() {
                        callback.onSuccess(navigationOperation, finalFileListWithIndex);
                    }
                });
            } else if (focusedListItem.type == ListItem.Type.DIRECTORY) {
                // We reload the content of the focused node
                treeNavigator.resyncFocusedNode();
                mainThread.post(new Runnable() {
                    @Override public void run() {
                        // Note: we cannot access navigationOperation here, because it has changed in the meantime!!!!
                        callback.onSuccess(
                                navigationOperation,
                                new ViewData(
                                        null,
                                        null,
                                        treeNavigator.getContentOfFocusedChild(),
                                        treeNavigator.getPathOfFocusedChild()
                                )
                        );
                    }
                });

            } else if(focusedListItem.type == ListItem.Type.ACTION) {
                ActionNode actionNode = (ActionNode) treeNavigator.getFocusedNodeData();
                mainThread.post(new Runnable() {
                    @Override public void run() {
                        callback.onSuccess(navigationOperation, actionNode.action);
                    }
                });
            } else {
                mainThread.post(new Runnable() {
                    @Override public void run() {
                        callback.onError();
                    }
                });
            }
        }
    }
}
