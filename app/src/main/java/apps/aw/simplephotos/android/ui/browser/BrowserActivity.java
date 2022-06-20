package apps.aw.simplephotos.android.ui.browser;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.android.AppContainer;
import apps.aw.simplephotos.android.MyApplication;
import apps.aw.simplephotos.android.ui.ActivityNavigation;

public class BrowserActivity
        extends AppCompatActivity
        implements BrowserFragment.OnFragmentInteractionListener
{

    private static final String TAG = "MainActivity";

    private AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        setContentView(R.layout.activity_main);
        appContainer = ((MyApplication) getApplication()).appContainer;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void openFullImage(ArrayList<String> list, int current) {
        ActivityNavigation.openFullImageActivity(this, list, current);
    }

    @Override
    public AppContainer getAppContainer() {
        return appContainer;
    }
}
