package apps.aw.simplephotos.java.presenters.fullimage;

import apps.aw.simplephotos.java.treenavigator.FileNode;

public interface FullImageContract {

    /**
     * Implemented by the fullimage fragment.
     */
    interface View {

        void setFullImage(FileNode img);
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