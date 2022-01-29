package apps.aw.simplephotos.presentation_layer.browser.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apps.aw.simplephotos.presentation_layer.browser.BrowserContract;
import apps.aw.simplephotos.R;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileContentView;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;
import apps.aw.simplephotos.presentation_layer.browser.utils.ImagePreview;
import apps.aw.simplephotos.presentation_layer.browser.view.adapter.FileListAdapter;
import apps.aw.simplephotos.presentation_layer.browser.view.adapter.FileListNavigationAdapter;
import apps.aw.simplephotos.presentation_layer.browser.view.views.NavigationRecyclerView;

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
public class BrowserFragment extends Fragment implements BrowserContract.View, NavigationRecyclerView.NavigationKeyHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //the presenter for this view
    private BrowserContract.Presenter presenter;

    //the android views
    private TextView pathView;
    private RecyclerView column1view;
    private NavigationRecyclerView column2view;
    private RecyclerView column3view;
    private View preview;

    //adapters
    FileListAdapter column1adapter;
    FileListNavigationAdapter column2adapter;
    FileListAdapter column3adapter;


    public BrowserFragment() {
        // Required empty public constructor
    }

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.i("BrowserFragment", "onCreate()");

        column1adapter = new FileListAdapter(
                new FileList()
        );

        column2adapter = new FileListNavigationAdapter(
                new FileList(),
                (position, hasFocus) -> {
                    Log.i("BrowserFragment", "onItemFocusChanged(" + position +", "+ hasFocus + ")");
                    if(hasFocus) {
                        presenter.focus(position);
                    }
                },
                position -> {
                    Log.i("BrowserFragment", "onItemClick(" + position + ")");
                    presenter.focus(position);
                }
        );

        column3adapter = new FileListAdapter(
                new FileList()
        );
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
        column1view = view.findViewById(R.id.column_1);
        column2view = view.findViewById(R.id.column_2);
        column3view = view.findViewById(R.id.column_3);
        column1view.setLayoutManager(new LinearLayoutManager(getContext()));
        column2view.setLayoutManager(new LinearLayoutManager(getContext()));
        column3view.setLayoutManager(new LinearLayoutManager(getContext()));
        column1view.setHasFixedSize(true);
        column2view.setHasFixedSize(true);
        column3view.setHasFixedSize(true);
        column1view.setAdapter(column1adapter);
        column2view.setAdapter(column2adapter);
        column3view.setAdapter(column3adapter);
//        column1view.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        column1view.setFocusable(false);

        column2view.setKeyPressHandler(this);

        preview = view.findViewById(R.id.preview);

        pathView = view.findViewById(R.id.path_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    //Implemented BrowserContract methods----------------------------------------------------------
    @Override
    public void setPresenter(@NonNull BrowserContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setColumn1(FileList column) {
        // TODO: check if column content exists, else display loading indicator
        column1adapter.setFileListSelection(column);
    }

    @Override
    public void setColumn2(FileList column) {
        // TODO: check if column content exists, else display loading indicator
        if(column != null) {
            column2adapter.setFileListSelection(column);

        }
    }

    @Override
    public void setColumn3(FileContentView fileContentView) {
        if(fileContentView == null) {
            // file does not exist
            return;
        }
        switch (fileContentView.getDisplayType()) {
            case IMAGE:
                preview.setVisibility(View.VISIBLE);
                column3view.setVisibility(View.GONE);
                setColumn3Preview(fileContentView.getImagePreview());
                break;
            case FILELIST:
                column3view.setVisibility(View.VISIBLE);
                preview.setVisibility(View.GONE);
                setColumn3List(fileContentView.getFileList());
                break;
        }
    }

    private void setColumn3Preview(ImagePreview imagePreview) {
        // TODO: check if column content exists, else display loading indicator
        ImageView imageView = preview.findViewById(R.id.imageView);
        Glide.with(this).load(imagePreview.getFile()).into(imageView);

        // TODO: set metadata (can display metadata even if image was not loaded, because metadata is loaded beforehand)
    }

    private void setColumn3List(FileList column) {
        // TODO: check if column content exists, else display loading indicator
//        Log.i("BrowserFragment", "setColumn3 length: " + column.getListSize());
        column3adapter.setFileListSelection(column);
    }

    @Override
    public void setPath(String path) {
        this.pathView.setText(path);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void down() {
        Log.i("BrowserFragment", "down()");
    }

    @Override
    public void up() {
        Log.i("BrowserFragment", "up()");
    }

    @Override
    public void left() {
        Log.i("BrowserFragment", "left()");
        presenter.toParent();
    }

    @Override
    public void right() {
        Log.i("BrowserFragment", "right()");
        presenter.toFocusedChild();
    }

    @Override
    public void enter() {
        Log.i("BrowserFragment", "enter()");
        presenter.openFocusedChild();
    }
}
