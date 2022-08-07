package apps.aw.simplephotos.android.ui.full_image;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.android.dpad.Dpad;
import apps.aw.simplephotos.java.Image;
import apps.aw.simplephotos.java.interactors.shared.Navigation;
import apps.aw.simplephotos.java.presenters.fullimage.FullImageContract;
import apps.aw.simplephotos.java.presenters.fullimage.FullImagePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullImageFragment
        extends Fragment
        implements FullImageContract.View {

    private static final String TAG = "FullImageFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;    //this points to the containing activity (context)

    private FullImageContract.Presenter presenter;

    private ImageView fullImageView;
    private TextView imageInfo;

    public FullImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FullImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullImageFragment newInstance(String param1, String param2) {
        FullImageFragment fragment = new FullImageFragment();
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
            //after this, mListener can be used to call functions in the context activity
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //get some important views
        fullImageView = view.findViewById(R.id.imageView3);
        imageInfo = view.findViewById(R.id.textView5);

        // get the Navigator from the activity (don't create it ourselves)
        presenter = new FullImagePresenter(this, mListener.getNavigation());

//        ((NavigationFullImageView) view).setKeyPressHandler(this);

//        boolean tookFocus = view.requestFocus();

//        Log.i(TAG, "tookFokus: " + tookFocus);
        //set onGenericMotionListener on view for e.g. Dpad navigation (go forward/backwards)
//        view.setOnGenericMotionListener(new View.OnGenericMotionListener() {
//            @Override
//            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
//                //TODO: use Dpad class to handle the event, then call appropriate presenter method
//                //e.g. fullImageNext() or fullImagePrevious
//                presenter.fullImageNext();
//                //or:
//                presenter.fullImagePrevious();
//                //etc
//                return true;    //consume event?
//            }
//        });

        //set onKeyListener
        Log.i(TAG, "onViewCreated()");
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (Dpad.getDirectionPressed(keyEvent)) {
                        case Dpad.LEFT:
                        presenter.fullImagePrevious();
                        return true;
                        case Dpad.RIGHT:
                        presenter.fullImageNext();
                        return true;
                    }
                }
                return false;  //do not consume event?
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.initialize();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //implemented contract interface methods-------------------------------------------------------

    @Override
    public void setFullImage(Image img) {
        //load image
        Log.i(TAG, "setFullImage: " + img.getFile().toString());
        Glide.with(this)
                .applyDefaultRequestOptions(
                        new RequestOptions()
                                .format(DecodeFormat.PREFER_ARGB_8888))
                .load(img.getFile()).into(fullImageView);

    }

    @Override
    public void setEndOfListImage(Object obj) {

    }

    //public interface implemented by containing activity------------------------------------------
    public interface OnFragmentInteractionListener {

        Navigation getNavigation();
    }


}
