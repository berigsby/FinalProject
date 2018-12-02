package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentQuiz.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentQuiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentQuiz extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView quizQuestion;
    RadioButton ansChoice1,ansChoice2,ansChoice3,ansChoice4;
    RadioGroup ansChoices;
    boolean updateQuiz;
    String classId,quizId,studentId;

    Button nextQuestion;

    //Database stuff
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    //Debug
    final String  TAG = "StudentQuiz";

    public StudentQuiz() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentQuiz.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentQuiz newInstance(String param1, String param2) {
        StudentQuiz fragment = new StudentQuiz();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void loadQuestion(final String studentId, final String quizId){
        //String classId = "-LS3EQGm-8kZEW5ZDAw1";
        final QuestionPojo questionPojo;
        String quizToFinish = MyFirebaseHelper.quizContinueable(myDataSnapshot,classId,studentId);
        if(!quizToFinish.equals(quizId)){
            if(!quizToFinish.equals("")) {
                //getActivity().onBackPressed();

                Intent intent = new Intent(getContext(),StudentMenuActivty.class);
                intent.putExtra("classId",classId);
                intent.putExtra("studentId",studentId);
                intent.putExtra("quizId",quizId);
                startActivity(intent);
                return;
            }
            //other incomplete quiz...
        }

        if(!quizToFinish.equals("")) {
            questionPojo = MyFirebaseHelper.getNextQuestion(myRef,myDataSnapshot,quizId,studentId);
        } else {
            MyFirebaseHelper.initiateQuiz(myRef,myDataSnapshot,studentId,quizId);

            //Fragment fragment;
            //FragmentManager fm = getFragmentManager();
            //FragmentTransaction ft = fm.beginTransaction();
            //Bundle args = new Bundle();
            //fragment = new StudentQuiz();
            //args.putString("quizId",quizId);
            //args.putString("studentId",studentId);
            //args.putString("classId",classId);
            //fragment.setArguments(args);
            //ft.replace(R.id.studentElementFragment,fragment);
            //Log.d("############","Items " +  MoreItems[arg2] );
            //ft.commit();
            return;
            //questionPojo = MyFirebaseHelper.getNextQuestion(myRef,myDataSnapshot,quizId,studentId);
        }
        if(questionPojo == null){
            //getActivity().onBackPressed();
            Intent intent = new Intent(getContext(),StudentMenuActivty.class);
            intent.putExtra("classId",classId);
            intent.putExtra("studentId",studentId);
            intent.putExtra("quizId",quizId);
            startActivity(intent);
            return;
        }

        quizQuestion = getActivity().findViewById(R.id.studentQuizQuestion);
        ansChoices = getActivity().findViewById(R.id.studentQuizRadioGroup);
        ansChoice1 = getActivity().findViewById(R.id.studentQuizRadioButton1);
        ansChoice2 = getActivity().findViewById(R.id.studentQuizRadioButton2);
        ansChoice3 = getActivity().findViewById(R.id.studentQuizRadioButton3);
        ansChoice4 = getActivity().findViewById(R.id.studentQuizRadioButton4);
        nextQuestion = getActivity().findViewById(R.id.studentQuizButtonNext);

        quizQuestion.setText(questionPojo.getQuestionText());
        ansChoice1.setText(questionPojo.getAnswer1());
        ansChoice2.setText(questionPojo.getAnswer2());
        ansChoice3.setText(questionPojo.getAnswer3());
        ansChoice4.setText(questionPojo.getAnswer4());

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizQuestion = getActivity().findViewById(R.id.studentQuizQuestion);
                ansChoices = getActivity().findViewById(R.id.studentQuizRadioGroup);
                ansChoice1 = getActivity().findViewById(R.id.studentQuizRadioButton1);
                ansChoice2 = getActivity().findViewById(R.id.studentQuizRadioButton2);
                ansChoice3 = getActivity().findViewById(R.id.studentQuizRadioButton3);
                ansChoice4 = getActivity().findViewById(R.id.studentQuizRadioButton4);
                nextQuestion = getActivity().findViewById(R.id.studentQuizButtonNext);
                int ans = ansChoices.getCheckedRadioButtonId();
                if(ans == ansChoice1.getId()){
                    ans = 1;
                } else if(ans == ansChoice2.getId()){
                    ans = 2;
                } else if (ans == ansChoice3.getId()){
                    ans = 3;
                } else if (ans == ansChoice4.getId()){
                    ans = 4;
                }
                if(ans != -1) {
                    boolean anotherQ = MyFirebaseHelper.markQuestion(myRef, myDataSnapshot, questionPojo, studentId, ans);
                    if ( anotherQ ) {
                        Fragment fragment;
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Bundle args = new Bundle();
                        fragment = new StudentQuiz();
                        args.putString("quizId",quizId);
                        args.putString("studentId",studentId);
                        args.putString("classId",classId);
                        fragment.setArguments(args);
                        ft.replace(R.id.studentElementFragment,fragment);
                        //Log.d("############","Items " +  MoreItems[arg2] );
                        ft.commit();
                        //Intent intent = new Intent(getActivity().getBaseContext(), StudentActivityMenuElement.class);
                        //intent.putExtra("classId",classId);
                        //intent.putExtra("studentId",studentId);
                        //intent.putExtra("quizId",quizId);
                        //intent.putExtra("buttonID", R.id.bQuizzes2); //getActivity().findViewById(R.id.bQuizzes2).getId());
                        //startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(),StudentActivityMenuElement.class);//
                        intent.putExtra("classId",classId);
                        intent.putExtra("studentId",studentId);
                        intent.putExtra("quizId",quizId);
                        intent.putExtra("buttonID", R.id.bQuizzes2);//
                        startActivity(intent);
                    }
                    //updateQuiz = true;
                    //Could add popup here
                    //loadQuestion(studentId, quizId);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aview = inflater.inflate(R.layout.fragment_student_quiz, container, false);

        Bundle b = getArguments();//getActivity().getIntent().getExtras();

        classId = b.getString("classId");
        studentId = b.getString("studentId");
        quizId = b.getString("quizId");

        updateQuiz = true;

        quizQuestion = getActivity().findViewById(R.id.studentQuizQuestion);
        ansChoices = getActivity().findViewById(R.id.studentQuizRadioGroup);
        ansChoice1 = getActivity().findViewById(R.id.studentQuizRadioButton1);
        ansChoice2 = getActivity().findViewById(R.id.studentQuizRadioButton2);
        ansChoice3 = getActivity().findViewById(R.id.studentQuizRadioButton3);
        ansChoice4 = getActivity().findViewById(R.id.studentQuizRadioButton4);
        nextQuestion = getActivity().findViewById(R.id.studentQuizButtonNext);


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

                //String classId = "-LS3EQGm-8kZEW5ZDAw1";//TODO Remove Testing Purposes only
                //String studentId = "-LS3HYIciNWJduRJAoq-"; //savedInstanceState.getString("studentId");
                //String quizId = "-LS3EQGvMB-Ov9Oa2hK9"; //savedInstanceState.getString("quizId");

                if(MyFirebaseHelper.quizContinueable(dataSnapshot,classId,studentId).equals("")){
                    //Intent intent = new Intent(getContext(),StudentMenuActivty.class);
                    //intent.putExtra("classId",classId);
                    //intent.putExtra("studentId",studentId);
                    //intent.putExtra("quizId",quizId);
                    //startActivity(intent);
                    //updateQuiz = false;
                } else {
                    updateQuiz = true;
                }

                //if(updateQuiz) {
                updateQuiz = false;
                loadQuestion(studentId, quizId);
                // }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
        //if (context instanceof OnFragmentInteractionListener) {
         //   mListener = (OnFragmentInteractionListener) context;
        //} else {
        //    throw new RuntimeException(context.toString()
        //            + " must implement OnFragmentInteractionListener");
        //}
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
