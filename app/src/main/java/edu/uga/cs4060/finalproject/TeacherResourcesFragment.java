package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
* Thinking about making it a list fragmet. TODO Change all "Fragments" to "ListFragment"
 */
public class TeacherResourcesFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String classId;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //List view
    ListView resourceListView;

    private OnFragmentInteractionListener mListener;

    //Database stuff
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    //Debug
    final String  TAG = "TeacherResourcesF";

    public TeacherResourcesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 TODO TBD --> Maybe for the fragment
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherResourcesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherResourcesFragment newInstance(String param1, String param2) {
        TeacherResourcesFragment fragment = new TeacherResourcesFragment();
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

        classId = TeacherMenuElement.classId;
        Log.d("TeacherFragRes", "Accessed");
        //((FloatingActionButton)((TeacherMenuElement)getActivity()).findViewById(R.id.fab)).show();
        TeacherMenuElement.fab.show();
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
        resourceListView = getActivity().findViewById(R.id.ResourceListView);

//        List<String> res = new ArrayList<String>();
        List<Map<String,String>> res = new ArrayList<Map<String,String>>();
        for(ResourcePojo resourcePojo : bensClassRes){
            Map<String,String> data = new HashMap<String,String>(2);
            data.put("title", resourcePojo.getTitle());
            data.put("subtitle", resourcePojo.getText());
            res.add(data);
            Log.d(TAG, resourcePojo.getTitle());
        }

        SimpleAdapter arrayAdapter = new SimpleAdapter(getActivity(),
                res,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2});
        resourceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String classId = TeacherMenuElement.classId;//TODO Remove Testing Purposes only

                List<ResourcePojo> resources = MyFirebaseHelper.getResourcesFromClassId(myDataSnapshot,classId);
                ResourcePojo theResource = resources.get(arg2);
                Log.d(TAG, "Obtaining resource ID: " + theResource.getResourceId() );

                Fragment fragment;
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                fragment = new ViewResource();
                args.putString("resourceID",theResource.getResourceId());
                args.putString("title",theResource.getTitle());
                args.putString("description",theResource.getText());
                ft.addToBackStack(null);
                fragment.setArguments(args);
                ft.replace(R.id.teacherElementFragment,fragment);
                ft.commit();
            }

        });
        resourceListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub

                List<ResourcePojo> resources = MyFirebaseHelper.getResourcesFromClassId(myDataSnapshot,classId);
                ResourcePojo theResource = resources.get(pos);

                Toast.makeText(getContext(), "Resource deleted", Toast.LENGTH_LONG).show();

                MyFirebaseHelper.removeResource(myRef,theResource.getResourceId());
                Fragment cur = new TeacherResourcesFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(cur);
                ft.attach(cur);
                ft.commit();
                return true;
            }
        });
        resourceListView.setAdapter(arrayAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_student_resources, container, false);
        return inflater.inflate(R.layout.fragment_teacher_resources, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause(){
        super.onPause();

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
