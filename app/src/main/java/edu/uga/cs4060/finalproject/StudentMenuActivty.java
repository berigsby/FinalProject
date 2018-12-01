package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class StudentMenuActivty extends AppCompatActivity implements View.OnClickListener {

    Button bRes2, bQuizzes2,bQuizHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu_activty);

        bRes2 = findViewById(R.id.bRes2); //id : 0
        bRes2.setOnClickListener(this);
        bQuizzes2 = findViewById(R.id.bQuizzes2); //id : 1
        bQuizzes2.setOnClickListener(this);
        bQuizHistory = findViewById(R.id.bQuizHistory); //id : 2
        bQuizHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(getBaseContext(), StudentActivityMenuElement.class);
        if(v.getId() != R.id.bSync){
            //Log.d(DEBUG_TAG, "Using Button ID" + v.getId());
            intent.putExtra("buttonID", v.getId());
            startActivity(intent);
        }
    }

}
