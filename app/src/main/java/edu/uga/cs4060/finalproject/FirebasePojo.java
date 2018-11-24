package edu.uga.cs4060.finalproject;

import com.google.firebase.database.Exclude;

public class FirebasePojo{

    @Exclude
    public String getDatabaseKey(){
        return "testing";
    }

    @Exclude
    public String getId(){
        return "testingId";
    }

    @Exclude
    public void setId(String theId){

    }
}
