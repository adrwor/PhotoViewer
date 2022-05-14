package apps.aw.simplephotos.domain.interactorinterfaces;

import apps.aw.simplephotos.domain.Navigation;
import apps.aw.simplephotos.domain.ViewData;
import apps.aw.simplephotos.domain.navigator.Navigator;
import apps.aw.simplephotos.executor.Executor;
import apps.aw.simplephotos.executor.MainThread;

public class NavigationInteractor implements Interactor, Navigation {

    NavigationChange navigationChange;
    private Callback callback;
    private Navigator navigator;
    private Executor executor;
    private MainThread mainThread;

    public NavigationInteractor(Navigator navigator, Executor executor, MainThread mainThread) {
        this.navigator = navigator;
        this.executor = executor;
        this.mainThread = mainThread;
    }

    @Override
    public void execute(NavigationChange navigationChange, Callback callback) {
        // This execute function is specified by the ChangeDirectory class
        if (callback == null) {
            throw new IllegalArgumentException(
                    "Callback can't be null, the client of this interactor " +
                            "needs to get the response in the callback");
        }
        // store navigationChange, it is used in the run() function of this class
        this.navigationChange = navigationChange;
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
        if(navigationChange instanceof NavigationChangeDirectory) {
            switch (((NavigationChangeDirectory) navigationChange).direction) {
                case DOWN:
                    error = !navigator.toChild(); // TODO: is this really an error?
                    break;
                case UP:
                    error = !navigator.toParent(); // TODO: is this really an error?
                    break;
            }
        } else if(navigationChange instanceof NavigationChangeFocus) {
            error = !navigator.setFocus(((NavigationChangeFocus) navigationChange).index);
            // TODO: call callback.onSuccess() but with viewData where only path and
            //  contentOfFocusedChildNode is set, the rest is null (because it did not get updated)
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
                                    navigator.getParentSubFiles(),
                                    navigator.getCurrentSubFiles(),
                                    navigator.getContentOfFocusedChildNode(),
                                    navigator.getPath()
                            )
                    );
                }
            }
        });
    }
}
