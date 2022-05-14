package apps.aw.simplephotos.ui;

public interface BasePresenter {
    void start();
    void stop();


    //TODO: replace the start() and stop() above with these new functions:
    /**
     * Called when the presenter is initialized, this method represents the start of the presenter
     * lifecycle.
     */
    void initialize();

    /**
     * Called when the presenter is resumed. After the initialization and when the presenter comes
     * from a pause state.
     */
    void resume();

    /**
     * Called when the presenter is paused.
     */
    void pause();
}

