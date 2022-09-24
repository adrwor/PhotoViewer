package apps.aw.photoviewer.java.presenters.fullimage;

import apps.aw.photoviewer.java.Image;
import apps.aw.photoviewer.java.presenters.BasePresenter;
import apps.aw.photoviewer.java.presenters.BaseView;

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
