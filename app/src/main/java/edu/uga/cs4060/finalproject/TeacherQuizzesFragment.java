package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
 * {@link TeacherQuizzesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherQuizzesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherQuizzesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    final String  TAG = "StudentResourcesFrag";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView resourceListStudent;
    private OnFragmentInteractionListener mListener;
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    String classId,studentId,quizId;
    public TeacherQuizzesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherQuizzesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherQuizzesFragment newInstance(String param1, String param2) {
        TeacherQuizzesFragment fragment = new TeacherQuizzesFragment();
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
//        ((FloatingActionButton)((TeacherMenuElement)getActivity()).findViewById(R.id.fab)).show();
        TeacherMenuElement.fab.show();
        //Accessing the firebase test
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Bundle b = getArguments();//getActivity().getIntent().getExtras();


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

                classId = TeacherMenuElement.classId;

                List<QuizPojo> studentQuizzesLeft = MyFirebaseHelper.getQuizzesFromClassId(myDataSnapshot,classId);
                addContentsToListView(studentQuizzesLeft);
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
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_teacher_quizzes, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void addContentsToListView(List<QuizPojo> bensClassRes){
        String valueS = "";
        resourceListStudent = getActivity().findViewById(R.id.quizListTeacher);

//        List<String> res = new ArrayList<String>();
        List<Map<String,String>> res = new ArrayList<Map<String,String>>();
        for(QuizPojo resourcePojo : bensClassRes){
            Map<String,String> data = new HashMap<String,String>(2);
            data.put("title", resourcePojo.getTitle());
            data.put("subtitle", resourcePojo.getDesc());
            //valueS = resourcePojo.getTitle() + " " + resourcePojo.getText() + "\n";
            res.add(data);
            Log.d(TAG, resourcePojo.getTitle());
        }

        SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(),
                res,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2});

        resourceListStudent.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub

                List<QuizPojo> studentQuizzesLeft = MyFirebaseHelper.getQuizzesFromClassId(myDataSnapshot,classId);
                QuizPojo quizPojo = studentQuizzesLeft.get(pos);

                MyFirebaseHelper.removeQuiz(myRef,myDataSnapshot,quizPojo.getQuizId());
                Fragment cur = new TeacherQuizzesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(cur);
                ft.attach(cur);
                ft.commit();
                return true;
            }
        });

        resourceListStudent.setAdapter(arrayAdapter);
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
