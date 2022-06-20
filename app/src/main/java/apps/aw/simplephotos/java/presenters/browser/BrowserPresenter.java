package apps.aw.simplephotos.java.presenters.browser;

import android.util.Log;

import apps.aw.simplephotos.java.FileListWithIndex;
import apps.aw.simplephotos.java.interactors.navigation.Navigation;
import apps.aw.simplephotos.java.interactors.Modification;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationDirectory;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationFocus;
import apps.aw.simplephotos.java.ViewData;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationOpen;

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
                    public void onSuccess(Object object) {
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
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
                    public void onSuccess(Object object) {
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
                        assert(viewData.column1 == null);
                        assert(viewData.column2 == null);
                        // Important: we must not update column1 and column2,
                        // because the view may not be updated yet
                        // Also: they are null in this viewData object
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
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.PARENT),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
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
                new NavigationOperationDirectory(NavigationOperationDirectory.Direction.CHILD),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        assert(object instanceof ViewData);
                        ViewData viewData = (ViewData)object;
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

    @Override
    public void openFullImage(int index) {
        Log.i(TAG, "openFullImage()");
        navigation.execute(
                // TODO: provide a special sub-class which specifies that an image should be opened
                //  the navigation then decides if it is successful or not
                new NavigationOperationOpen(index),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        assert(object instanceof FileListWithIndex);
                        FileListWithIndex fileListWithIndex = (FileListWithIndex)object;
                        // TODO: how do we calculate the index of the current image?
                        //  this could be difficult, because there can be folders and other non-images
                        //  in column2, which we do not want to have in the list.
                        //  Possible solution: in the NavigationOoperationOpen, find all images,
                        //  recalculate the index of the current image, and return it
                        //  (instead of just re-using the ViewData object for this purpose)
                        view.showFullImageView(fileListWithIndex.list, fileListWithIndex.index);

                    }
                    @Override
                    public void onError() { }
                }
        );
    }
}
