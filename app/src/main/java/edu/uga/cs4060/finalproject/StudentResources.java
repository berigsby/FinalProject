package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * {@link StudentResources.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentResources#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentResources extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView resourceListStudent;

    private OnFragmentInteractionListener mListener;

    //Database stuff
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    String classId,studentId;

    //Debug
    final String  TAG = "StudentResourcesFrag";

    public StudentResources() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentResources.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentResources newInstance(String param1, String param2) {
        StudentResources fragment = new StudentResources();
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

        Bundle b = getArguments();
        classId = b.getString("classId");
        studentId = b.getString("studentId");

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

                List<ResourcePojo> bensClassRes = MyFirebaseHelper.getResourcesFromClassId(myDataSnapshot,classId);
                addContentsToListView(bensClassRes);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //Add resources to the list view
    private void addContentsToListView(List<ResourcePojo> bensClassRes){
        String valueS = "";
        resourceListStudent = getActivity().findViewById(R.id.ResourceListStudent);

//        List<String> res = new ArrayList<String>();
        List<Map<String,String>> res = new ArrayList<Map<String,String>>();
        for(ResourcePojo resourcePojo : bensClassRes){
            Map<String,String> data = new HashMap<String,String>(2);
            data.put("title", resourcePojo.getTitle());
            data.put("subtitle", resourcePojo.getText());
            valueS = resourcePojo.getTitle() + " " + resourcePojo.getText() + "\n";
            res.add(data);
            Log.d(TAG, resourcePojo.getTitle());
        }

        SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(),
                res,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2});

        resourceListStudent.setAdapter(arrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_resources, container, false);
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
        //    mListener = (OnFragmentInteractionListener) context;
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
