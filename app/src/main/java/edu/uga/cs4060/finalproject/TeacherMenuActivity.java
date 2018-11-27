package edu.uga.cs4060.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TeacherMenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button bRes, bQuizzes,bClassList,bSync;
    final String DEBUG_TAG = "TeacherMenuActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);

        bRes = findViewById(R.id.bRes); //id : 0
        bRes.setOnClickListener(this);
        bQuizzes = findViewById(R.id.bQuizzes); //id : 1
        bQuizzes.setOnClickListener(this);
        bClassList = findViewById(R.id.bClassList); //id : 2
        bClassList.setOnClickListener(this);
        bSync = findViewById(R.id.bSync); //id : 3
        bSync.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(getBaseContext(), TeacherMenuElement.class);
        if(v.getId() != R.id.bSync){
            Log.d(DEBUG_TAG, "Using Button ID" + v.getId());
            intent.putExtra("buttonID", v.getId());
            startActivity(intent);
        }else{
            Log.d(DEBUG_TAG, "Using Sync");
            Toast.makeText(getBaseContext(),"Syncing class",Toast.LENGTH_LONG).show();
        }
    }
}
