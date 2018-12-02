package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class StudentMenuActivty extends AppCompatActivity implements View.OnClickListener {

    Button bRes2, bQuizzes2,bQuizHistory;
    String classId,studentId;
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;
    final String DEBUG_TAG = "StudentMenuActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu_activty);
        Intent intent = getIntent();

        classId = intent.getStringExtra("classId");
        studentId = intent.getStringExtra("studentId");

        bRes2 = findViewById(R.id.bRes2); //id : 0
        bRes2.setOnClickListener(this);
        bQuizzes2 = findViewById(R.id.bQuizzes2); //id : 1
        bQuizzes2.setOnClickListener(this);
        bQuizHistory = findViewById(R.id.bQuizHistory); //id : 2
        bQuizHistory.setOnClickListener(this);
        Button accountInfo = findViewById(R.id.bAccountInfo2);
        accountInfo.setOnClickListener(this);



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

                StudentPojo teach = MyFirebaseHelper.getStudent(myDataSnapshot,studentId);
                ClassPojo className = MyFirebaseHelper.getClass(myDataSnapshot,classId);

                TextView bGreetingStudent = findViewById(R.id.bGreetingStudent);
                bGreetingStudent.setText("Hello, " + teach.getName());
                TextView textView4 = findViewById(R.id.textView4);
                textView4.setText("Class Name: " +  className.getTitle());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(DEBUG_TAG, "Failed to read value.", error.toException());
            }
        });



    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(getBaseContext(), StudentActivityMenuElement.class);
            //Log.d(DEBUG_TAG, "Using Button ID" + v.getId());
            intent.putExtra("classId", classId);
            intent.putExtra("studentId",studentId);
            intent.putExtra("buttonID", v.getId());
            startActivity(intent);

    }

}
