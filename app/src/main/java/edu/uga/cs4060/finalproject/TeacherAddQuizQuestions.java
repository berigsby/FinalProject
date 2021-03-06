package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherAddQuizQuestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherAddQuizQuestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherAddQuizQuestions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean goBack = false;
    String classId,teacherId,studentId,quizId;
    EditText a1,a2,a3,a4,questionTextView;
    Button doneButton, addMoreButton;
    int count =0;

    //Database stuff
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    String TAG = "AddQuizQuestion";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String title, description;
    private OnFragmentInteractionListener mListener;

    public TeacherAddQuizQuestions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherAddQuizQuestions.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherAddQuizQuestions newInstance(String param1, String param2) {
        TeacherAddQuizQuestions fragment = new TeacherAddQuizQuestions();
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
        //((FloatingActionButton)((TeacherMenuElement)getActivity()).findViewById(R.id.fab)).hide();
        TeacherMenuElement.fab.hide();
        Log.d(TAG, "Fragment stack count" + getFragmentManager().getBackStackEntryCount());
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

        Bundle b = getArguments();
        title = b.getString("title");
        description = b.getString("description");
        quizId = b.getString("quizId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View aview = inflater.inflate(R.layout.fragment_teacher_add_quiz_questions, container, false);

        addMoreButton = aview.findViewById(R.id.addMoreButton);
        aview.findViewById(R.id.addMoreButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                questionTextView = aview.findViewById(R.id.editText5);
                a1 = aview.findViewById(R.id.a1);
                a2 = aview.findViewById(R.id.a2);
                a3 = aview.findViewById(R.id.a3);
                a4 = aview.findViewById(R.id.a4);

                String question= questionTextView.getText().toString();
                String s1 = a1.getText().toString();
                String s2 = a2.getText().toString();
                String s3 = a3.getText().toString();
                String s4 = a4.getText().toString();

                //Verify all fields are filled in
                if(s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("") || question.equals(""))
                {
                    Toast.makeText(aview.getContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                    goBack=false;
                    return;
                }

                RadioGroup radioGroup = getActivity().findViewById(R.id.radioGroup);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                int ans = 0;
                if(selectedId == R.id.answerRadio1){
                    ans = 1;
                } else if(selectedId == R.id.answerRadio2){
                    ans = 2;
                } else if(selectedId == R.id.answerRadio3){
                    ans = 3;
                } else if(selectedId == R.id.answerRadio4){
                    ans = 4;
                } else {
                    goBack=false;
                    Toast.makeText(aview.getContext(),"Please select the correct answer", Toast.LENGTH_LONG).show();
                    return;
                }
                QuestionPojo questionPojo = new QuestionPojo("",quizId,questionTextView.getText().toString(),
                        a1.getText().toString(),a2.getText().toString(),a3.getText().toString(),a4.getText().toString(),ans);
                MyFirebaseHelper.create(myRef,questionPojo);
                count++;
                a1.setText("");
                a2.setText("");
                a3.setText("");
                a4.setText("");
                radioGroup.clearCheck();
                questionTextView.setText("");

            }
        });

        aview.findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               if(count>0) {
                   //Toast.makeText(getContext(), "Question not recorded", Toast.LENGTH_LONG).show();
                   /*Screw it
                   getFragmentManager().popBackStack();
                   getActivity().onBackPressed();*/
                   getActivity().finish();
               }else{
                   Toast.makeText(getContext(), "Please add at least one question", Toast.LENGTH_LONG).show();
               }
                //TODO go back to the quizList
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
