package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    TextView textView;
    EditText editText;
    Button button, testingButton;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        testingButton = (Button) findViewById(R.id.testingButton);
        testingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),TeacherMenuActivity.class);
                startActivity(intent);
            }
        });


        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("testing").push().child("myString").setValue(editText.getText().toString());
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = "";
                for(DataSnapshot child1 : dataSnapshot.child("class").getChildren()){
                    String className = child1.child("name").getValue(String.class);
                    //String classId = child1.getKey();
                    String classId = child1.child("classId").getValue(String.class);
                    for(DataSnapshot child2 : dataSnapshot.child("teacherToClass").getChildren()){
                        String classId2 = child2.child("classId").getValue(String.class);
                        if(classId2.equals(classId)){
                            String teacherId = child2.child("teacherId").getValue(String.class);
                            //String teacherName = dataSnapshot.child("teacher").child(teacherId).child("name").getValue(String.class);
                            TeacherPojo teacher = dataSnapshot.child("teacher").child(teacherId).getValue(TeacherPojo.class);
                            value += (className + "'s teacher is " + teacher.getName() + "\n");
                        }//if
                    }//for child2
                }//for child1
                textView.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }

}
