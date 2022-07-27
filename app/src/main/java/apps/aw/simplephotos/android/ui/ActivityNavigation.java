package apps.aw.simplephotos.android.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.util.ArrayList;

import apps.aw.simplephotos.android.ui.full_image.FullImageActivity;
import apps.aw.simplephotos.java.Image;

public class ActivityNavigation {

    private static final String TAG = "ActivityNavigation";

    public static void openFullImageActivity(Context context, ArrayList<String> list, int current) {
        Log.i(TAG, "openFullImageActivity");
        Intent intent = new Intent(context.getApplicationContext(), FullImageActivity.class);
        intent.putExtra("stringArrayList", list);
        intent.putExtra("current", current);
        context.startActivity(intent);
    }

    public static void openSystemFilePicker(Context context) {
        // Choose a directory using the system's file picker.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        ActivityCompat.startActivityForResult((Activity) context, intent, 0, null);
    }
}
