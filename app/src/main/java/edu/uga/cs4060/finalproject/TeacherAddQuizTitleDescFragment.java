package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherAddQuizTitleDescFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherAddQuizTitleDescFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherAddQuizTitleDescFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText editQuizTitle,editQuizDesc;
    Button buttonCreateQuiz;

    String classId,teacherId;

    //Database stuff
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    String TAG = "AddQuizTitleDesc";

    public TeacherAddQuizTitleDescFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherAddQuizTitleDescFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherAddQuizTitleDescFragment newInstance(String param1, String param2) {
        TeacherAddQuizTitleDescFragment fragment = new TeacherAddQuizTitleDescFragment();
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

        //Accessing the firebase test
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = "test";

                myDataSnapshot = dataSnapshot;

                //textView.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_add_quiz_title_desc, container, false);
        buttonCreateQuiz = view.findViewById(R.id.buttonCreateQuiz);
        editQuizTitle = view.findViewById(R.id.editQuizTitle);
        editQuizDesc = view.findViewById(R.id.editQuizDesc);
        buttonCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editQuizDesc.getText().toString();
                String title = editQuizTitle.getText().toString();
                Bundle args = new Bundle();
                Fragment fragment;
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fragment = new TeacherAddQuizQuestions();
                args.putString("description",description);
                args.putString("title",title);
                ft.addToBackStack(null);
                fragment.setArguments(args);
                ft.replace(R.id.teacherElementFragment,fragment);
                ft.commit();

                /***
                 * You can determine here if you want to add the title
                 * and description to the DB
                 */
                editQuizTitle = getActivity().findViewById(R.id.editQuizTitle);
                editQuizDesc = getActivity().findViewById(R.id.editQuizDesc);

                QuizPojo quizPojo = new QuizPojo("",classId,editQuizTitle.getText().toString(),editQuizDesc.getText().toString());
                MyFirebaseHelper.create(myRef,quizPojo);
                //TODO call addquizquestions

            }
        });
        return view;
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
