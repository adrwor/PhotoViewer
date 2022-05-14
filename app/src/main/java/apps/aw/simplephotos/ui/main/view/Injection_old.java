package apps.aw.simplephotos.ui.main.view;


import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;

import apps.aw.simplephotos.domain.navigator.cachednavigator.NodeTreeNavigator;

/**
 * Enables injection of production implementations for
 * TasksDataSource at compile time.
 */
public class Injection_old {

    //TODO: is this ok like this? (having a static reference to the interactor?)
    //  or better have this static reference in another class, and here just call getInstance()?

    private static NodeTreeNavigator navigator;

    public static NodeTreeNavigator provideNavigator(@NonNull Context context) {
        // create an instance of the interactor, which operates on the database
        if(navigator == null) {
//            File defaultPath = context.getFilesDir();
            File defaultPath = Environment.getExternalStorageDirectory();
            navigator = new NodeTreeNavigator(); // should we enforce that we have at least one subRoot?
            navigator.addSubRoot(Environment.getExternalStorageDirectory()); // why does this still work SDK 31?
            // TODO: add other storage locations as subRoots, e.g. sd-card, usb, ...

        }
        //return the interactor
        return navigator;
    }
}

