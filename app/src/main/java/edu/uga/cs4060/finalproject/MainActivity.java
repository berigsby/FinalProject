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

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";

    TextView textView;
    EditText editText;
    Button button, testingButton;

    DataSnapshot myDataSnapshot;

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

                //This is meant to be an example of how to do things with the database
                /*
                //create a teacher, a class for them, and some resources/quizzes and questions
                TeacherPojo steveTeacher = new TeacherPojo("Steve", "");
                steveTeacher = MyFirebaseHelper.create(myRef, steveTeacher);
                ClassPojo stevesClass = new ClassPojo("",steveTeacher.getTeacherId(),"This is Steves Class", "This class is aight");
                stevesClass = MyFirebaseHelper.create(myRef,stevesClass);
                ResourcePojo stevesRes1 = new ResourcePojo("",stevesClass.getClassId(),"Res1 for Steve", "this is test...");
                stevesRes1 = MyFirebaseHelper.create(myRef,stevesRes1);
                ResourcePojo stevesRes2 = new ResourcePojo("",stevesClass.getClassId(),"Res2 for Steve", "this is test also.");
                stevesRes2 = MyFirebaseHelper.create(myRef,stevesRes2);
                QuizPojo stevesQuiz1 = new QuizPojo("",stevesClass.getClassId(),"Quiz 1","First Quiz for Steves Class");
                stevesQuiz1 = MyFirebaseHelper.create(myRef, stevesQuiz1);
                QuizPojo stevesQuiz2 = new QuizPojo("",stevesClass.getClassId(),"Quiz 2","Second Quiz for Steves Class");
                stevesQuiz2 = MyFirebaseHelper.create(myRef, stevesQuiz2);
                for(int i = 0; i < 6; i ++){
                    String s = Integer.toString(i);
                    QuestionPojo questionPojo = new QuestionPojo("",stevesQuiz1.getQuizId(),"What is 0 + " + s,
                            s, Integer.toString(i+1), Integer.toString(i+2), Integer.toString(i+3), 1);
                    MyFirebaseHelper.create(myRef,questionPojo);
                }
                */

                String classId = "-LS3EQGm-8kZEW5ZDAw1"; //This would be obtained initally from the teacher. The Class Code

                /*
                //Create a new student
                StudentPojo benStudent = new StudentPojo("","Ben");
                benStudent = MyFirebaseHelper.create(myRef,benStudent);
                MyFirebaseHelper.enroll(myRef,benStudent.getStudentId(),classId);
                */

                /*
                //view the class resources
                List<ResourcePojo> bensClassRes = MyFirebaseHelper.getResourcesFromClassId(myDataSnapshot,classId);
                String value = "";
                for(ResourcePojo resourcePojo : bensClassRes){
                    value += resourcePojo.getTitle() + " " + resourcePojo.getText() + "\n";
                }
                textView.setText(value);
                */

                /*
                //view the classes quizzes
                List<QuizPojo> classQuizzes = MyFirebaseHelper.getQuizzesFromClassId(myDataSnapshot,classId);
                String value = "";
                for(QuizPojo quizPojo : classQuizzes){
                    value += quizPojo.getTitle() + " " + quizPojo.getDesc() + "\n";
                }
                textView.setText(value);
                */

                /*
                //view the quiz questions
                QuizPojo quizPojo = MyFirebaseHelper.getQuizzesFromClassId(myDataSnapshot, classId).get(0);
                List<QuestionPojo> questionPojos = MyFirebaseHelper.getQuestionsFromQuizId(myDataSnapshot,quizPojo.getQuizId());
                String value = "";
                for(QuestionPojo questionPojo : questionPojos){
                    value += questionPojo.getQuestionText() + " " + questionPojo.getAnswer1() + " " +
                            questionPojo.getAnswer2() + " " +  questionPojo.getAnswer3() + " " +  questionPojo.getAnswer4() + "\n";
                }
                textView.setText(value);
                */

                /*
                //This is how one would take a quiz
                String studentId = "-LS3HYIciNWJduRJAoq-"; //This would be obtainable through the current user
                QuizPojo quizPojo = MyFirebaseHelper.getQuizzesFromClassId(myDataSnapshot, classId).get(0);
                List<QuestionPojo> questionPojos = MyFirebaseHelper.getQuestionsFromQuizId(myDataSnapshot,quizPojo.getQuizId());
                int i = 1;
                for(QuestionPojo questionPojo : questionPojos){
                    QuizResultsToQuestionPojo quizResultsToQuestionPojo = new QuizResultsToQuestionPojo("",quizPojo.getQuizId(),questionPojo.getQuestionId(),studentId, i%4);
                    i ++;
                    quizResultsToQuestionPojo = MyFirebaseHelper.create(myRef,quizResultsToQuestionPojo);
                }
                MyFirebaseHelper.takeQuiz(myRef,studentId,quizPojo.getQuizId());
                */

                /*
                //This is how to view past quizzes taken
                String studentId = "-LS3HYIciNWJduRJAoq-"; //This would be obtainable through the current user
                List<QuizPojo> classQuizzesTaken = MyFirebaseHelper.getQuizzesTaken(myDataSnapshot,studentId,classId);
                String value = "";
                for(QuizPojo quizPojo : classQuizzesTaken){
                    value += quizPojo.getTitle() + " " + quizPojo.getDesc() + "\n";
                }
                textView.setText(value);
                */

                /*
                //This is how to view a particular quiz results
                String studentId = "-LS3HYIciNWJduRJAoq-"; //This would be obtainable through the current user
                QuizPojo quiz = MyFirebaseHelper.getQuizzesTaken(myDataSnapshot,studentId,classId).get(0);
                List<QuizResultsToQuestionPojo> quizQuestions = MyFirebaseHelper.getQuizResultsToQuestionFromIds(myDataSnapshot,quiz.getQuizId(),studentId,classId);
                String value = "";
                for(QuizResultsToQuestionPojo quizQuestion : quizQuestions){
                    String mark = "";
                    if(MyFirebaseHelper.getQuestion(myDataSnapshot,quizQuestion.getQuestionId()).getCorrectAnswerChoice() == quizQuestion.getUserAnswerChoice()){
                        mark = "Correct";
                    } else {
                        mark = "Wrong";
                    }
                    value += MyFirebaseHelper.getQuestion(myDataSnapshot, quizQuestion.getQuestionId()).getQuestionText() + " was " + mark + "\n";
                }
                textView.setText(value);
                */

            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = "test";

                myDataSnapshot = dataSnapshot;

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
