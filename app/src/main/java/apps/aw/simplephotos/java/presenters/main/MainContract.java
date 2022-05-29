package apps.aw.simplephotos.java.presenters.main;

import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.presenters.BasePresenter;

/**
 * This interface describes the contract between the presenter and the view (activity).
 */
public interface MainContract {

    /**
     * Implemented by the View.
     */
    interface View {

        /**
         * Tells view to show the FullImage View
         */
        void showFullImageView(Image image);

        /**
         * Tells view to show Preferences View
         */
        void showPreferencesView();

        void showFileBrowser();

    }

    /**
     * Implemented by the Presenter.
     */
    interface Presenter extends BasePresenter {
        void openFullImage();

    }

}
