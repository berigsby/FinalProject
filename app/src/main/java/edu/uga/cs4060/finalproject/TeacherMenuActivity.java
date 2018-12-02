package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TeacherMenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button bRes, bQuizzes,bClassList,bAccountInfo2;

    String classId,teacherId,studentId,quizId;

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
        bAccountInfo2 = findViewById(R.id.bAccountInfo2); //id : 3
        bAccountInfo2.setOnClickListener(this);

        //classId = "-LS3EQGm-8kZEW5ZDAw1";
        //teacherId ="-LS3EQGb_fs76nVCm76l";
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
