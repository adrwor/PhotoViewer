package apps.aw.simplephotos.presentation_layer.main.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import apps.aw.simplephotos.Injection;
import apps.aw.simplephotos.framework_utils.Dpad;
import apps.aw.simplephotos.presentation_layer.browser.presenter.BrowserPresenter;
import apps.aw.simplephotos.presentation_layer.browser.view.BrowserFragment;
import apps.aw.simplephotos.presentation_layer.full_image.view.FullImageFragment;
import apps.aw.simplephotos.presentation_layer.main.MainContract;
import apps.aw.simplephotos.R;
import apps.aw.simplephotos.presentation_layer.main.presenter.MainPresenter;
import apps.aw.simplephotos.framework_utils.ActivityTransactions;


public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

    private MainPresenter mainPresenter;    //reference on the presenter of this activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        setContentView(R.layout.activity_main);




        //Create a MainPresenter, give it this view and the interactor
        mainPresenter = new MainPresenter(this, Injection.provideFileSystemInteractor(getApplicationContext()));
        //Explanation:
        /*
        This Activity creates a new Interactor, gives it to the Presenter of this activity, which can then access it..
        The interactor is for communicating with the model.
         */

        //TODO: get menu item buttons, set onClickListeners (in listener, call method of mainPresenter)
        
        //Create and add the browserFragment to this activity
        BrowserFragment browserFragment = (BrowserFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (browserFragment == null) {
            browserFragment = BrowserFragment.newInstance("haha", "hoho");
            ActivityTransactions.addFragmentToActivity(getSupportFragmentManager(), browserFragment, R.id.fragmentContainerView);
        }

        //Create a BrowserPresenter, give it the browserfragment and the interactor
        new BrowserPresenter(browserFragment, Injection.provideFileSystemInteractor(getApplicationContext()));


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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("NavigationRecyclerView", "onKeyUp");
        // TODO: somehow process these events so that we can navigate in the browser fragment.
//        can we directly call methods of the browser fragment?
        return true;
    }

}
