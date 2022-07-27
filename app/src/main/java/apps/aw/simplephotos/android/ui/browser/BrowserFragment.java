package apps.aw.simplephotos.android.ui.browser;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.VerticalGridView;

import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps.aw.simplephotos.android.AppContainer;
import apps.aw.simplephotos.android.ui.ActivityNavigation;
import apps.aw.simplephotos.java.Action;
import apps.aw.simplephotos.java.Path;
import apps.aw.simplephotos.java.presenters.browser.BrowserContract;
import apps.aw.simplephotos.R;
import apps.aw.simplephotos.java.Item;
import apps.aw.simplephotos.java.ItemList;
import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.android.ui.browser.adapter.FileListAdapter;
import apps.aw.simplephotos.android.ui.browser.adapter.FileListNavigationAdapter;
import apps.aw.simplephotos.android.ui.browser.custom_views.NavigationItemView;
import apps.aw.simplephotos.java.presenters.browser.BrowserPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowserFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This Fragment is for navigating in a file system and displaying a preview of image files.
 * It can get a specific directory from the context (e.g. shortcuts),
 * and give back the current path (e.g. so the context can display images in full-size).
 *
 */
public class BrowserFragment
        extends Fragment
        implements BrowserContract.View, NavigationItemView.NavigationKeyHandler {
    private static final String TAG = "BrowserFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //the presenter for this view
    private BrowserContract.Presenter presenter;

    private OnFragmentInteractionListener mListener;

    //the android views
    private TextView pathView;
    private VerticalGridView column1view;
    private VerticalGridView column2view;
    private VerticalGridView column3view;
    private View preview;

    //adapters
    FileListAdapter column1adapter;
    FileListNavigationAdapter column2adapter;
    FileListAdapter column3adapter;

    ActivityResultLauncher<Uri> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocumentTree(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        // The result is a URI for the document or directory that
                        // the user selected.
                        if (result != null) {
                            // Perform operations on the document using its URI.
                            String uriPath = result.getPath();
                            Log.i(TAG, "uriPath: " + uriPath);
                            String[] pathParts = uriPath.split(":")[0].split("/");
                            String name = pathParts[pathParts.length-1];

                            presenter.addSubRoot("/storage/" + name);
                            presenter.addSubRoot("/mnt/media_rw/" + name);

                            // TODO: use this somehow or something similar
                            DocumentFile.fromTreeUri(getContext(), result);
                            DocumentsContract.buildChildDocumentsUriUsingTree(result, "");

                        }
                    }
                }
        );


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FileNavigation.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowserFragment newInstance(String param1, String param2) {
        BrowserFragment fragment = new BrowserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.i("BrowserFragment", "onCreate()");
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.i("BrowserFragment", "onCreateView()");

        //Just return inflated view
        return inflater.inflate(R.layout.fragment_browser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        presenter = new BrowserPresenter(
                this,
                mListener.getAppContainer().navigation,
                mListener.getAppContainer().modification
        );

        column1adapter = new FileListAdapter(
                new ItemList(),
                getContext()
        );

        column2adapter = new FileListNavigationAdapter(
                new ItemList(),
                (position, hasFocus) -> {
                    Log.i("BrowserFragment", "RecyclerView: onItemFocusedListener called");
                    if(hasFocus) {
                        presenter.focus(position);
                    }
                },
                position -> {
                    Log.i("BrowserFragment", "RecyclerView: onItemClickListener called");
                    presenter.open(position);
                },
                this,
                getContext()
        );

        column3adapter = new FileListAdapter(
                new ItemList(),
                getContext()
        );

        column1view = view.findViewById(R.id.column_1);
        column2view = view.findViewById(R.id.column_2);
        column3view = view.findViewById(R.id.column_3);
        column1view.setHasFixedSize(true);
        column2view.setHasFixedSize(true);
        column3view.setHasFixedSize(true);
        column1view.setAdapter(column1adapter);
        column2view.setAdapter(column2adapter);
        column3view.setAdapter(column3adapter);
        column1view.setFocusable(false);
        column2view.setFocusable(false);
        column3view.setFocusable(false);
        preview = view.findViewById(R.id.preview);
        pathView = view.findViewById(R.id.path_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.initialize();
        boolean focused = column2view.requestFocus();
        Log.i(TAG, "focused: " + focused);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setColumn1(ItemList column) {
        // TODO: check if column content exists, else display loading indicator
        Log.i(TAG, "setFileListSelection for column 1");
        column1adapter.setFileListSelection(column);
//        setColumn1Focus(column.getFocus()); // TODO: is this necessary? (focus is already contained in the fileList object?)
    }

    private void setColumn1Focus(int index) {
        column1view.scrollToPosition(index);
        column1adapter.setFocusedItem(index);
    }

    @Override
    public void setColumn2(ItemList column) {
        // TODO: check if column content exists, else display loading indicator
        if(column != null) {
            column2adapter.setFileListSelection(column);
            setColumn2Focus(column.getFocus());
        }
    }

    private void setColumn2Focus(int index) {
        column2view.scrollToPosition(index);
    }

    @Override
    public void setColumn3(Item item) {
        if(item instanceof Image) {
            preview.setVisibility(View.VISIBLE);
            column3view.setVisibility(View.GONE);
            setColumn3Preview((Image) item);
        }
        else if(item instanceof ItemList) {
            column3view.setVisibility(View.VISIBLE);
            preview.setVisibility(View.GONE);
            setColumn3List((ItemList) item);
        }
        else if(item instanceof Action) {
            column3view.setVisibility(View.VISIBLE);
            preview.setVisibility(View.GONE);
            setColumn3List(ItemList.emptyItemList());
        }
    }

    private void setColumn3Focus(int index) {
        column3view.scrollToPosition(index);
        column3adapter.setFocusedItem(index);
    }

    private void setColumn3Preview(Image imagePreview) {
        // TODO: check if column content exists, else display loading indicator
        ImageView imageView = preview.findViewById(R.id.imageView);
        Glide.with(this).load(imagePreview.getFile()).into(imageView);

        // TODO: set metadata (can display metadata even if image was not loaded, because metadata is loaded beforehand)
    }

    private void setColumn3List(ItemList column) {
        // TODO: check if column content exists, else display loading indicator
        column3adapter.setFileListSelection(column);
        setColumn3Focus(column.getFocus());
    }

    @Override
    public void setPath(Path path) {
        this.pathView.setText(path.parentPath + path.name);
    }

    @Override
    public void showFullImageView(ArrayList<String> list, int current) {
        mListener.openFullImage(list, current);
    }

    @Override
    public void openSystemFilePicker() {
//        ActivityNavigation.openSystemFilePicker(getContext());
        activityResultLauncher.launch(null);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void left() {
        Log.i("BrowserFragment", "left()");
        presenter.toParent();
    }

    @Override
    public void right() {
        Log.i("BrowserFragment", "right()");
        presenter.toChild();
    }

    public interface OnFragmentInteractionListener {
        void openFullImage(ArrayList<String> list, int current);
        AppContainer getAppContainer();
    }
}
