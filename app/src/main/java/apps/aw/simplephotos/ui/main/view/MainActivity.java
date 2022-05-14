package apps.aw.simplephotos.ui.main.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import apps.aw.simplephotos.AppContainer;
import apps.aw.simplephotos.MyApplication;
import apps.aw.simplephotos.ui.browser.presenter.BrowserPresenter;
import apps.aw.simplephotos.ui.browser.view.BrowserFragment;
import apps.aw.simplephotos.ui.full_image.view.FullImageFragment;
import apps.aw.simplephotos.ui.main.MainContract;
import apps.aw.simplephotos.R;
import apps.aw.simplephotos.ui.main.presenter.MainPresenter;
import apps.aw.simplephotos.framework_utils.ActivityTransactions;


public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

    private MainPresenter mainPresenter;    //reference to the presenter of this activity

    private AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        setContentView(R.layout.activity_main);

        //Create a MainPresenter, give it this view
        mainPresenter = new MainPresenter(this);
        appContainer = ((MyApplication) getApplication()).appContainer;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.start();
    }

    //TODO: change this method so that it calls a method from ActivityUtils (e.g. replaceFragmentInActivity(...))
    @Override
    public void showFullImageView() {
        FragmentManager fm = getSupportFragmentManager();
        //Check if fragment already exists, only make fragment if it does not exist
        if(fm.findFragmentByTag("FullImageView") == null) {
            //create new fragment
            FullImageFragment fragment = new FullImageFragment();
            //begin fragment transaction
            FragmentTransaction transaction = fm.beginTransaction();
            //optional: give a bundle to the fragment
            //Bundle bundle = new Bundle();
            //bundle.putString("key", "hello world");
            //fragment.setArguments(bundle);
            //add fragment to root view in main activity layout
            transaction.add(R.id.root, fragment, "FullImageView");
            //optional: set a transition
            //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //add the transition to the backstack (will be reversed)
            transaction.addToBackStack(null);
            //commit the transaction
            transaction.commit();
        }
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
                    appContainer.initialization
            );
            ActivityTransactions.addFragmentToActivity(
                    getSupportFragmentManager(),
                    browserFragment,
                    R.id.fragmentContainerView
            );
        }
    }

    private BrowserFragment findBrowserFragment() {
        return (BrowserFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
    }
}
