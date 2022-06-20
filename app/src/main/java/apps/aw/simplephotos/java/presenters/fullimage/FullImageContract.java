package apps.aw.simplephotos.java.presenters.fullimage;

import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.presenters.BasePresenter;
import apps.aw.simplephotos.java.presenters.BaseView;
import apps.aw.simplephotos.java.treenavigator.FileNode;

public interface FullImageContract {

    /**
     * Implemented by the fullimage fragment.
     */
    interface View extends BaseView<Presenter> {

        void setFullImage(Image img);
        void setEndOfListImage(Object obj); //TODO: change argument to contain some information? (not really necessary)

    }

    /**
     * Implemented by the fullimage Presenter.
     */
    interface Presenter extends BasePresenter {
//        void start();
//        void stop();

        void fullImageNext();
        void fullImagePrevious();

    }
}
