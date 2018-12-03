package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassList extends AppCompatActivity {

    String personId;
    boolean isTeacher;
    TextView classListLoggedInAs,titleView,descView;
    EditText titleEdit,descEdit;
    ListView classListView;
    Button classListSignOutButton,addClassButton;

    //Database stuff
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;

    GoogleSignInClient mGoogleSignInClient;

    String TAG = "ClassListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        classListSignOutButton = findViewById(R.id.classListSignOutbutton);
        classListView = findViewById(R.id.classList);
        classListLoggedInAs = findViewById(R.id.classListLoggedInAs);
        titleView = findViewById(R.id.textViewClassListTitle);
        descView = findViewById(R.id.textViewClassListDesc);
        titleEdit = findViewById(R.id.editTextClassListTitle);
        descEdit = findViewById(R.id.editTextClassListDesc);
        addClassButton = findViewById(R.id.addClassButton);

        Bundle b = getIntent().getExtras();
        final String studentId = b.getString("studentId");
        final String teacherId = b.getString("teacherId");
        isTeacher = false;

        if(studentId == null){
            if(teacherId == null){
                //not signedIn
                classListSignOutButton.callOnClick();
            } else {
                isTeacher = true;
                personId = teacherId;
            }
        } else {
            isTeacher = false;
            personId = studentId;
            titleView.setVisibility(View.GONE);
            titleEdit.setVisibility(View.GONE);
            descView.setText("ClassId:");
            addClassButton.setText("Enroll");
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                myDataSnapshot = dataSnapshot;

                String name = "";
                if(isTeacher){
                    TeacherPojo teacherPojo = MyFirebaseHelper.getTeacher(myDataSnapshot,personId);
                    name = teacherPojo.getName();
                } else {
                    //isStudent
                    StudentPojo studentPojo = MyFirebaseHelper.getStudent(myDataSnapshot,personId);
                    name = studentPojo.getName();
                }
                classListLoggedInAs.setText("Signed in as " + name);

                List<ClassPojo> classList;
                if(isTeacher){
                    classList = MyFirebaseHelper.getClassesFromTeacherId(myDataSnapshot,personId);
                } else {
                    //isStudent
                    classList = MyFirebaseHelper.getClassesFromStudentId(myDataSnapshot, personId);
                }
                addContentsToListView(classList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        classListSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                Toast.makeText(getBaseContext(), "Signed Out", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String descText = descEdit.getText().toString();
                String titleText = titleEdit.getText().toString();
                if(descText.equals("")){
                    Toast.makeText(getBaseContext(), "Please fill in the values", Toast.LENGTH_LONG).show();
                } else{
                    if (isTeacher && titleText.equals("")) {
                        Toast.makeText(getBaseContext(), "Please fill in the values", Toast.LENGTH_LONG).show();
                    } else if (isTeacher){
                        //Create a teacher class
                        ClassPojo classPojo = new ClassPojo("",personId,titleText,descText);
                        classPojo = MyFirebaseHelper.create(myRef,classPojo);
                        Intent intent = new Intent(getBaseContext(),TeacherMenuActivity.class);
                        intent.putExtra("classId", classPojo.getClassId());
                        intent.putExtra("teacherId", personId);
                        startActivity(intent);
                    } else {
                        //Enroll student if class id works
                        String classId = descText;
                        ClassPojo classPojo = MyFirebaseHelper.getClass(myDataSnapshot,classId);
                        if(classPojo == null){
                            Toast.makeText(getBaseContext(), "No class found with that Id", Toast.LENGTH_LONG).show();
                        } else {
                            MyFirebaseHelper.enroll(myRef,personId,classId);
                            Intent intent = new Intent(getBaseContext(),StudentMenuActivty.class);
                            intent.putExtra("classId",classId);
                            intent.putExtra("studentId",personId);
                            startActivity(intent);
                        }
                    }
                }

                descEdit.setText("");
                titleEdit.setText("");
            }
        });


        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                List<ClassPojo> classList;
                if(isTeacher){
                    classList = MyFirebaseHelper.getClassesFromTeacherId(myDataSnapshot,personId);
                } else {
                    //isStudent
                    classList = MyFirebaseHelper.getClassesFromStudentId(myDataSnapshot, personId);
                }

                ClassPojo classPojo = classList.get(arg2);

                if(isTeacher) {
                    Intent intent = new Intent(getBaseContext(),TeacherMenuActivity.class);
                    intent.putExtra("classId", classPojo.getClassId());
                    intent.putExtra("teacherId", personId);
                    startActivity(intent);
                } else {
                    //isStudent
                    Intent intent = new Intent(getBaseContext(),StudentMenuActivty.class);
                    intent.putExtra("classId",classPojo.getClassId());
                    intent.putExtra("studentId",personId);
                    startActivity(intent);
                }
            }

        });

        classListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                List<ClassPojo> classList;
                if(isTeacher){
                    classList = MyFirebaseHelper.getClassesFromTeacherId(myDataSnapshot,personId);
                } else {
                    //isStudent
                    classList = MyFirebaseHelper.getClassesFromStudentId(myDataSnapshot, personId);
                }

                ClassPojo classPojo = classList.get(pos);

                if(isTeacher) {
                    MyFirebaseHelper.removeClass(myRef,myDataSnapshot,classPojo.getClassId());
                    Toast.makeText(getBaseContext(), "Class Deleted", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    //isStudent
                    MyFirebaseHelper.unenroll(myRef,myDataSnapshot,personId,classPojo.getClassId());
                    Toast.makeText(getBaseContext(), "Un-enrolled", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

                return true;
            }
        });

    }

    //Add resources to the list view
    private void addContentsToListView(List<ClassPojo> classListPojo){
        List<Map<String,String>> res = new ArrayList<Map<String,String>>();
        for(ClassPojo classPojo : classListPojo){
            Map<String,String> data = new HashMap<String,String>(2);
            data.put("title", classPojo.getTitle());
            data.put("subtitle", classPojo.getDesc());
            res.add(data);
            Log.d(TAG, classPojo.getTitle());
        }

        SimpleAdapter arrayAdapter = new SimpleAdapter(this,
                res,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2});

        classListView.setAdapter(arrayAdapter);
    }
}
