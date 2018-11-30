package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherMenuElement extends AppCompatActivity{
    final String DEBUG_TAG = "TeacherMenuElementA";
    TextView elementTextVIew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu_element);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(android.R.drawable.ic_input_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        elementTextVIew = findViewById(R.id.elementTextVIew);

        Intent intent = getIntent();
        int teacherSelection = intent.getIntExtra("buttonID",0);

        //Select the correct fragment
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();

        switch(teacherSelection){
            case R.id.bRes:
                Log.d(DEBUG_TAG, "bRes " + teacherSelection);
                elementTextVIew.setText("Your Resources");
                fragment = new TeacherResourcesFragment();
                fragment.setArguments(args);
                ft.replace(R.id.teacherElementFragment,fragment);
                break;
            case R.id.bQuizzes:
                Log.d(DEBUG_TAG, "bQuizzes " + teacherSelection);
                elementTextVIew.setText("Class Quizzes");
                fragment = new TeacherQuizzesFragment();
                ft.replace(R.id.teacherElementFragment,fragment);
                break;
            case R.id.bClassList:
                Log.d(DEBUG_TAG, "bClassList " + teacherSelection);
                elementTextVIew.setText("Class Roster");
                fragment = new TeacherClassRoster();
                ft.replace(R.id.teacherElementFragment,fragment);
                break;
                default:
                    Log.d(DEBUG_TAG, "Nothing " + teacherSelection);

        }


        ft.commit();
    }

}
