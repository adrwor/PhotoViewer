package apps.aw.simplephotos.java.presenters.main;

import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.ViewData;
import apps.aw.simplephotos.java.interactors.Navigation;
import apps.aw.simplephotos.java.interactors.NavigationOperation;
import apps.aw.simplephotos.java.interactors.NavigationOperationOpen;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    private final Navigation navigation;

    /**
     * Constructor
     */
    public MainPresenter(
            MainContract.View view,
            Navigation navigation
    ) {
        this.view = view;
        this.navigation = navigation;
    }

    //implemented Contract interface methods-------------------------------------------------------
    @Override
    public void initialize() {
        view.showFileBrowser();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void openFullImage() {
        navigation.execute(
                // TODO: provide a special sub-class which specifies that an image should be opened
                //  the navigation then decides if it is successful or not
            new NavigationOperationOpen(),
            new Navigation.Callback() {
                @Override
                public void onSuccess(ViewData viewData) {
                    assert(viewData.item instanceof Image);
                    view.showFullImageView((Image) viewData.item);
                }
                @Override
                public void onError() { }
            }
        );
    }
}
