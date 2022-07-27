package apps.aw.simplephotos.android.ui.full_image;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.android.executor.MainThreadImpl;
import apps.aw.simplephotos.android.executor.ThreadExecutor;
import apps.aw.simplephotos.java.interactors.linear.LinearNavigationInteractor;
import apps.aw.simplephotos.java.interactors.shared.Navigation;
import apps.aw.simplephotos.java.linearnavigator.LinearNavigatorImpl;

public class FullImageActivity
        extends AppCompatActivity
        implements FullImageFragment.OnFragmentInteractionListener {

    private static final String TAG = "FullImageActivity";

    LinearNavigationInteractor linearNavigationInteractor;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra("stringArrayList");
        int currentIndex = intent.getIntExtra("current", 0);

        linearNavigationInteractor = new LinearNavigationInteractor(
                new LinearNavigatorImpl(list, currentIndex),
                new ThreadExecutor(),
                new MainThreadImpl()
        );

        fragment = getSupportFragmentManager().findFragmentById(R.id.full_image_fragment);
    }

    // TODO: is this the right place to catch the keyEvents? Or better in the View?
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        switch (Dpad.getDirectionPressed(event)) {
//            case Dpad.LEFT:
//                // TODO: communicate this keypress to the fragment
//                Log.i("NavigationItemView", "LEFT");
//                return false;
//            case Dpad.RIGHT:
//                // TODO: communicate this keypress to the fragment
//                Log.i("NavigationItemView", "RIGHT");
//                return false;
//            case Dpad.UP:
//                // TODO: communicate this keypress to the fragment
//                Log.i("NavigationItemView", "UP");
//                return false;
//            case Dpad.DOWN:
//                // TODO: communicate this keypress to the fragment
//                Log.i("NavigationItemView", "DOWN");
//                return false;
//            case Dpad.CENTER:
//                // TODO: communicate this keypress to the fragment
//                Log.i("NavigationItemView", "CENTER");
//                return false;
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }

    @Override
    public Navigation getNavigation() {
        return linearNavigationInteractor;
    }
}