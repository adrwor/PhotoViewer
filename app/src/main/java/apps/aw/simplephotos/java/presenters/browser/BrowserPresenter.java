package apps.aw.simplephotos.java.presenters.browser;

import android.util.Log;

import apps.aw.simplephotos.java.interactors.Navigation;
import apps.aw.simplephotos.java.interactors.Modification;
import apps.aw.simplephotos.java.interactors.NavigationOperation;
import apps.aw.simplephotos.java.interactors.NavigationOperationDirectory;
import apps.aw.simplephotos.java.interactors.NavigationOperationFocus;
import apps.aw.simplephotos.java.ViewData;

public class BrowserPresenter implements BrowserContract.Presenter {

    private static final String TAG = "BrowserPresenter";

    private BrowserContract.View view;

    private final Navigation navigation;
    private final Modification modification;


    /**
     * Constructor
     */
    public BrowserPresenter(
            BrowserContract.View view,
            Navigation navigation,
            Modification modification
    ) {
        this.view = view;               // keep a reference to the view
        this.view.setPresenter(this);   // give this presenter instance to the view
        this.navigation = navigation; // keep a reference to the navigation
        this.modification = modification; // keep a reference to the initialization
    }

    @Override
    public void initialize() {
        // TODO: subscribe to events of the view
        // TODO: show loading indicator
        modification.initialize();

        navigation.execute(
                new NavigationOperation(),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.item);
                        view.setPath(viewData.path);
                    }

                    @Override
                    public void onError() {
                        Log.i(TAG, "start: error!!!!!!!!!");
                        // TODO: show error
                    }
                }
        );
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        view = null;    //to prevent calling the view when it is destroyed
//        interactor = null;
        //TODO: unsubscribe of events (release dependencies)
    }

    @Override
    public void focus(int index) {
        // TODO: show loading indicator
        navigation.execute(
                new NavigationOperationFocus(index),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        // Important: we must not update column1 and column2,
                        // because the view may not be updated yet
                        Log.i(TAG, "focus(" + index + ") onSuccess()");
                        view.setColumn3(viewData.item);
                        view.setPath(viewData.path);
                    }

                    @Override
                    public void onError() {
                        Log.i(TAG, "focus: error!!!!!!!!!");
                        // TODO: show error
                    }
                }
        );
    }

    @Override
    public void toParent() {
        // TODO: show loading indicator
        navigation.execute(
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.UP),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        Log.i(TAG, "toParent() onSuccess()");
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.item);
                        view.setPath(viewData.path);
                    }

                    @Override
                    public void onError() {
                        Log.i(TAG, "toParent: error!!!!!!!!!");
                        // TODO: show error
                    }
                }
        );
    }

    @Override
    public void toChild() {
        // TODO: show loading indicator
        navigation.execute(
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.DOWN),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        Log.i(TAG, "toChild() onSuccess()");
                        Log.i(TAG, "Column 1:");
                        for (String s: viewData.column1.getFileList()) {
                            Log.i(TAG, "file: " + s);
                        }
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.item);
                        view.setPath(viewData.path);
                    }

                    @Override
                    public void onError() {
                        Log.i(TAG, "toChild: error!!!!!!!!!");
                        // TODO: show error
                    }
                }
        );
    }
}
