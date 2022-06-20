package apps.aw.simplephotos.java.interactors.tree;

import java.util.ArrayList;

import apps.aw.simplephotos.java.FileListWithIndex;
import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.Item;
import apps.aw.simplephotos.java.ViewData;
import apps.aw.simplephotos.java.interactors.Interactor;
import apps.aw.simplephotos.java.interactors.navigation.Navigation;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperation;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationDirectory;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationFocus;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationOpen;
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
                    if(finalError) {
                        callback.onError();
                    } else {
                        callback.onSuccess(
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
        else if(navigationOperation instanceof NavigationOperationOpen) {
            boolean error = false;
            Item item = treeNavigator.getContentOfFocusedChild();
            FileListWithIndex fileListWithIndex = null;
            if(!(item instanceof Image)) {
                error = true;
            } else {
                // TODO: this may not be ideal, because not all items in treeNavigator.getItemList()
                //      are images, and therefore we cannot see all of them
                //  Furthermore, it would be nice to have an argument in the NavigationOperationOpen
                //      object which specifies which images we want to see, e.g. only the current folder,
                //      or all images in all subfolders etc.
                // TODO: this is only temporary!!!! (fileList contains only currently focused image!!!)
                ArrayList<String> fileList = new ArrayList<>();
                fileList.add(((Image)(treeNavigator.getContentOfFocusedChild())).getFile().getAbsolutePath());
                fileListWithIndex = new FileListWithIndex(
                        fileList,
                        0
                );
            }
            final boolean finalError = error;
            final FileListWithIndex finalFileListWithIndex = fileListWithIndex;
            mainThread.post(new Runnable() {
                @Override public void run() {
                    if(finalError) {
                        callback.onError();
                    } else {
                        callback.onSuccess(finalFileListWithIndex);
                    }
                }
            });
        }
    }
}
