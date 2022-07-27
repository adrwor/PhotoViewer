package apps.aw.simplephotos.android.ui.browser;

import static android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;

import java.io.File;
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
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                },
                1
        );

        boolean media_mounted_read_only = (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean media_mounted = (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED);

        Log.i(TAG, "onCreate: media_mouted_read_only: " + media_mounted_read_only);
        Log.i(TAG, "onCreate: media_mounted: " + media_mounted);

        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY && 
                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            Log.i(TAG, "onCreate: externalStorageState is ok");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void openFullImage(ArrayList<String> list, int current) {
        ActivityNavigation.openFullImageActivity(this, list, current);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
//        super.onActivityResult(requestCode, resultCode, resultData);
//        Log.i(TAG, "onActivityResult()");
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//            // The result data contains a URI for the document or directory that
//            // the user selected.
//            Uri uri = null;
//            if (resultData != null) {
//                uri = resultData.getData();
//                // Perform operations on the document using its URI.
//                assert uri != null;
//                String uriPath = uri.getPath();
//                Log.i(TAG, "uriPath: " + uriPath);
//                String path = uriPath.split(":")[0];
//                Log.i(TAG, "path: " + path);
//            }
//        }
//    }


    @Override
    public AppContainer getAppContainer() {
        return appContainer;
    }
}
