package apps.aw.simplephotos.android.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import apps.aw.simplephotos.android.AppContainer;
import apps.aw.simplephotos.android.MyApplication;
import apps.aw.simplephotos.R;
import apps.aw.simplephotos.android.ui.full_image.FullImageFragment;
import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.presenters.browser.BrowserPresenter;
import apps.aw.simplephotos.java.presenters.fullimage.FullImagePresenter;
import apps.aw.simplephotos.java.presenters.main.MainContract;
import apps.aw.simplephotos.java.presenters.main.MainPresenter;
import apps.aw.simplephotos.android.ui.ActivityTransactions;
import apps.aw.simplephotos.android.ui.browser.BrowserFragment;


public class MainActivity extends AppCompatActivity implements
        MainContract.View,
        BrowserFragment.OnFragmentInteractionListener
{

    private static final String TAG = "MainActivity";

    private MainPresenter mainPresenter;    //reference to the presenter of this activity

    private AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        setContentView(R.layout.activity_main);

        appContainer = ((MyApplication) getApplication()).appContainer;

        //Create a MainPresenter, give it this view
        mainPresenter = new MainPresenter(
                this,
                appContainer.navigation
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.initialize();
    }

    //TODO: implement this method so that it calls a method from ActivityUtils (e.g. replaceFragmentInActivity(...))
    @Override
    public void showFullImageView(Image image) {
        // TODO: HIER WEITER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!¨

        // TODO: maybe don't use fragments, but just display the full image (and also the browser)
        //  here in the MainActivity? (through showing/hiding views)
        Log.i(TAG, "showFullImageView()");

    }

    @Override
    public void showPreferencesView() {
        //TODO: open preferences (acitivity or fragment)
    }

    @Override
    public void showFileBrowser() {
        BrowserFragment browserFragment = findBrowserFragment();
        if (browserFragment == null) {
            browserFragment = BrowserFragment.newInstance("haha", "hoho");
            new BrowserPresenter(
                    browserFragment,
                    appContainer.navigation,
                    appContainer.modification
            );
            ActivityTransactions.addFragmentToActivity(
                    getSupportFragmentManager(),
                    browserFragment,
                    R.id.fragmentContainerView
            );
        }
    }

    private BrowserFragment findBrowserFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if(fragment instanceof BrowserFragment) {
            return (BrowserFragment) fragment;
        }
        return null;
    }

    private FullImageFragment findFullImageFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if(fragment instanceof FullImageFragment) {
            return (FullImageFragment) fragment;
        }
        return null;
    }

    // BrowserFragment.FragmentInteractionListener interface methods
    @Override
    public void openFullImage(int position) {
        mainPresenter.openFullImage();
    }
}
