package apps.aw.simplephotos.ui.main.presenter;

import apps.aw.simplephotos.ui.main.MainContract;


public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;

    /**
     * Constructor
     */
    public MainPresenter(MainContract.View view) {
        this.view = view;
    }


    //implemented Contract interface methods-------------------------------------------------------
    @Override
    public void start() {
        //TODO: subscribe to events of the view (Why? How?)
        view.showFileBrowser();
    }

    @Override
    public void stop() {
        //TODO: unsubscribe of events (release dependencies)
    }

}
