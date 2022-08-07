package apps.aw.simplephotos.java.interactors.linear;

import apps.aw.simplephotos.java.executor.Executor;
import apps.aw.simplephotos.java.executor.MainThread;
import apps.aw.simplephotos.java.interactors.Interactor;
import apps.aw.simplephotos.java.interactors.shared.Navigation;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperation;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationDirection;
import apps.aw.simplephotos.java.linearnavigator.LinearNavigator;

public class LinearNavigationInteractor implements Interactor, Navigation {

    NavigationOperation navigationOperation;
    private Callback callback;
    private final LinearNavigator linearNavigator;
    private final Executor executor;
    private final MainThread mainThread;

    public LinearNavigationInteractor(LinearNavigator linearNavigator, Executor executor, MainThread mainThread) {
        this.linearNavigator = linearNavigator;
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
        //  and the work depends on the navigationOperation,
        //  and then call callback.onSuccess() resp. onError()
        if(navigationOperation instanceof NavigationOperationDirection) {
            boolean error = false;
            switch (((NavigationOperationDirection) navigationOperation).direction) {
                case PREVIOUS:
                    error = !linearNavigator.previous();
                    break;
                case NEXT:
                    error = !linearNavigator.next();
                    break;
            }
            // we execute the callback on the mainThread
            final boolean finalError = error;
            mainThread.post(new Runnable() {
                @Override public void run() {
                    if(finalError) {
                        callback.onError();
                    } else {
                        callback.onSuccess(navigationOperation, linearNavigator.getImage());
                    }
                }
            });
        }
    }
}
