package edu.uga.cs4060.finalproject;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MyFirebaseHelper {

    public static List<TeacherPojo> getAllTeachers(DataSnapshot dataSnapshot){
        ArrayList<TeacherPojo> teacherPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("teacher").getChildren()){
            TeacherPojo teacherPojo = child.getValue(TeacherPojo.class);
            teacherPojos.add(teacherPojo);
        }

        return teacherPojos;
    }

    public static List<StudentPojo> getAllStudents(DataSnapshot dataSnapshot){
        ArrayList<StudentPojo> studentPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("student").getChildren()){
            StudentPojo studentPojo = child.getValue(StudentPojo.class);
            studentPojos.add(studentPojo);
        }

        return studentPojos;
    }

    public static List<ResourcePojo> getAllResources(DataSnapshot dataSnapshot){
        ArrayList<ResourcePojo> resourcePojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("resource").getChildren()){
            ResourcePojo resourcePojo = child.getValue(ResourcePojo.class);
            resourcePojos.add(resourcePojo);
        }

        return resourcePojos;
    }

    public static List<QuestionPojo> getAllQuestions(DataSnapshot dataSnapshot){
        ArrayList<QuestionPojo> questionPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("question").getChildren()){
            QuestionPojo questionPojo = child.getValue(QuestionPojo.class);
            questionPojos.add(questionPojo);
        }

        return questionPojos;
    }

    public static List<QuizResultsToQuestionPojo> getAllQuizResultsToQuestions(DataSnapshot dataSnapshot){
        ArrayList<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("quizResultsToQuestion").getChildren()){
            QuizResultsToQuestionPojo quizResultsToQuestionPojo = child.getValue(QuizResultsToQuestionPojo.class);
            quizResultsToQuestionPojos.add(quizResultsToQuestionPojo);
        }

        return quizResultsToQuestionPojos;
    }

    public static List<QuizPojo> getAllQuizzes(DataSnapshot dataSnapshot){
        ArrayList<QuizPojo> quizPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("quiz").getChildren()){
            QuizPojo quizPojo = child.getValue(QuizPojo.class);
            quizPojos.add(quizPojo);
        }

        return quizPojos;
    }

    public static List<ClassPojo> getAllClasses(DataSnapshot dataSnapshot){
        ArrayList<ClassPojo> classPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("class").getChildren()){
            ClassPojo classPojo = child.getValue(ClassPojo.class);
            classPojos.add(classPojo);
        }

        return classPojos;
    }

    public static TeacherPojo getTeacher(DataSnapshot dataSnapshot, String teacherId){
        TeacherPojo teacherPojo = dataSnapshot.child("teacher").child(teacherId).getValue(TeacherPojo.class);
        return teacherPojo;
    }

    public static StudentPojo getStudent(DataSnapshot dataSnapshot, String studentId){
        StudentPojo studentPojo = dataSnapshot.child("student").child(studentId).getValue(StudentPojo.class);
        return studentPojo;
    }

    public static ResourcePojo getResource(DataSnapshot dataSnapshot, String resourceId){
        ResourcePojo resourcePojo = dataSnapshot.child("resource").child(resourceId).getValue(ResourcePojo.class);
        return resourcePojo;
    }

    public static QuestionPojo getQuestion(DataSnapshot dataSnapshot, String questionId){
        QuestionPojo questionPojo = dataSnapshot.child("question").child(questionId).getValue(QuestionPojo.class);
        return questionPojo;
    }

    public static QuizPojo getQuiz(DataSnapshot dataSnapshot, String quizId){
        QuizPojo quizPojo = dataSnapshot.child("quiz").child(quizId).getValue(QuizPojo.class);
        return quizPojo;
    }

    public static ClassPojo getClass(DataSnapshot dataSnapshot, String classId){
        ClassPojo classPojo = dataSnapshot.child("class").child(classId).getValue(ClassPojo.class);
        return classPojo;
    }

    public static QuizResultsToQuestionPojo getQuizResultsToQuestion(DataSnapshot dataSnapshot, String quizResultsToQuestionId){
        QuizResultsToQuestionPojo quizResultsToQuestionPojo = dataSnapshot.child("quizResultsToQuestion").child(quizResultsToQuestionId).getValue(QuizResultsToQuestionPojo.class);
        return quizResultsToQuestionPojo;
    }

    public static <T extends FirebasePojo> void update(DatabaseReference myRef, T tClass){
        myRef.child(tClass.getDatabaseKey()).child(tClass.getId()).setValue(tClass);
    }

    public static <T extends  FirebasePojo> T create(DatabaseReference myRef, T tClass){
        DatabaseReference pushRef = myRef.child(tClass.getDatabaseKey()).push();
        String pushRefId = pushRef.getKey();
        tClass.setId(pushRefId);
        pushRef.setValue(tClass);
        return tClass;
    }

    private static void createRelationRow(DatabaseReference myRef, String relationTable,
                                         String firstName, String firstId, String secondName, String secondId){
        DatabaseReference pushRef = myRef.child(relationTable).push();
        pushRef.child(firstName).setValue(firstId);
        pushRef.child(secondName).setValue(secondId);
    }

    public static void enroll(DatabaseReference myRef, String studentId, String classId){
        createRelationRow(myRef,"enrollment","classId", classId,"studentId", studentId);
    }

    public static void takeQuiz(DatabaseReference myRef, String studentId, String quizId){
        createRelationRow(myRef,"quizResults","studentId", studentId, "quizId",quizId);
    }

    public static List<IdHolder> getRelationTable(DataSnapshot dataSnapshot, String relationTableName){
        ArrayList<IdHolder> idHolders = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child(relationTableName).getChildren()){
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders.add(idHolder);
        }//for
        return idHolders;
    }

    public static List<ResourcePojo> getResourcesFromClassId(DataSnapshot dataSnapshot, String classId){
        List<ResourcePojo> resourcePojos = getAllResources(dataSnapshot);

        for(ResourcePojo resourcePojo : resourcePojos){
            if(! resourcePojo.getClassId().equals(classId)){
                resourcePojos.remove(resourcePojo);
            }//if
        }//for
        return resourcePojos;
    }

    public static List<QuizPojo> getQuizzesFromClassId(DataSnapshot dataSnapshot, String classId){
        List<QuizPojo> quizPojos = getAllQuizzes(dataSnapshot);

        for(QuizPojo quizPojo : quizPojos){
            if(! quizPojo.getClassId().equals(classId)){
                quizPojos.remove(quizPojo);
            }//if
        }//for

        return quizPojos;
    }

    public static List<QuestionPojo> getQuestionsFromQuizId(DataSnapshot dataSnapshot, String quizId){
        List<QuestionPojo> questionPojos = getAllQuestions(dataSnapshot);

        for(QuestionPojo questionPojo : questionPojos){
            if(! questionPojo.getQuizId().equals(quizId)){
                questionPojos.remove(questionPojo);
            }//if
        }//for
        return questionPojos;
    }

    public static List<ClassPojo> getClassesFromTeacherId(DataSnapshot dataSnapshot, String teacherId){
        List<ClassPojo> classPojos = getAllClasses(dataSnapshot);

        for(ClassPojo classPojo : classPojos){
            if(! classPojo.getTeacherId().equals(teacherId)){
                classPojos.remove(classPojo);
            }//if
        }//for
        return classPojos;
    }

    public static List<ClassPojo> getClassesFromStudentId(DataSnapshot dataSnapshot, String studentId){
        ArrayList<ClassPojo> returnList = new ArrayList<>();

        List<IdHolder> idHolders = getRelationTable(dataSnapshot,"enrollment");
        for(IdHolder idHolder : idHolders){
            if(! idHolder.getStudentId().equals(studentId)){
                idHolders.remove(idHolder);
            }//if
        }//for

        for(int i = 0; i < idHolders.size(); i ++){
            returnList.add(getClass(dataSnapshot, idHolders.get(i).getClassId()));
        }

        return returnList;
    }

    public static List<QuizPojo> getQuizzesTaken(DataSnapshot dataSnapshot, String studentId, String classId){
        ArrayList<QuizPojo> returnList = new ArrayList<>();


        List<QuizPojo> quizPojos = getQuizzesFromClassId(dataSnapshot,classId);

        List<IdHolder> idHolders = getRelationTable(dataSnapshot,"quizResults");
        for(IdHolder idHolder : idHolders){
            for(QuizPojo quizPojo : quizPojos){
                if(idHolder.getQuizId().equals(quizPojo.getQuizId()) && idHolder.getStudentId().equals(studentId)){
                    returnList.add(quizPojo);
                }//if
            }//for
        }//for

        return returnList;
    }

    public static List<QuizResultsToQuestionPojo> getQuizResultsToQuestionFromIds(DataSnapshot dataSnapshot, String quizId, String studentId, String classId){
        ArrayList<QuizResultsToQuestionPojo> returnList = new ArrayList<>();

        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);

        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getStudentId().equals(studentId) &&
                    quizResultsToQuestionPojo.getQuizId().equals(quizId)){
                returnList.add(quizResultsToQuestionPojo);
            }//if
        }//for

        return returnList;
    }


}
