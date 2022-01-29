package apps.aw.simplephotos.presentation_layer.full_image;

import apps.aw.simplephotos.domain_layer.model.FileModel;

public interface FullImageContract {

    /**
     * Implemented by the fullimage fragment.
     */
    interface View {

        void setFullImage(FileModel img);
        void setEndOfListImage(Object obj); //TODO: change argument to contain some information? (not really necessary)

    }

    /**
     * Implemented by the fullimage Presenter.
     */
    interface Presenter {
        void start();
        void stop();

        void fullImageNext();
        void fullImagePrevious();

    }
}
