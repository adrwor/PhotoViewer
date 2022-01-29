package apps.aw.simplephotos.presentation_layer.browser.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import apps.aw.simplephotos.presentation_layer.browser.BrowserContract;
import apps.aw.simplephotos.Interactor;
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
    public void focus(int position) {
//        Log.i("BrewserPresenter", "focus(" + position + ")");
        interactor.focusChild(position);
        view.setColumn3(interactor.getContentOfFocusedChild());
    }


    @Override
    public void toParent() {
        interactor.toParent();
        setColumns();
    }

    @Override
    public void toFocusedChild() {
        interactor.toFocusedChild();
        setColumns();
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



}
