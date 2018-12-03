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
import android.widget.Button;
import android.widget.ListView;
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
 * {@link TeacherClassRoster.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherClassRoster#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherClassRoster extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final String  TAG = "TeacherClassRosterF";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String teacherID = "-LS3EQGb_fs76nVCm76l";
    private String classID= TeacherMenuElement.classId;
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;
    private OnFragmentInteractionListener mListener;
    ListView rosterListView;
    public TeacherClassRoster() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherClassRoster.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherClassRoster newInstance(String param1, String param2) {
        TeacherClassRoster fragment = new TeacherClassRoster();
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


    //Add resources to the list view
    private void addContentsToListView(List<StudentPojo> studentList,View view){
        rosterListView = view.findViewById(R.id.classRosterList);
        final List<StudentPojo> studentList2 = studentList;
//        List<String> res = new ArrayList<String>();
        final List<Map<String,String>> res = new ArrayList<Map<String,String>>();
        for(StudentPojo stud : studentList){
            Map<String,String> data = new HashMap<String,String>(2);
            data.put("title", stud.getName());
            data.put("subtitle",stud.getId());
            res.add(data);
            Log.d(TAG, stud.getName());
        }

        SimpleAdapter arrayAdapter = new SimpleAdapter(view.getContext(),
                res,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2});

        rosterListView.setAdapter(arrayAdapter);
        rosterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String classId = TeacherMenuElement.classId;//TODO Remove Testing Purposes only


                StudentPojo theStudent = studentList2.get(arg2);
                Log.d(TAG, "Obtaining student ID: " + theStudent.getId() );

                Fragment fragment;
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                fragment = new StudentGrades();
                args.putString("studentId",theStudent.getStudentId());
                args.putString("classId", classId);
                ft.addToBackStack(null);
                fragment.setArguments(args);
                ft.replace(R.id.teacherElementFragment,fragment);
                TextView tv = getActivity().findViewById(R.id.elementTextVIew);
                tv.setText("Quizzes for: " + theStudent.getName());
                ft.commit();
            }
        });

        rosterListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub
                StudentPojo theStudent = studentList2.get(pos);

                MyFirebaseHelper.unenroll(myRef,myDataSnapshot,theStudent.getStudentId(),classID);
                Fragment cur = new TeacherClassRoster();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(cur);
                ft.attach(cur);
                ft.commit();
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_teacher_class_roster, container, false);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        TextView tv = getActivity().findViewById(R.id.elementTextVIew);
        tv.setText("Class Roster");

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
                String classId = TeacherMenuElement.classId;

                //Database needs to be updated so classID are included
                List<StudentPojo> allStudents = MyFirebaseHelper.getStudentsFromClassId(myDataSnapshot,classId);
                addContentsToListView(allStudents,view);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
