package apps.aw.simplephotos.java.interactors;

import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.Item;
import apps.aw.simplephotos.java.ViewData;
import apps.aw.simplephotos.java.treenavigator.TreeNavigator;
import apps.aw.simplephotos.java.executor.Executor;
import apps.aw.simplephotos.java.executor.MainThread;

public class NavigationInteractor implements Interactor, Navigation {

    NavigationOperation navigationOperation;
    private Callback callback;
    private final TreeNavigator treeNavigator;
    private final Executor executor;
    private final MainThread mainThread;

    public NavigationInteractor(TreeNavigator treeNavigator, Executor executor, MainThread mainThread) {
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
        boolean error = false;
        if(navigationOperation instanceof NavigationOperationDirectory) {
            switch (((NavigationOperationDirectory) navigationOperation).direction) {
                case DOWN:
                    error = !treeNavigator.toFocusedChild(); // TODO: is this really an error?
                    break;
                case UP:
                    error = !treeNavigator.toParent(); // TODO: is this really an error?
                    break;
            }
        } else if(navigationOperation instanceof NavigationOperationFocus) {
            error = !treeNavigator.setFocus(((NavigationOperationFocus) navigationOperation).index);
            // TODO: call callback.onSuccess() but with viewData where only path and
            //  contentOfFocusedChildNode is set, the rest is null (because it did not get updated)
        } else if(navigationOperation instanceof NavigationOperationOpen) {
            Item item = treeNavigator.getContentOfFocusedChild();
            if(!(item instanceof Image)) {
                error = true;
            }
        }

        // we execute the callback on the mainThread
        final boolean finalError = error;
        mainThread.post(new Runnable() {
            @Override public void run() {
                if(finalError) {
                    callback.onError();
                } else {
                    callback.onSuccess(
                        // TODO: support partial updates of the viewData?
                        new ViewData(
                            treeNavigator.getParentItems(),
                            treeNavigator.getItems(),
                            treeNavigator.getContentOfFocusedChild(),
                            treeNavigator.getPathOfFocusedChild()
                        )
                    );
                }
            }
        });
    }
}
