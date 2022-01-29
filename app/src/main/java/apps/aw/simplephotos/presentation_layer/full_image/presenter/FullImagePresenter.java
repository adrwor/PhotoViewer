package apps.aw.simplephotos.presentation_layer.full_image.presenter;

import apps.aw.simplephotos.domain_layer.model.FileModel;
import apps.aw.simplephotos.presentation_layer.full_image.FullImageContract;

public class FullImagePresenter implements FullImageContract.Presenter {

    //reference to view (-interface)
    FullImageContract.View view;   //TODO: use weak reference? Why?

    /**
     * Constructor
     * @param view
     */
    public FullImagePresenter(FullImageContract.View view) {
        this.view = view; //get view    //use weak reference?
    }

    //implemented contract interface methods-------------------------------------------------------

    @Override
    public void start() {
        //alternative to weak-reference approach
    }

    @Override
    public void stop() {
        //alternative to weak-reference approach
    }

    @Override
    public void fullImageNext() {
        //TODO: get appropriate imageFile, tell view to load it
        FileModel img = null;
        view.setFullImage(img);
    }

    @Override
    public void fullImagePrevious() {
        //TODO: get appropriate imageFile, tell view to load it
        FileModel img = null;
        view.setFullImage(img);
    }
}
