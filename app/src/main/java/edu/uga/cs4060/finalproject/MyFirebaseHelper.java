package edu.uga.cs4060.finalproject;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyFirebaseHelper {

    /**
     *  Returns all the teachers in the datasnapshot
     * @param dataSnapshot
     * @return list of TeacherPojo
     */
    public static List<TeacherPojo> getAllTeachers(DataSnapshot dataSnapshot){
        ArrayList<TeacherPojo> teacherPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("teacher").getChildren()){
            TeacherPojo teacherPojo = child.getValue(TeacherPojo.class);
            teacherPojos.add(teacherPojo);
        }

        return teacherPojos;
    }

    /**
     * Returns all the students in the datasnapshot
     * @param dataSnapshot
     * @return List of StudentPojo
     */
    public static List<StudentPojo> getAllStudents(DataSnapshot dataSnapshot){
        ArrayList<StudentPojo> studentPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("student").getChildren()){
            StudentPojo studentPojo = child.getValue(StudentPojo.class);
            studentPojos.add(studentPojo);
        }

        return studentPojos;
    }

    /**
     * returns all the resources in the datasnapshot
     * @param dataSnapshot
     * @return List of ResourcePojo
     */
    public static List<ResourcePojo> getAllResources(DataSnapshot dataSnapshot){
        ArrayList<ResourcePojo> resourcePojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("resource").getChildren()){
            ResourcePojo resourcePojo = child.getValue(ResourcePojo.class);
            resourcePojos.add(resourcePojo);
        }

        return resourcePojos;
    }

    /**
     * returns all the questions in the datasnapshot
     * @param dataSnapshot
     * @return list of Question Pojo
     */
    public static List<QuestionPojo> getAllQuestions(DataSnapshot dataSnapshot){
        ArrayList<QuestionPojo> questionPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("question").getChildren()){
            QuestionPojo questionPojo = child.getValue(QuestionPojo.class);
            questionPojos.add(questionPojo);
        }

        return questionPojos;
    }

    /**
     * returns all the quiz results to specific questions in the datasbapshot
     * @param dataSnapshot
     * @return list of QuizResultsToQuestionPojo
     */
    public static List<QuizResultsToQuestionPojo> getAllQuizResultsToQuestions(DataSnapshot dataSnapshot){
        ArrayList<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("quizResultsToQuestion").getChildren()){
            QuizResultsToQuestionPojo quizResultsToQuestionPojo = child.getValue(QuizResultsToQuestionPojo.class);
            quizResultsToQuestionPojos.add(quizResultsToQuestionPojo);
        }

        return quizResultsToQuestionPojos;
    }

    /**
     * returns all the quizzes in the datasnapshot
     * @param dataSnapshot
     * @return list of QuizPojo
     */
    public static List<QuizPojo> getAllQuizzes(DataSnapshot dataSnapshot){
        ArrayList<QuizPojo> quizPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("quiz").getChildren()){
            QuizPojo quizPojo = child.getValue(QuizPojo.class);
            quizPojos.add(quizPojo);
        }

        return quizPojos;
    }

    /**
     * returns all the classes in the datasnapshot
     * @param dataSnapshot
     * @return List of ClassPojo
     */
    public static List<ClassPojo> getAllClasses(DataSnapshot dataSnapshot){
        ArrayList<ClassPojo> classPojos = new ArrayList<>();

        for(DataSnapshot child : dataSnapshot.child("class").getChildren()){
            ClassPojo classPojo = child.getValue(ClassPojo.class);
            classPojos.add(classPojo);
        }

        return classPojos;
    }

    /**
     * Returns the teacherpojo object with teacherId
     * @param dataSnapshot
     * @param teacherId
     * @return TeacherPojo
     */
    public static TeacherPojo getTeacher(DataSnapshot dataSnapshot, String teacherId){
        TeacherPojo teacherPojo = dataSnapshot.child("teacher").child(teacherId).getValue(TeacherPojo.class);
        return teacherPojo;
    }

    /**
     * returns the studentpojo object with the studentid
     * @param dataSnapshot
     * @param studentId
     * @return StudentPojo
     */
    public static StudentPojo getStudent(DataSnapshot dataSnapshot, String studentId){
        StudentPojo studentPojo = dataSnapshot.child("student").child(studentId).getValue(StudentPojo.class);
        return studentPojo;
    }

    /**
     * Returns the resourcepojo with the resourceid
     * @param dataSnapshot
     * @param resourceId
     * @return ResourcePojo
     */
    public static ResourcePojo getResource(DataSnapshot dataSnapshot, String resourceId){
        ResourcePojo resourcePojo = dataSnapshot.child("resource").child(resourceId).getValue(ResourcePojo.class);
        return resourcePojo;
    }

    /**
     * Returns the question with the questionId
     * @param dataSnapshot
     * @param questionId
     * @return QuestionPojo
     */
    public static QuestionPojo getQuestion(DataSnapshot dataSnapshot, String questionId){
        QuestionPojo questionPojo = dataSnapshot.child("question").child(questionId).getValue(QuestionPojo.class);
        return questionPojo;
    }

    /**
     * Returns the QuizPojo with quizid
     * @param dataSnapshot
     * @param quizId
     * @return QuizPojo
     */
    public static QuizPojo getQuiz(DataSnapshot dataSnapshot, String quizId){
        QuizPojo quizPojo = dataSnapshot.child("quiz").child(quizId).getValue(QuizPojo.class);
        return quizPojo;
    }

    /**
     * Returns the ClassPojo with the classId
     * @param dataSnapshot
     * @param classId
     * @return ClassPojo
     */
    public static ClassPojo getClass(DataSnapshot dataSnapshot, String classId){
        ClassPojo classPojo = dataSnapshot.child("class").child(classId).getValue(ClassPojo.class);
        return classPojo;
    }

    /**
     * Returns the quizResultToQuestionPojo with quizResultToQuestionPojoId
     * @param dataSnapshot
     * @param quizResultsToQuestionId
     * @return QuizResultToQuestionPojo
     */
    public static QuizResultsToQuestionPojo getQuizResultsToQuestion(DataSnapshot dataSnapshot, String quizResultsToQuestionId){
        QuizResultsToQuestionPojo quizResultsToQuestionPojo = dataSnapshot.child("quizResultsToQuestion").child(quizResultsToQuestionId).getValue(QuizResultsToQuestionPojo.class);
        return quizResultsToQuestionPojo;
    }

    /**
     * Updates the database with the object passed in
     * These can be a: QuestionPojo, QuizPojo, QuizResultsToQuestionPojo, ResourcePojo,
     *  StudentPojo, or TeacherPojo
     * @param myRef
     * @param tClass
     * @param <T> Must extend FirebasePojo
     */
    public static <T extends FirebasePojo> void update(DatabaseReference myRef, T tClass){
        myRef.child(tClass.getDatabaseKey()).child(tClass.getId()).setValue(tClass);
    }

    /**
     * Creates an object in the database with the object passed in
     * These can be a: QuestionPojo, QuizPojo, QuizResultsToQuestionPojo, ResourcePojo,
     *  StudentPojo, or TeacherPojo
     * @param myRef
     * @param tClass
     * @param <T> Must extend FirebasePojo
     */
    public static <T extends  FirebasePojo> T create(DatabaseReference myRef, T tClass){
        DatabaseReference pushRef = myRef.child(tClass.getDatabaseKey()).push();
        String pushRefId = pushRef.getKey();
        tClass.setId(pushRefId);
        pushRef.setValue(tClass);
        return tClass;
    }

    /**
     * This creates a relationalrow in the database
     * @param myRef
     * @param relationTable name of the table in firebase
     * @param firstName the first key in firebase
     * @param firstId the first value in firebase
     * @param secondName the second key in firebase
     * @param secondId the second value in firebase
     */
    private static void createRelationRow(DatabaseReference myRef, String relationTable,
                                         String firstName, String firstId, String secondName, String secondId){
        DatabaseReference pushRef = myRef.child(relationTable).push();
        pushRef.child(firstName).setValue(firstId);
        pushRef.child(secondName).setValue(secondId);
    }

    /**
     * This will create enroll studentid in classid by creating a row in the enrollement table
     * @param myRef
     * @param studentId
     * @param classId
     */
    public static void enroll(DatabaseReference myRef, String studentId, String classId){
        createRelationRow(myRef,"enrollment","classId", classId,"studentId", studentId);
    }

    /**
     * This returns the quizId of an incomplete quiz if one exists for the user, and "" otherwise
     * @param dataSnapshot
     * @param classId
     * @param studentId
     * @return String quizId if exists, "" otherwise
     */
    public static String quizContinueable(DataSnapshot dataSnapshot, String classId, String studentId){
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        List<QuizPojo> quizzesIncomplete = new ArrayList<>();
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getStudentId().equals(studentId) &&
                    quizResultsToQuestionPojo.getUserAnswerChoice() <= 0){
                return quizResultsToQuestionPojo.getQuizId();
                //quizzesIncomplete.add(getQuiz(dataSnapshot,quizResultsToQuestionPojo.getQuizId()));
            }
        }

        //List<QuizPojo> quizzesTaken = getQuizzesTaken(dataSnapshot,studentId,classId);
        //for(QuizPojo quizTaken : quizzesTaken){
        //    if(quizzesIncomplete.contains(quizTaken)){
        //        return quizTaken.getQuizId();
        //    }
        //}
        return "";
    }

    /**
     * This returns a list of the questions that have yet to be answered for studentid of quizid
     * @param dataSnapshot
     * @param studentId
     * @param quizId
     * @return list of questionPojo yet to be answered in no order
     */
    private static List<QuestionPojo> continueQuiz(DataSnapshot dataSnapshot, String studentId, String quizId){
        List<QuestionPojo> questions = getQuestionsFromQuizId(dataSnapshot,quizId);
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getQuizResultsToQuestionFromIds(dataSnapshot,quizId,studentId);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getUserAnswerChoice() <= 0){
                questions.remove(getQuestion(dataSnapshot,quizResultsToQuestionPojo.getQuestionId()));
            }
        }
        return questions;
    }

    /**
     * This initiates a quiz in firebase by creating the quizResultsToQuestionPojo objects in teh database
     * with the default values of -1 in the userAnswerChoice
     * This should be followed up with getNextQuestion
     * @param myRef
     * @param dataSnapshot
     * @param studentId
     * @param quizId
     */
    public static void initiateQuiz(DatabaseReference myRef, DataSnapshot dataSnapshot, String studentId, String quizId){
        takeQuiz(myRef,studentId,quizId);
        List<QuestionPojo> questions = getQuestionsFromQuizId(dataSnapshot,quizId);
        Collections.shuffle(questions);
        int i = 0;
        for(QuestionPojo question : questions){
            int userAnswerChoice = -1;
            if(i == 0) userAnswerChoice = 0;
            else userAnswerChoice = -1;
            i++;
            QuizResultsToQuestionPojo quizResultsToQuestionPojo = new QuizResultsToQuestionPojo("",quizId,question.getQuestionId(),studentId,userAnswerChoice);
            MyFirebaseHelper.create(myRef,quizResultsToQuestionPojo);
        }
    }

    /**
     * This returns the questionPojo of the next Question in an active quiz.
     * It will also set the next question quiz, so recall this method for every question.
     * This will return null if the quiz is complete
     *
     * @param myRef
     * @param dataSnapshot
     * @param quizId
     * @param studentId
     * @return QuestionPojo of next Question in quiz, or null if quiz is complete
     */
    public static QuestionPojo getNextQuestion(DatabaseReference myRef, DataSnapshot dataSnapshot, String quizId, String studentId){
        String quizContinueable = quizContinueable(dataSnapshot,getQuiz(dataSnapshot,quizId).getClassId(),studentId);
        if(quizContinueable.equals("")) return null;
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getQuizResultsToQuestionFromIds(dataSnapshot, quizId, studentId);
        List<QuizResultsToQuestionPojo> returnList = new ArrayList<>();
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getUserAnswerChoice() <= 0){
                returnList.add(quizResultsToQuestionPojo);
            }
        }

        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : returnList){
            if(quizResultsToQuestionPojo.getUserAnswerChoice() == 0){
                if(returnList.size() > 1) {
                    returnList.remove(quizResultsToQuestionPojo);
                    //Collections.shuffle(returnList);
                    //returnList.get(0).setUserAnswerChoice(0);
                    //update(myRef, returnList.get(0));
                    return getQuestion(dataSnapshot, quizResultsToQuestionPojo.getQuestionId());
                } else {
                    return getQuestion(dataSnapshot, quizResultsToQuestionPojo.getQuestionId());
                }
            }
        }
        return null;
    }

    public static boolean markQuestion(DatabaseReference myRef, DataSnapshot dataSnapshot, QuestionPojo question, String studentId, int ansChoice){
        boolean rv = false;
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getQuizResultsToQuestionFromIds(dataSnapshot,question.getQuizId(),studentId);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getQuestionId().equals(question.getQuestionId())){
                quizResultsToQuestionPojo.setUserAnswerChoice(ansChoice);
                update(myRef,quizResultsToQuestionPojo);
                if(quizResultsToQuestionPojo.getUserAnswerChoice() == question.getCorrectAnswerChoice()) {
                    rv = true;
                    break;
                } else {
                    rv = false;
                    break;
                }
            }
        }

        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos2 = getQuizResultsToQuestionFromIds(dataSnapshot, question.getQuizId(), studentId);
        List<QuizResultsToQuestionPojo> returnList = new ArrayList<>();
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos2){
            if(quizResultsToQuestionPojo.getUserAnswerChoice() < 0){
                returnList.add(quizResultsToQuestionPojo);
            }
        }
        if(returnList.size() == 0){
            return false;
        }

        Collections.shuffle(returnList);
        returnList.get(0).setUserAnswerChoice(0);
        update(myRef, returnList.get(0));
        return true;
        /*
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : returnList){
            if(quizResultsToQuestionPojo.getUserAnswerChoice() == 0){
                if(returnList.size() > 1) {
                    returnList.remove(quizResultsToQuestionPojo);
                    Collections.shuffle(returnList);
                    returnList.get(0).setUserAnswerChoice(0);
                    update(myRef, returnList.get(0));
                    break;
                } else {
                    break;
                }
            }
        }*/
        //return rv;
    }

    /**
     * This is used to update the QuizResults relational table
     * @param myRef
     * @param studentId
     * @param quizId
     */
    private static void takeQuiz(DatabaseReference myRef, String studentId, String quizId){
        createRelationRow(myRef,"quizResults","studentId", studentId, "quizId",quizId);
    }

    /**
     * This is used to get a entire particular relationTable
     * @param dataSnapshot
     * @param relationTableName name of table in Firebase
     * @return list of IdHolder
     */
    private static List<IdHolder> getRelationTable(DataSnapshot dataSnapshot, String relationTableName){
        ArrayList<IdHolder> idHolders = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child(relationTableName).getChildren()){
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders.add(idHolder);
        }//for
        return idHolders;
    }

    /**
     *  This returns all the resources in classId
     * @param dataSnapshot
     * @param classId
     * @return list of ResourcePojo
     */
    public static List<ResourcePojo> getResourcesFromClassId(DataSnapshot dataSnapshot, String classId){
        List<ResourcePojo> resourcePojos = getAllResources(dataSnapshot);
        List<ResourcePojo> returnList = new ArrayList<>();

        for(ResourcePojo resourcePojo : resourcePojos){
            if(resourcePojo.getClassId().equals(classId)){
                returnList.add(resourcePojo);
                //!resourcePojos.remove(resourcePojo);
            }//if
        }//for
        return returnList;
    }

    /**
     * This returns all the quizzes in classId
     * @param dataSnapshot
     * @param classId
     * @return list of QuizPojo
     */
    public static List<QuizPojo> getQuizzesFromClassId(DataSnapshot dataSnapshot, String classId){
        List<QuizPojo> quizPojos = getAllQuizzes(dataSnapshot);
        List<QuizPojo> returnList = new ArrayList<>();

        for(QuizPojo quizPojo : quizPojos){
            if(quizPojo.getClassId().equals(classId)){
                returnList.add(quizPojo);
                //!quizPojos.remove(quizPojo);
            }//if
        }//for

        return returnList;
    }

    /**
     * This returns all the questions within a quiz
     * @param dataSnapshot
     * @param quizId
     * @return List of QuestionPojo
     */
    public static List<QuestionPojo> getQuestionsFromQuizId(DataSnapshot dataSnapshot, String quizId){
        List<QuestionPojo> questionPojos = getAllQuestions(dataSnapshot);
        List<QuestionPojo> returnList = new ArrayList<>();
        for(QuestionPojo questionPojo : questionPojos){
            if(questionPojo.getQuizId().equals(quizId)){
                returnList.add(questionPojo);
            }//if
        }//for
        return returnList;
    }

    /**
     * This returns all the classes linked to teacherId
     * @param dataSnapshot
     * @param teacherId
     * @return List of ClassPojo
     */
    public static List<ClassPojo> getClassesFromTeacherId(DataSnapshot dataSnapshot, String teacherId){
        List<ClassPojo> classPojos = getAllClasses(dataSnapshot);
        List<ClassPojo> returnList = new ArrayList<>();

        for(ClassPojo classPojo : classPojos){
            if(classPojo.getTeacherId().equals(teacherId)){
                returnList.add(classPojo);
                //!classPojos.remove(classPojo);
            }//if
        }//for
        return returnList;
    }

    /**
     * This returns all the classes the studentId is enrolled in
     * @param dataSnapshot
     * @param studentId
     * @return List of ClassPojo
     */
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

    /**
     * This returns all students of classId
     * @param dataSnapshot
     * @param classId
     * @return List of StudentPojo
     */
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

    /**
     * This returns all the quizzes taken by the studentId in classId
     * @param dataSnapshot
     * @param studentId
     * @param classId
     * @return List of QuizPojo
     */
    public static List<QuizPojo> getQuizzesTaken(DataSnapshot dataSnapshot, String studentId, String classId){
        ArrayList<QuizPojo> returnList = new ArrayList<>();


        List<QuizPojo> quizPojos = getQuizzesFromClassId(dataSnapshot,classId);

        List<IdHolder> idHolders = getRelationTable(dataSnapshot,"quizResults");
        for(IdHolder idHolder : idHolders){
            for(QuizPojo quizPojo : quizPojos){
                if(idHolder.getQuizId().equals(quizPojo.getQuizId()) && idHolder.getStudentId().equals(studentId)){
                    List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getQuizResultsToQuestionFromIds(dataSnapshot,quizPojo.getQuizId(),studentId);
                    boolean zeroFound = false;
                    for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
                        if( quizResultsToQuestionPojo.getUserAnswerChoice() <= 0){
                            zeroFound = true;
                        }
                    }
                    if(!zeroFound) {
                        returnList.add(quizPojo);
                    }
                }//if
            }//for
        }//for

        return returnList;
    }

    /**
     * This returns all the quizzes not taken nor started by the studentid in classid
     * @param dataSnapshot
     * @param studentId
     * @param classId
     * @return List of QuizPojo
     */
    public static List<QuizPojo> getQuizzesLeft(DataSnapshot dataSnapshot, String studentId, String classId){
        List<QuizPojo> allQuizzes = getQuizzesFromClassId(dataSnapshot, classId);
        List<QuizPojo> quizzesTaken = getQuizzesTaken(dataSnapshot,studentId,classId);
        List<QuizPojo> returnList = new ArrayList<>();
        for( QuizPojo allQuiz : allQuizzes){
            boolean found = false;
            for(QuizPojo quizTaken : quizzesTaken) {
                if(allQuiz.getQuizId().equals(quizTaken.getQuizId())){
                    found = true;
                    //allQuizzes.remove(allQuiz);
                }
            }
            if(!found){
                returnList.add(allQuiz);
            }
        }
        return returnList;
    }

    /**
     *  This returns all the quizResultsToQuestionsPojos linked to the quizId of studentId
     *  This essentially returns all the answers the user selected of quizId
     * @param dataSnapshot
     * @param quizId
     * @param studentId
     * @return List of QuizResultsToQuestionsPojo
     */
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

    /**
     * This returns true if the account exists, false else
     * @param dataSnapshot
     * @param accountId
     * @return boolean true if account exists, false else
     */
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

    /**
     * This creats a new account of type T
     * T should be a teacherPojo or studentPojo
     * @param myRef
     * @param tClass
     * @param <T> extends FirebasePojo
     */
    public static <T extends FirebasePojo> void createAccount(DatabaseReference myRef, T tClass){
        DatabaseReference pushRef = myRef.child(tClass.getDatabaseKey()).child(tClass.getId());
        pushRef.setValue(tClass);
    }

    public static String getGrade(DataSnapshot myDataSnapshot, String quizId, String studentId){
        int numOfQuestions = 0;
        int numCorrect = 0;

        List<QuestionPojo> questionPojos = getQuestionsFromQuizId(myDataSnapshot,quizId);
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getQuizResultsToQuestionFromIds(myDataSnapshot,quizId,studentId);

        for(QuestionPojo questionPojo : questionPojos){
            for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
                if(questionPojo.getQuestionId().equals(quizResultsToQuestionPojo.getQuestionId())){
                    numOfQuestions ++;
                    if(questionPojo.getCorrectAnswerChoice() == quizResultsToQuestionPojo.getUserAnswerChoice()){
                        numCorrect ++;
                    }
                }
            }
        }
        String rv = " " + numCorrect + " correct out of " + numOfQuestions + ".";
        return rv;
    }//getGrade

    /**
     * This removes the teacherId, and any classes they have, any resources, quizzes,
     * questions and question relations in those classes from firebase
     * @param myRef
     * @param dataSnapshot
     * @param teacherId
     */
    public static void removeTeacher(DatabaseReference myRef, DataSnapshot dataSnapshot, String teacherId){
        removeItem(myRef,"teacher",teacherId);
        List<ClassPojo> classes = getClassesFromTeacherId(dataSnapshot, teacherId);
        for(ClassPojo classPojo : classes){
            removeClass(myRef,dataSnapshot,classPojo.getClassId());
        }
    }

    /**
     * This removes the classId, and any resources, quizzes, questions and question relations in that class from firebase
     * @param myRef
     * @param dataSnapshot
     * @param classId
     */
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

        ArrayList<IdHolder> idHolders2 = new ArrayList<>();
        ArrayList<String> ids2 = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child("enrollment").getChildren()){
            ids2.add(child.getKey());
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders2.add(idHolder);
        }//for
        for(int i = 0; i < idHolders2.size(); i++){
            String itemClassId = idHolders2.get(i).getClassId();
            if(itemClassId == null) continue;
            if(itemClassId.equals(classId)){
                removeItem(myRef,"enrollment",ids2.get(i));
            }
        }
    }

    /**
     * This removes the rsourceId from firebase
     * @param myRef
     * @param resourceId
     */
    public static void removeResource(DatabaseReference myRef, String resourceId){
        removeItem(myRef,"resource",resourceId);
    }

    /**
     * This removes the quizId, question and any questionRelations from firebase
     * @param myRef
     * @param dataSnapshot
     * @param quizId
     */
    public static void removeQuiz(DatabaseReference myRef, DataSnapshot dataSnapshot, String quizId){
        removeItem(myRef, "quiz", quizId);

        ArrayList<IdHolder> idHolders = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child("quizResults").getChildren()){
            ids.add(child.getKey());
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders.add(idHolder);
        }//for

        for(int i = 0; i < idHolders.size(); i++){
            String itemQuizId = idHolders.get(i).getQuizId();
            if(itemQuizId == null) continue;
            if(itemQuizId.equals(quizId)){
                removeItem(myRef,"quizResults",ids.get(i));
            }
        }

        List<QuestionPojo> questions = getQuestionsFromQuizId(dataSnapshot, quizId);
        for(QuestionPojo question : questions){
            removeQuestion(myRef, dataSnapshot, question.getQuestionId());
        }

        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getQuizId().equals(quizId)){
                removeQuizResultsToQuestion(myRef,quizResultsToQuestionPojo.getQuizResultsToQuestionId());
            }
        }
    }

    /**
     * This removes teh questionId and any questionRelations from firebase
     * @param myRef
     * @param dataSnapshot
     * @param questionId
     */
    public static void removeQuestion(DatabaseReference myRef, DataSnapshot dataSnapshot, String questionId){
        removeItem(myRef,"question",questionId);
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getQuestionId().equals(questionId)){
                removeQuizResultsToQuestion(myRef, quizResultsToQuestionPojo.getQuizResultsToQuestionId());
            }
        }
    }

    /**
     * Removes all the questions answered by studentId for quizId and associtations from the firebase
     * @param myRef
     * @param dataSnapshot
     * @param studentId
     * @param quizId
     */
    public static void removeAnsweredQuestions(DatabaseReference myRef, DataSnapshot dataSnapshot, String studentId, String quizId){
        ArrayList<IdHolder> idHolders = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child("quizResults").getChildren()){
            ids.add(child.getKey());
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders.add(idHolder);
        }//for
        for(int i = 0; i < idHolders.size(); i++){
            String itemStudentId = idHolders.get(i).getStudentId();
            String itemQuizId = idHolders.get(i).getQuizId();
            if(itemQuizId == null) continue;
            if(itemStudentId == null) continue;
            if(itemStudentId.equals(studentId) && itemQuizId.equals(quizId)){
                removeItem(myRef,"quizResults",ids.get(i));
            }
        }
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getStudentId().equals(studentId) && quizResultsToQuestionPojo.getQuizId().equals(quizId)){
                removeQuizResultsToQuestion(myRef,quizResultsToQuestionPojo.getQuizResultsToQuestionId());
            }
        }
    }

    /**
     * This removes the quizReulstsToQuestionId from firebase
     * @param myRef
     * @param quizResulstToQuestionId
     */
    public static void removeQuizResultsToQuestion(DatabaseReference myRef, String quizResulstToQuestionId){
        removeItem(myRef,"quizResultsToQuestion",quizResulstToQuestionId);
    }

    /**
     * This removes the studentId and any student questionrelaitons from firebase
     * @param myRef
     * @param dataSnapshot
     * @param studentId
     */
    public static void removeStudent(DatabaseReference myRef, DataSnapshot dataSnapshot, String studentId){
        removeItem(myRef,"student",studentId);
        ArrayList<IdHolder> idHolders = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child("quizResults").getChildren()){
            ids.add(child.getKey());
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders.add(idHolder);
        }//for
        for(int i = 0; i < idHolders.size(); i++){
            String itemQuizId = idHolders.get(i).getStudentId();
            if(itemQuizId == null) continue;
            if(itemQuizId.equals(studentId)){
                removeItem(myRef,"quizResults",ids.get(i));
            }
        }
        List<QuizResultsToQuestionPojo> quizResultsToQuestionPojos = getAllQuizResultsToQuestions(dataSnapshot);
        for(QuizResultsToQuestionPojo quizResultsToQuestionPojo : quizResultsToQuestionPojos){
            if(quizResultsToQuestionPojo.getStudentId().equals(studentId)){
                removeQuizResultsToQuestion(myRef,quizResultsToQuestionPojo.getQuizResultsToQuestionId());
            }
        }

        ArrayList<IdHolder> idHolders2 = new ArrayList<>();
        ArrayList<String> ids2 = new ArrayList<>();
        for(DataSnapshot child : dataSnapshot.child("enrollment").getChildren()){
            ids2.add(child.getKey());
            IdHolder idHolder = child.getValue(IdHolder.class);
            idHolders2.add(idHolder);
        }//for
        for(int i = 0; i < idHolders2.size(); i++){
            String itemQuizId = idHolders2.get(i).getStudentId();
            if(itemQuizId == null) continue;
            if(itemQuizId.equals(studentId)){
                removeItem(myRef,"enrollment",ids2.get(i));
            }
        }

    }

    /**
     * This removes the item with id myId in table databaseKey
     * @param myRef
     * @param databaseKey
     * @param myId
     */
    private static void removeItem(DatabaseReference myRef, String databaseKey, String myId){
        myRef.child(databaseKey).child(myId).removeValue();
    }
}
