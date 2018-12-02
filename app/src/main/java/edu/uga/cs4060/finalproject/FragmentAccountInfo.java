package edu.uga.cs4060.finalproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAccountInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAccountInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccountInfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    final String DEBUG_TAG = "FragmentAccountInfo";
    private String mParam1;
    private String mParam2;
    private String classId, teacherId, studentId;
    private OnFragmentInteractionListener mListener;
    TextView nameViewText;
    TextView email;
    TextView textView8;
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;
    public FragmentAccountInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAccountInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAccountInfo newInstance(String param1, String param2) {
        FragmentAccountInfo fragment = new FragmentAccountInfo();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account_info, container, false);

        Bundle b = getArguments();
        classId = b.getString("classId");
        studentId = b.getString("studentId");
        teacherId = b.getString("teacherId");

        nameViewText = view.findViewById(R.id.nameTextView);
        email =        view.findViewById(R.id.email);
        textView8 =    view.findViewById(R.id.textView8);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = "test";
                myDataSnapshot = dataSnapshot;
                //textView.setText(value);
                Log.d(DEBUG_TAG, "Value is: " + value);

                TeacherPojo teach;
                StudentPojo stud;
                if(!teacherId.equals("")) {
                    teach = MyFirebaseHelper.getTeacher(myDataSnapshot, teacherId);
                    Log.d(DEBUG_TAG, teach.getName() + teach.getId() + teach.getTeacherId());
                    nameViewText.setText(("Name: "+  teach.getName()));
                    email.setText(("Class Id: "+classId));
                    textView8.setText(("Teacher Id: "+teach.getTeacherId()));
                }else{
                    stud = MyFirebaseHelper.getStudent(myDataSnapshot,studentId);
                    nameViewText.setText(("Name: "+  stud.getName()));
                    email.setText(("Class Id: "+classId));
                    textView8.setText(("Student Id: "+stud.getId()));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(DEBUG_TAG, "Failed to read value.", error.toException());
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
