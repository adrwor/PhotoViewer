package apps.aw.simplephotos.java.presenters.fullimage;

import apps.aw.simplephotos.java.treenavigator.FileNode;

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
        FileNode img = null;
        view.setFullImage(img);
    }

    @Override
    public void fullImagePrevious() {
        //TODO: get appropriate imageFile, tell view to load it
        FileNode img = null;
        view.setFullImage(img);
    }
}
