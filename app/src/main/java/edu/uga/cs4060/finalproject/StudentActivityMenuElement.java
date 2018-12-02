package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class StudentActivityMenuElement extends AppCompatActivity {
    String DEBUG_TAG = "StudentActivityMenuElement";
    TextView textView5;

    String classId,studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu_element);

        textView5 = findViewById(R.id.textView5);
        Bundle b = getIntent().getExtras();
        Intent intent = getIntent();
        int teacherSelection = intent.getIntExtra("buttonID",0);
        classId = intent.getStringExtra("classId");
        studentId = intent.getStringExtra("studentId");

        //Select the correct fragment
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();

        switch(teacherSelection){
            case R.id.bRes2:
                Log.d(DEBUG_TAG, "bRes " + teacherSelection);
                textView5.setText("Your Resources");
                fragment = new TeacherResourcesFragment();
                fragment.setArguments(args);
                ft.replace(R.id.fragment2,fragment);
                break;
            case R.id.bQuizzes2:
                Log.d(DEBUG_TAG, "bQuizzes " + teacherSelection);
                textView5.setText("Class Quizzes");
                //Intent intent1 = new Intent(getBaseContext(),StudentQuiz.class);
                fragment = new StudentQuizListFragment();//TODO change this to go to the quizlist
                args.putString("classId",classId);
                args.putString("studentId",studentId);
                fragment.setArguments(args);
                ft.replace(R.id.fragment2,fragment);
                /*
                fragment = new StudentQuiz();//TODO change this to go to the quizlist
                args.putString("quizId","-LS3EQGvMB-Ov9Oa2hK9");
                args.putString("studentId","-LS3HYIciNWJduRJAoq-");
                fragment.setArguments(args);
                ft.replace(R.id.fragment2,fragment);
                */
                break;
            case R.id.bQuizHistory:
                Log.d(DEBUG_TAG, "bClassList " + teacherSelection);
                textView5.setText("Class Roster");
                fragment = new StudentQuiz();
                ft.replace(R.id.fragment2,fragment);
                break;
            default:
                Log.d(DEBUG_TAG, "Nothing " + teacherSelection);

        }


        ft.commit();
    }

}
