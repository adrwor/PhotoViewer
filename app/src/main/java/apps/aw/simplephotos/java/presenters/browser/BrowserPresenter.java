package apps.aw.simplephotos.java.presenters.browser;

import android.util.Log;

import apps.aw.simplephotos.java.FileListWithIndex;
import apps.aw.simplephotos.java.interactors.shared.Navigation;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperation;
import apps.aw.simplephotos.java.interactors.tree.Modification;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationDirectory;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationFocus;
import apps.aw.simplephotos.java.ViewData;
import apps.aw.simplephotos.java.interactors.shared.NavigationOperationEnter;

public class BrowserPresenter implements BrowserContract.Presenter {

    private static final String TAG = "BrowserPresenter";

    // The view for this presenter
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
        this.navigation = navigation; // keep a reference to the navigation
        this.modification = modification; // keep a reference to the initialization
    }

    @Override
    public void initialize() {
        // TODO: subscribe to events of the view
        // TODO: show loading indicator
        modification.initialize();

        navigation.execute(
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.NEUTRAL),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(NavigationOperation navigationOperation, Object object) {
                        assert navigationOperation instanceof NavigationOperationDirectory;
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.columnViewData);
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
                    public void onSuccess(NavigationOperation navigationOperation, Object object) {
                        assert navigationOperation instanceof NavigationOperationFocus;
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
                        assert(viewData.column1 == null);
                        assert(viewData.column2 == null);
                        // Important: we must not update column1 and column2,
                        // because the view may not be updated yet
                        // Also: they are null in this viewData object
                        Log.i(TAG, "focus(" + index + ") onSuccess()");
                        view.setColumn3(viewData.columnViewData);
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
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.PARENT),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(NavigationOperation navigationOperation, Object object) {
                        assert navigationOperation instanceof NavigationOperationDirectory;
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
                        Log.i(TAG, "toParent() onSuccess()");
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.columnViewData);
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
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.CHILD),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(NavigationOperation navigationOperation, Object object) {
                        assert navigationOperation instanceof NavigationOperationDirectory;
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
                        Log.i(TAG, "toChild() onSuccess()");
                        Log.i(TAG, "Column 1:");
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.columnViewData);
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
    public void enter() {
        Log.i(TAG, "enter()");
        navigation.execute(
                // TODO: provide a special sub-class which specifies that an image should be opened
                //  the navigation then decides if it is successful or not
                new NavigationOperationEnter(),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(NavigationOperation navigationOperation, Object object) {
                        assert navigationOperation instanceof NavigationOperationEnter;
                        if(object instanceof FileListWithIndex) { // TODO:  create explicit types to distinguish between these callbacks
                            FileListWithIndex fileListWithIndex = (FileListWithIndex)object;
                            view.showFullImageView(fileListWithIndex.list, fileListWithIndex.index);
                        } else if (object instanceof Integer) { // TODO:  create explicit types to distinguish between these callbacks
                            view.openSystemFilePicker();
                        } else if (object instanceof ViewData) { // user wanted to sync the current file
                            ViewData viewData = (ViewData)object;
                            assert(viewData.column1 == null);
                            assert(viewData.column2 == null);
                            // Important: we must not update column1 and column2,
                            // because the view may not be updated yet
                            // Also: they are null in this viewData object
                            view.setColumn3(viewData.columnViewData);
                            view.setPath(viewData.path);
                        }
                    }
                    @Override
                    public void onError() { }
                }
        );
    }

    @Override
    public void addSubRoot(String path) {
        Log.i(TAG, "addSubRoot: " + path);
        modification.addSubRoot(path);
        navigation.execute(
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.NEUTRAL),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(NavigationOperation navigationOperation, Object object) {
                        assert navigationOperation instanceof NavigationOperationDirectory;
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
                        view.setColumn1(viewData.column1);
                        view.setColumn2(viewData.column2);
                        view.setColumn3(viewData.columnViewData);
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
}
