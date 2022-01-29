package apps.aw.simplephotos.presentation_layer.full_image.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.domain_layer.model.FileModel;
import apps.aw.simplephotos.presentation_layer.full_image.FullImageContract;
import apps.aw.simplephotos.presentation_layer.full_image.presenter.FullImagePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullImageFragment extends Fragment implements FullImageContract.View {

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

        //Create the presenter (or let it inject by activity? What does that mean?)
        presenter = new FullImagePresenter(this);
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

        //set onGenericMotionListener on view for e.g. Dpad navigation (go forward/backwards)
        view.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View view, MotionEvent motionEvent) {
                //TODO: use Dpad class to handle the event, then call appropriate presenter method
                //e.g. fullImageNext() or fullImagePrevious
                presenter.fullImageNext();
                //or:
                presenter.fullImagePrevious();
                //etc
                return true;    //consume event?
            }
        });

        //set onKeyListener
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                //TODO: use Dpad class to handle the event, then call appropriate presenter method
                //e.g. fullImageNext() or fullImagePrevious
                presenter.fullImageNext();
                //or:
                presenter.fullImagePrevious();
                //etc
                return true;    //consume event?
            }
        });

    }

    //public member methods (for communication)----------------------------------------------------

    /**
     * Called by activity to display an image in the fragments imageview.
     * @param img an ImageFile object
     */
    public void updateImageView(FileModel img) {
        Glide.with(this).load(img.getFile()).into(fullImageView);
        imageInfo.setText("put info of img here");
    }


    //implemented contract interface methods-------------------------------------------------------

    @Override
    public void setFullImage(FileModel img) {
        //load image
        Glide.with(this).load(img.getFile()).into(fullImageView);
    }

    @Override
    public void setEndOfListImage(Object obj) {

    }


    //public interface implemented by containing activity------------------------------------------

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        boolean onInputEvent(View view, InputEvent inputEvent);
    }


}
