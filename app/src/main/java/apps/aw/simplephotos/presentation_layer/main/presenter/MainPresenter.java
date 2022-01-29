package apps.aw.simplephotos.presentation_layer.main.presenter;

import apps.aw.simplephotos.presentation_layer.main.MainContract;
import apps.aw.simplephotos.Interactor;


public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    Interactor interactor;

    /**
     * Constructor
     */
    public MainPresenter(MainContract.View view, Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }


    //implemented Contract interface methods-------------------------------------------------------
    @Override
    public void start() {
        //TODO: subscribe to events of the view (Why? How?)

        //view.showFileNavigationView();
    }

    @Override
    public void stop() {
        //TODO: unsubscribe of events (release dependencies)
    }

    @Override
    public void clickedMenuItem() {
        //TODO: tell view to open corresponding menu
    }

    @Override
    public void clickedShortCut() {
        //TODO: tell model that path has changed
        //TODO: tell view to show corresponding path
    }

    @Override
    public void up() {

    }

    @Override
    public void down() {

    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void ok() {

    }

}
