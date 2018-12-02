package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.os.Bundle;
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

import edu.uga.cs4060.finalproject.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StudentQuizListFragment extends Fragment {

    final String  TAG = "StudentResourcesFrag";

    ListView resourceListStudent;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    String classId,studentId,quizId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentQuizListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StudentQuizListFragment newInstance(int columnCount) {
        StudentQuizListFragment fragment = new StudentQuizListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //Accessing the firebase test
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Bundle b = getArguments();//getActivity().getIntent().getExtras();
        classId = b.getString("classId");
        studentId = b.getString("studentId");
        quizId = b.getString("quizId");

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
                //String studentId = "-LS3HYIciNWJduRJAoq-";

                List<QuizPojo> studentQuizzesLeft = MyFirebaseHelper.getQuizzesLeft(myDataSnapshot,studentId,classId);
                addContentsToListView(studentQuizzesLeft);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    //Add resources to the list view
    private void addContentsToListView(List<QuizPojo> bensClassRes){
        String valueS = "";
        resourceListStudent = getActivity().findViewById(R.id.ResourceListStudent);

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

        resourceListStudent.setAdapter(arrayAdapter);

        resourceListStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //String classId = "-LS3EQGm-8kZEW5ZDAw1";//TODO Remove Testing Purposes only
                //String studentId = "-LS3HYIciNWJduRJAoq-";

                List<QuizPojo> studentQuizzesLeft = MyFirebaseHelper.getQuizzesLeft(myDataSnapshot,studentId,classId);
                String quizId = studentQuizzesLeft.get(arg2).getQuizId();

                Fragment fragment;
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                fragment = new StudentQuiz();
                args.putString("quizId",quizId);
                args.putString("studentId",studentId);
                args.putString("classId",classId);
                fragment.setArguments(args);
                ft.replace(R.id.fragment2,fragment);
                //Log.d("############","Items " +  MoreItems[arg2] );
                ft.commit();
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_resources, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if (context instanceof OnListFragmentInteractionListener) {
        //    mListener = (OnListFragmentInteractionListener) context;
        //} else {
        //    throw new RuntimeException(context.toString()
        //            + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
