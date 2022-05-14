package apps.aw.simplephotos.ui.browser.presenter;

import android.util.Log;

import java.io.File;

import apps.aw.simplephotos.domain.Navigation;
import apps.aw.simplephotos.domain.interactorinterfaces.Initialization;
import apps.aw.simplephotos.domain.interactorinterfaces.NavigationChange;
import apps.aw.simplephotos.domain.interactorinterfaces.NavigationChangeDirectory;
import apps.aw.simplephotos.domain.interactorinterfaces.NavigationChangeFocus;
import apps.aw.simplephotos.ui.browser.BrowserContract;
import apps.aw.simplephotos.domain.ViewData;

public class BrowserPresenter implements BrowserContract.Presenter {

    private static final String TAG = "BrowserPresenter";

    private BrowserContract.View view;

    private Navigation navigation;
    private Initialization initialization;

    /**
     * Constructor
     */
    public BrowserPresenter(
            BrowserContract.View view,
            Navigation navigation,
            Initialization initialization
    ) {
        this.view = view;               // keep a reference to the view
        this.view.setPresenter(this);   // give this presenter instance to the view
        this.navigation = navigation; // keep a reference to the navigation
        this.initialization = initialization; // keep a reference to the initialization

    }

    @Override
    public void start() {
        // TODO: subscribe to events of the view
        // TODO: show loading indicator

        // TODO: add the correct subroot files!
        // TODO: don't give a file as argument, but a special object
        //   which contains a file, but also a name, so that we can
        //   set a custom name for each subroot (so that the internal
        //   storage is not called "0", but "internal storage")
        File defaultRoot = new File("/storage/emulated/0");
        Log.i(TAG, "addSubRoot() path: " + defaultRoot.getAbsolutePath());
        initialization.addSubRoot(defaultRoot);
        initialization.addSubRoot(new File(defaultRoot, "aefji"));

        navigation.execute(
                new NavigationChange(),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.fileContent);
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
    public void stop() {
        view = null;    //to prevent calling the view when it is destroyed
//        interactor = null;
        //TODO: unsubscribe of events (release dependencies)
    }

    @Override
    public void initialize() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void focus(int index) {
        // TODO: show loading indicator
        navigation.execute(
                new NavigationChangeFocus(index),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        // Important: we must not update column1 and column2,
                        // because the view may not be updated yet
//                        view.setColumn1(viewData.column1);
//                        view.setColumn2(viewData.column2);
                        Log.i(TAG, "focus(" + index + ") onSuccess()");
                        view.setColumn3(viewData.fileContent);
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
                new NavigationChangeDirectory(NavigationChangeDirectory.Direction.UP),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(ViewData viewData) {
                        Log.i(TAG, "toParent() onSuccess()");
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.fileContent);
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
                new NavigationChangeDirectory(NavigationChangeDirectory.Direction.DOWN),
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
                        view.setColumn3(viewData.fileContent);
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


    @Override
    public void openFocusedChild() {
        //TODO: open full_image (fragment should be replaced with other fragment)
        // -> notify view, which in turn notifies the activity?
        // -> have to write an Interactor for this
    }
}
