package apps.aw.simplephotos;


import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Enables injection of production implementations for
 * TasksDataSource at compile time.
 */
public class Injection {

    //TODO: is this ok like this? (having a static reference to the interactor?)
    //  or better have this static reference in another class, and here just call getInstance()?

    private static Interactor interactor;

    public static Interactor provideInteractor(@NonNull Context context) {
        // create an instance of the interactor, which operates on the database
        if(interactor == null) {
            interactor = new InteractorImpl();
        }
        //return the interactor
        return interactor;
    }
}

