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

import java.util.List;

public class TeacherMenuElement extends AppCompatActivity{
    final String DEBUG_TAG = "TeacherMenuElementA";
    TextView elementTextVIew;
    int teacherSelectionp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu_element);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Get the intent
        Intent intent = getIntent();
        final int teacherSelection = intent.getIntExtra("buttonID",0);
        teacherSelectionp2 = teacherSelection;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(android.R.drawable.ic_input_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAction(teacherSelection);
            }
        });

        elementTextVIew = findViewById(R.id.elementTextVIew);
        selectFragment(teacherSelection);
    }

    /***
    * Do we see the fab? Yes or no based on option
    */
    private void fabSetDisplay(FloatingActionButton fab, int teacherSelection){
        switch(teacherSelection){
            case R.id.bRes:
                Log.d(DEBUG_TAG, "bRes " + teacherSelection);
                break;
            case R.id.bQuizzes:
                Log.d(DEBUG_TAG, "bQuizzes " + teacherSelection);
                break;
            case R.id.bClassList:
                Log.d(DEBUG_TAG, "bClassList " + teacherSelection);
                fab.hide();
                break;
            case R.id.bAccountInfo:
                Log.d(DEBUG_TAG, "bAccountInfo " + teacherSelection);
                fab.hide();
                break;
            default:
                Log.d(DEBUG_TAG, "Nothing " + teacherSelection);

        }
    }

    //Display and use the correct fragment
    private void selectFragment(int teacherSelection){
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
                ft.addToBackStack(null);
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
            case R.id.bAccountInfo:
                Log.d(DEBUG_TAG, "bAccountInfo"+ teacherSelection);
                elementTextVIew.setText("Account Information");
                fragment = new FragmentAccountInfo();
                ft.replace(R.id.teacherElementFragment,fragment);
                break;
            default:
                Log.d(DEBUG_TAG, "Nothing " + teacherSelection);

        }
        ft.commit();
    }


    private void fabAction(int teacherSelection){
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();

        switch(teacherSelection){
            case R.id.bRes:
                Log.d(DEBUG_TAG, "bRes " + teacherSelection);
                fragment = new FragmentTeacherAddRes();
                ft.replace(R.id.teacherElementFragment, fragment);
                ft.addToBackStack(null); //So that the back button doesn't take you to the wrong place
                ft.commit();
                break;
            case R.id.bQuizzes:
                Log.d(DEBUG_TAG, "bQuizzes " + teacherSelection);
                fragment = new TeacherAddQuizTitleDescFragment();
                ft.replace(R.id.teacherElementFragment, fragment);
                break;
            default:
                Log.d(DEBUG_TAG, "Nothing " + teacherSelection);

        }
    }

    @Override
    public void onBackPressed(){
        Log.d(DEBUG_TAG, "Fragment Count = " + getSupportFragmentManager().getBackStackEntryCount());

        //If there are more than 2 fragments, pop the top
        if(getSupportFragmentManager().getBackStackEntryCount() <= 1)
        {
            finish();//super.onBackPressed();
        }
        else{
            getSupportFragmentManager().popBackStack();
            refreshFragment();
        }

    }
    //Refresh the fragment on the top of the stack
    private void refreshFragment(){
        Fragment cur = getCurrentFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(cur);
        ft.attach(cur);
        ft.commit();

    }

    private Fragment getCurrentFragment(){
        Fragment fragment = new TeacherResourcesFragment();
        List<Fragment> list = getSupportFragmentManager().getFragments();
        switch(teacherSelectionp2){
            case R.id.bRes:
                Log.d(DEBUG_TAG, "bRes " + teacherSelectionp2);
                fragment = new TeacherResourcesFragment();
                break;
            case R.id.bQuizzes:
                Log.d(DEBUG_TAG, "bQuizzes " + teacherSelectionp2);
                fragment = new TeacherQuizzesFragment();
                break;
            case R.id.bClassList:
                Log.d(DEBUG_TAG, "bClassList " + teacherSelectionp2);
                fragment = new TeacherClassRoster();
                break;
            case R.id.bAccountInfo:
                Log.d(DEBUG_TAG, "bAccountInfo"+ teacherSelectionp2);
                fragment = new FragmentAccountInfo();
                break;
            default:
                Log.d(DEBUG_TAG, "Nothing " + teacherSelectionp2);

        }

        return fragment; //list.get(list.size() - 1);
    }
}
