package apps.aw.simplephotos.presentation_layer.browser.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import apps.aw.simplephotos.presentation_layer.browser.BrowserContract;
import apps.aw.simplephotos.Interactor;
import apps.aw.simplephotos.presentation_layer.browser.utils.DisplayType;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileContentView;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;

public class BrowserPresenter implements BrowserContract.Presenter {

    private BrowserContract.View view;
    private Interactor interactor;

    /**
     * Constructor
     */
    public BrowserPresenter(
            @NonNull BrowserContract.View view,
            @NonNull Interactor interactor
    ) {
        this.view = view;               // keep a reference to the view
        this.view.setPresenter(this);   // give our presenter instance to the view

        this.interactor = interactor;   // keep a reference to the interactor
    }

    @Override
    public void start() {
        //TODO: subscribe to events of the view
        setColumns();
    }

    /*@Override
    public void stop() {
        view = null;    //to prevent calling the view when it is destroyed
        //TODO: unsubscribe of events (release dependencies)
    }*/

    @Override
    public void focus(int index) {
//        Log.i("BrowserPresenter", "focus(" + position + ")");
        interactor.focusChild(index);
        updateFocusColumn2And3(index);
    }

    @Override
    public void focusNextChild() {
        interactor.focusNextChild();
        updateFocusColumn2And3(interactor.getFocusedChildIndex());
    }

    @Override
    public void focusPreviousChild() {
        interactor.focusPreviousChild();
        updateFocusColumn2And3(interactor.getFocusedChildIndex());
    }


    @Override
    public void toParent() {
        interactor.toParent();
        setColumns();
        view.setPath(interactor.getCurrentPath());
    }

    @Override
    public void toFocusedChild() {
        interactor.toFocusedChild();
        setColumns();
        view.setPath(interactor.getCurrentPath());
    }


    @Override
    public void openFocusedChild() {
        //TODO: open full_image

    }

    private void setColumns() {
        // TODO: do the loading asynchronously, and display a loading indicator in place of the content
        String path = interactor.getCurrentPath();
        FileList column1 = interactor.getParentSubFiles();
        FileList column2 = interactor.getSubFiles();
        FileContentView column3 = interactor.getContentOfFocusedChild();

        // TODO: these functions should be called in callBack methods of this presenter, called by the interactor
        view.setPath(interactor.getCurrentPath());
        view.setColumn1(interactor.getParentSubFiles());
        view.setColumn2(interactor.getSubFiles());
        view.setColumn3(interactor.getContentOfFocusedChild());
    }

    private void updateFocusColumn2And3(int index) {
        view.setColumn2Focus(index);
        view.setColumn3(interactor.getContentOfFocusedChild());
        FileContentView column3Content = interactor.getContentOfFocusedChild();
        if(column3Content != null && column3Content.getDisplayType() == DisplayType.FILELIST) {
            view.setColumn3Focus(column3Content.getFileList().getFocus());
        }
        view.setPath(interactor.getCurrentPath());
    }

}
