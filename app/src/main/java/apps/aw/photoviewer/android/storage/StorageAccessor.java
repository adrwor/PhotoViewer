package apps.aw.photoviewer.android.storage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;

public class StorageAccessor {
    Context context;
    public static int REQUEST_CODE = 42;
    public StorageAccessor(Context context) {
        this.context = context;
    }

    public void openDirectory(Uri uriToLoad) {
        // Choose a directory using the system's file picker.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);


        ((Activity)context).startActivityForResult(intent, REQUEST_CODE);
    }

}