package apps.aw.simplephotos.presentation_layer.main;

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

    }

    /**
     * Implemented by the Presenter.
     */
    interface Presenter {
        void start();
        void stop();
        void clickedMenuItem();
        void clickedShortCut();

        void up();
        void down();
        void left();
        void right();
        void ok();


    }

}
