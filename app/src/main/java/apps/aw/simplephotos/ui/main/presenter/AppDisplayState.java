package apps.aw.simplephotos.ui.main.presenter;

/**
 * This class is for
 */
public class AppDisplayState {

    private State state;

    public enum State {
        HOME,   //Navigation screen
        FULLIMAGE,  //Watching image in fullScreen
        SETTINGS    //Settings screen
    }

    /**
     * Constructor.
     * State is initially 'HOME'.
     */
    public AppDisplayState() {
        state = State.HOME; //initial state is 'HOME'
    }


    public void setState(State state) {
        this.state = state;
    }


    public State getState() {
        return state;
    }

}
