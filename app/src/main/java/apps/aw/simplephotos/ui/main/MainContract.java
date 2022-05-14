package apps.aw.simplephotos.ui.main;

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
        void showFullImageView();

        /**
         * Tells view to show Preferences View
         */
        void showPreferencesView();

        void showFileBrowser();

    }

    /**
     * Implemented by the Presenter.
     */
    interface Presenter {
        void start();
        void stop();
    }

}
