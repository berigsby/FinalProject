package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTeacherAddRes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTeacherAddRes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTeacherAddRes extends Fragment{
    // TODO: Rename parameter arguments, choose names that match

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String classID =TeacherMenuElement.classId;

    private View aview;
    //Database stuff
    DatabaseReference myRef;

    private OnFragmentInteractionListener mListener;

    //Primary UI
    private Button bAddResource;
    private EditText titleTextView, descriptionTextView;
    public FragmentTeacherAddRes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTeacherAddRes.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTeacherAddRes newInstance(String param1, String param2) {
        FragmentTeacherAddRes fragment = new FragmentTeacherAddRes();
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
        ((FloatingActionButton)((TeacherMenuElement)getActivity()).findViewById(R.id.fab)).hide();
    }

    /***
     * Verify that EditText are not blank and add them to the
     * Firebase
     */
    public void addResource(){
        //Verify resources are properly filled in
        if(titleTextView.getText().toString().trim().equals(null) ||
                descriptionTextView.getText().toString().trim().equals(null) ||
                titleTextView.getText().toString().trim().equals("") ||
                descriptionTextView.getText().toString().trim().equals("")){
            Toast.makeText(getContext(), "Please make sure fields are not empty", Toast.LENGTH_SHORT).show();
            return;
        }else{
            String title = titleTextView.getText().toString().trim();
            String text = descriptionTextView.getText().toString().trim();
            ResourcePojo stevesRes1 = new ResourcePojo("", classID, title, text);
            stevesRes1 = MyFirebaseHelper.create(myRef,stevesRes1);
            //Return to the previous fragment to review the added resource
            ((TeacherMenuElement)getActivity()).onBackPressed();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        aview =  inflater.inflate(R.layout.fragment_teacher_add_res, container, false);

        //Firebase addtion
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //UI elements
        bAddResource = aview.findViewById(R.id.bAddResource);
        descriptionTextView = aview.findViewById(R.id.addResourceText);
        titleTextView = aview.findViewById(R.id.titleTextView);

        bAddResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addResource();
            }
        });

        return aview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
