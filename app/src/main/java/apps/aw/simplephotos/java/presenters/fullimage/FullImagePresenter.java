package apps.aw.simplephotos.java.presenters.fullimage;

import android.provider.ContactsContract;
import android.util.Log;

import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.interactors.navigation.Navigation;
import apps.aw.simplephotos.java.interactors.navigation.NavigationOperationDirection;

public class FullImagePresenter implements FullImageContract.Presenter {

    private static final String TAG = "FullImagePresenter";

    //reference to view (-interface)
    private FullImageContract.View view;   //TODO: use weak reference? Why?

    private final Navigation nagivation;

    /**
     * Constructor
     * @param view
     */
    public FullImagePresenter(
            FullImageContract.View view,
            Navigation navigation
    ) {
        this.view = view; //get view    //use weak reference?
        this.nagivation = navigation;
    }

    //implemented contract interface methods-------------------------------------------------------

    @Override
    public void fullImageNext() {
        nagivation.execute(
                new NavigationOperationDirection(NavigationOperationDirection.Direction.NEXT),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        assert(object instanceof Image);
                        Image image = (Image)object;
                        view.setFullImage(image);
                    }

                    @Override
                    public void onError() {
                        // TODO: show error
                    }
                }
        );
    }

    @Override
    public void fullImagePrevious() {
        nagivation.execute(
                new NavigationOperationDirection(NavigationOperationDirection.Direction.PREVIOUS),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        assert(object instanceof Image);
                        Image image = (Image)object;
                        view.setFullImage(image);
                    }

                    @Override
                    public void onError() {
                        // TODO: show error
                    }
                }
        );
    }

    @Override
    public void initialize() {
        Log.i(TAG, "initialize()");
        // TODO: subscribe to events of the view
        // TODO: show loading indicator?
        nagivation.execute(
                new NavigationOperationDirection(NavigationOperationDirection.Direction.NEUTRAL),
                new Navigation.Callback() {
                    @Override
                    public void onSuccess(Object object) {
                        assert(object instanceof Image);
                        Image image = (Image)object;
                        Log.i(TAG, "initialize(): onSuccess()!");
                        Log.i(TAG, "image: " + image.getFile().toString());
                        view.setFullImage(image);
                    }

                    @Override
                    public void onError() {
                        // TODO: show error
                        Log.i(TAG, "initialize(): onError()!!!");
                    }
                }
        );
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        view = null;
    }
}
