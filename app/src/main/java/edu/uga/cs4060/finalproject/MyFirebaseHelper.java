package edu.uga.cs4060.finalproject;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    public static String quizContinueable(DataSnapshot dataSnapshot, String classId, String studentId){
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        List<QuizPojo> quizzesIncomplete = new ArrayList<>();
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getStudentId().equals(studentId) &&
                    quizResultsToQuestionPojo.getUserAnswerChoice() == -1){
                quizzesIncomplete.add(getQuiz(dataSnapshot,quizResultsToQuestionPojo.getQuizId()));
            }
        }

        List<QuizPojo> quizzesTaken = getQuizzesTaken(dataSnapshot,studentId,classId);
        for(QuizPojo quizTaken : quizzesTaken){
            if(quizzesIncomplete.contains(quizTaken)){
                return quizTaken.getQuizId();
            }
        }
        return "";
    }

    public static List<QuestionPojo> continueQuiz(DataSnapshot dataSnapshot, String studentId, String quizId){
        List<QuestionPojo> questions = getQuestionsFromQuizId(dataSnapshot,quizId);
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getQuizResultsToQuestionFromIds(dataSnapshot,quizId,studentId);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getUserAnswerChoice() != -1){
                questions.remove(getQuestion(dataSnapshot,quizResultsToQuestionPojo.getQuestionId()));
            }
        }
        Collections.shuffle(questions);
        return questions;
    }

    public static List<QuestionPojo> initiateQuiz(DatabaseReference myRef, DataSnapshot dataSnapshot, String studentId, String quizId){
        takeQuiz(myRef,studentId,quizId);
        List<QuestionPojo> questions = getQuestionsFromQuizId(dataSnapshot,quizId);
        for(QuestionPojo question : questions){
            QuizResultsToQuestionPojo quizResultsToQuestionPojo = new QuizResultsToQuestionPojo("",quizId,question.getQuestionId(),studentId,-1);
        }
        Collections.shuffle(questions);
        return questions;
    }

    private static void takeQuiz(DatabaseReference myRef, String studentId, String quizId){
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

    public static List<StudentPojo> getStudentsFromClassId(DataSnapshot dataSnapshot, String classId){
        ArrayList<StudentPojo> returnList = new ArrayList<>();
        List<IdHolder> list = getRelationTable(dataSnapshot, "enrollment");
        for(IdHolder item : list){
            if(! item.getClassId().equals(classId)){
                list.remove(item);
            }
        }
        for(int i = 0; i < list.size(); i++){
            returnList.add(getStudent(dataSnapshot,list.get(i).getStudentId()));
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

    public static List<QuizResultsToQuestionPojo> getQuizResultsToQuestionFromIds(DataSnapshot dataSnapshot, String quizId, String studentId){
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

    public static boolean accountExists(DataSnapshot dataSnapshot, String accountId){
        boolean exists = false;
        List<TeacherPojo> teachers = getAllTeachers(dataSnapshot);
        for(TeacherPojo teacher : teachers){
            if(teacher.getTeacherId().equals(accountId)) exists = true;
        }
        if(exists) return true;

        List<StudentPojo> students = getAllStudents(dataSnapshot);
        for(StudentPojo student : students){
            if(student.getStudentId().equals(accountId)) exists = true;
        }
        if(exists) return true;
        return false;
    }

    public static <T extends FirebasePojo> void createAccount(DatabaseReference myRef, T tClass){
        DatabaseReference pushRef = myRef.child(tClass.getDatabaseKey()).child(tClass.getId());
        pushRef.setValue(tClass);
    }

    public static void removeTeacher(DatabaseReference myRef, DataSnapshot dataSnapshot, String teacherId){
        removeItem(myRef,"teacher",teacherId);
        List<ClassPojo> classes = getClassesFromTeacherId(dataSnapshot, teacherId);
        for(ClassPojo classPojo : classes){
            removeClass(myRef,dataSnapshot,classPojo.getClassId());
        }
    }

    public static void removeClass(DatabaseReference myRef, DataSnapshot dataSnapshot, String classId){
        removeItem(myRef,"class",classId);
        List<ResourcePojo> resources = getResourcesFromClassId(dataSnapshot, classId);
        for(ResourcePojo resource : resources){
            removeResource(myRef,resource.getResourceId());
        }
        List<QuizPojo> quizzes = getQuizzesFromClassId(dataSnapshot,classId);
        for(QuizPojo quiz : quizzes){
            removeQuiz(myRef, dataSnapshot, quiz.getQuizId());
        }
    }

    public static void removeResource(DatabaseReference myRef, String resourceId){
        removeItem(myRef,"resource",resourceId);
    }

    public static void removeQuiz(DatabaseReference myRef, DataSnapshot dataSnapshot, String quizId){
        removeItem(myRef, "quiz", quizId);
        //TODO remove linked data...
        /*List<IdHolder> myList = getRelationTable(dataSnapshot,"quizResults");
        for(IdHolder item : myList){
            String itemQuizId = item.getQuizId();
            if(itemQuizId == null) continue;
            if(itemQuizId.equals(quizId)){
                removeItem(myRef,"quizResults",item.get);
            }
        }*/
        List<QuestionPojo> questions = getQuestionsFromQuizId(dataSnapshot, quizId);
        for(QuestionPojo question : questions){
            removeQuestion(myRef, dataSnapshot, question.getQuestionId());
        }
    }

    public static void removeQuestion(DatabaseReference myRef, DataSnapshot dataSnapshot, String questionId){
        removeItem(myRef,"question",questionId);
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getQuestionId().equals(questionId)){
                removeQuizResultsToQuestion(myRef, quizResultsToQuestionPojo.getQuizResultsToQuestionId());
            }
        }
    }

    public static void removeQuizResultsToQuestion(DatabaseReference myRef, String quizResulstToQuestionId){
        removeItem(myRef,"quizResultsToQuestion",quizResulstToQuestionId);
    }

    public static void removeStudent(DatabaseReference myRef, DataSnapshot dataSnapshot, String studentId){
        removeItem(myRef,"student",studentId);
        //TODO remove taken quizzes...
        //TODO remove enrollment status
    }

    public static void removeItem(DatabaseReference myRef, String databaseKey, String myId){
        myRef.child(databaseKey).child(myId).removeValue();
    }
}
