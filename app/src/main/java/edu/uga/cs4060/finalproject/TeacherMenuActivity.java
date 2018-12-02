package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TeacherMenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button bRes, bQuizzes,bClassList,bAccountInfo2;

    String classId,teacherId,studentId,quizId;
    DataSnapshot myDataSnapshot;
    DatabaseReference myRef;
    final String DEBUG_TAG = "TeacherMenuActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);
        Intent intent = getIntent();

        classId = intent.getStringExtra("classId");
        teacherId = intent.getStringExtra("teacherId");

        bRes = findViewById(R.id.bRes); //id : 0
        bRes.setOnClickListener(this);
        bQuizzes = findViewById(R.id.bQuizzes); //id : 1
        bQuizzes.setOnClickListener(this);
        bClassList = findViewById(R.id.bClassList); //id : 2
        bClassList.setOnClickListener(this);
        bAccountInfo2 = findViewById(R.id.bAccountInfo); //id : 3
        bAccountInfo2.setOnClickListener(this);

//        classId = "-LS3EQGm-8kZEW5ZDAw1";
//        teacherId ="-LS3EQGb_fs76nVCm76l";

//        DataSnapshot myDataSnapshot;
//        DatabaseReference myRef;
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

                TeacherPojo teach = MyFirebaseHelper.getTeacher(myDataSnapshot,teacherId);
                ClassPojo className = MyFirebaseHelper.getClass(myDataSnapshot,classId);
                TextView tx = findViewById(R.id.GreetingTeacher);
                tx.setText("Hello, " + teach.getName() + "!");

                TextView tz = findViewById(R.id.classIddd);
                tz.setText("Class ID(copy):"+ classId);
                tz.setTextIsSelectable(true);

                TextView ohwowicanmakethisvaraiblenamereallylongfornoreasonrightholycowineedtohavemorefunthingtodo = findViewById(R.id.ClassName);
                ohwowicanmakethisvaraiblenamereallylongfornoreasonrightholycowineedtohavemorefunthingtodo.setText("Class Name: "+ className.getTitle());


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
        Intent intent = new Intent(getBaseContext(), TeacherMenuElement.class);

            Log.d(DEBUG_TAG, "Using Button ID" + v.getId());
            Bundle bungle = new Bundle();
            bungle.putInt("buttonId", v.getId());
            bungle.putString("classId",classId);
            bungle.putString("teacherId",teacherId);
            intent.putExtras(bungle);
            startActivity(intent);

    }
}
