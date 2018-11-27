package edu.uga.cs4060.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;

public class PopUpTeacherOrStudent extends DialogFragment {
    private String teacherOrStudent;
    private String name;
    private String theid;
    private DatabaseReference myRef;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you a student or a teacher?")
                .setPositiveButton("Student", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        teacherOrStudent = "student";
                        MyFirebaseHelper.createAccount(myRef, new StudentPojo(theid, name));
                    }
                })
                .setNegativeButton("Teacher", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        teacherOrStudent = "teacher";
                        MyFirebaseHelper.createAccount(myRef, new TeacherPojo(name, theid));
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setMyRef(DatabaseReference myRef){
        this.myRef = myRef;
    }

    public String getTeacherOrStudent() {
        return teacherOrStudent;
    }

    public void setTeacherOrStudent(String teacherOrStudent) {
        this.teacherOrStudent = teacherOrStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheId() {
        return theid;
    }

    public void setTheId(String id) {
        this.theid = id;
    }
}
