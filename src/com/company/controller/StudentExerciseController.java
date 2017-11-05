package com.company.controller;

import com.company.models.QuestionModel;
import com.company.models.StudentExerciseModel;
import com.company.objects.Question;
import com.company.objects.Answer;
import com.company.objects.User;

import java.util.*;

import com.company.objects.Exercise;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

public class StudentExerciseController implements Controller {
    private static StudentExerciseController instance = null;
    private User student = null;
    private Exercise exercise = null;

    private List<Question> questions;
    private List<Answer> studentResponses;
    private List<Answer> correctAnswers;
    private HashMap<String, Answer> answerHashMap;
    String[] responses = {"a", "b", "c", "d"};

    public void setUser(User u) {
        student = u;
    }

    private StudentExerciseController(){}

    public static StudentExerciseController getInstance() {
        if (instance == null) {
            instance = new StudentExerciseController();
        }
        return instance;
    }

    public String hlines(String middleString){
        int count = middleString.length();
        String hlines = "";
        for (int i = 0; i < count + 10; i ++){
            hlines += "-";
        }
        return hlines;
    }

    public void landingPage(Exercise e){
        exercise = e;
        if (e.getAdaptive() == true){
            landingPageAdaptive();
        }
        else{
            this.landingPage();
        }

    }

    private void loadQuestionsAndTitle(){
        try{
            questions = QuestionModel.getQuestionModel().getQuestionsByEx(exercise.getId());
        }catch(Exception e){
            System.out.println("error in studentExerciseController getting a question");
            StudentController.getInstance().landingPage();
        }


        for (Question q : questions){
            try{
                q.answers = QuestionModel.getQuestionModel().getAnswersByQues(q.getQuestionID());

            }catch(Exception e){
                System.out.println("error in studentExerciseController getting an answer");
                StudentController.getInstance().landingPage();
            }
        }

        correctAnswers = new ArrayList<>();
        studentResponses = new ArrayList<>();

        String title = "       " + exercise.getCourseID() + " " + exercise.getName();
        System.out.println(hlines(title));
        System.out.println(title);
        System.out.println(hlines(title));
    }

    private void displayAnswersAndGetResponse(Question q){
        Scanner scanner = new Scanner(System.in);

        String studentResponse = "";

        while (studentResponse == ""){
            studentResponse = scanner.nextLine();
            switch (studentResponse){
                case "a":
                case "b":
                case "c":
                case "d":
                    studentResponses.add(answerHashMap.get(studentResponse));
                    q.setStudentAnswer(answerHashMap.get(studentResponse));
                    break;
                case "e":
                    String hint = q.getHint();
                    if (hint != null){
                        System.out.println(hint);
                    }else{
                        System.out.println("No hint available");
                    }
                    studentResponse = "";
                    break;
                default:
                    System.out.println("ERROR:  INCORRECT RESPONSE GIVEN. PLEASE USE a, b, c, d, or e");
                    studentResponse = "";
                    break;
            }
        }
    }

    private Question getQuestionWithDifficulty(int startingDifficulty, ArrayList<Question> remainingQuestions){
        int difficulty = startingDifficulty;

        while (difficulty < 6){
            for (Question q : remainingQuestions){
                if (q.getDifficulty() == difficulty){
                    return q;
                }
            }
            difficulty ++;
        }
        difficulty = startingDifficulty;

        while (difficulty > 0){
            for (Question q : remainingQuestions){
                if (q.getDifficulty() == difficulty){
                    return q;
                }
            }
            difficulty --;
        }
        return null;
    }

    private void landingPageAdaptive(){
        loadQuestionsAndTitle();
        int currentDifficulty;
        Question currentQuestion;

        ArrayList<Question> sortedQuestions = new ArrayList<>();

        // that's right.  Insertion sort FTW.  May the powers of O(n^2) rain down upon us!

        for (int difficulty = 0; difficulty < 6; difficulty ++){
            for (Question q : questions){
                if (q.getDifficulty() == difficulty){
                    sortedQuestions.add(q);
                }
            }
        }

        currentDifficulty = sortedQuestions.get(sortedQuestions.size()/2).getDifficulty();  // right in the middle
        currentQuestion = getQuestionWithDifficulty(currentDifficulty, sortedQuestions);
        sortedQuestions.remove(currentQuestion);


        for (int i = 0; i < questions.size(); i ++){
            displayQuestionAndAnswers(currentQuestion);
            displayAnswersAndGetResponse(currentQuestion);

            if(studentResponses.get(i) == correctAnswers.get(i)){
                currentDifficulty ++;
            } else{
                currentDifficulty --;
            }
            currentQuestion = getQuestionWithDifficulty(currentDifficulty, sortedQuestions);
            sortedQuestions.remove(currentQuestion);

        }

        printReport();

    }

    public void landingPage(){
        loadQuestionsAndTitle();


        for (Question q : questions){
            displayQuestionAndAnswers(q);
            displayAnswersAndGetResponse(q);
        }
        printReport();

    }

    private void displayQuestionAndAnswers(Question q){

        // If the question is parameterized
        if (q.answers.get(0).getParameterID() != 0){
            displayParameterizedAnswersForQuestion(q);
        }

        else{
            String title = " " + q.getText() + "  DIFFICULTY: " + q.getDifficulty();
            System.out.println(hlines(title));
            System.out.println(title);
            System.out.println("");

            Random rand = new Random();

            ArrayList<Answer> allAnswersToDisplay = new ArrayList<>();

            while (allAnswersToDisplay.size() < 3){
                int yolo = rand.nextInt(q.answers.size()-1);
                Answer incorrectAnswerCandidate = q.answers.get(yolo);
                if (incorrectAnswerCandidate.getCorrect() == 0 &&!allAnswersToDisplay.contains(incorrectAnswerCandidate))
                    allAnswersToDisplay.add(incorrectAnswerCandidate);
            }

            // Find a correct answer
            Answer correctAnswer = new Answer();
            while (correctAnswer.getCorrect() == 0){
                correctAnswer = q.answers.get(rand.nextInt(q.answers.size()-1));
            }
            allAnswersToDisplay.add(correctAnswer);
            correctAnswers.add(correctAnswer);
            q.setActualAnswer(correctAnswer);

            Collections.shuffle(allAnswersToDisplay);

            answerHashMap = new HashMap<>();

            for (int i = 0; i < allAnswersToDisplay.size(); i ++){
                Answer ans = allAnswersToDisplay.get(i);
                answerHashMap.put(responses[i], ans);
                System.out.println("   " + responses[i] + "): " + answerHashMap.get(responses[i]).getText());
            }
            System.out.println("   e): Hint");
        }

    }

    void displayParameterizedAnswersForQuestion(Question question){

        Random rand = new Random();
        ArrayList<Answer> allAnswersToDisplay = new ArrayList<>();

        //get parameter id range
        ArrayList<Integer> parameterIDs = new ArrayList<>();
        for (Answer a : question.answers){
            if (!parameterIDs.contains(a.getParameterID()))
                parameterIDs.add(a.getParameterID());
        }
        int parametersToUse = rand.nextInt(parameterIDs.size()) + 1;
        question.setParamIndex(parametersToUse);

        while (allAnswersToDisplay.size() < 3){
            int yolo = rand.nextInt(question.answers.size()-1);
            Answer incorrectAnswerCandidate = question.answers.get(yolo);
            if (incorrectAnswerCandidate.getCorrect() == 0 &&
                    !allAnswersToDisplay.contains(incorrectAnswerCandidate) &&
                    incorrectAnswerCandidate.getParameterID() == parametersToUse)
                allAnswersToDisplay.add(incorrectAnswerCandidate);
        }

        while (allAnswersToDisplay.size() < 4){
            int yolo = rand.nextInt(question.answers.size()-1);
            Answer correctAnswerCandidate = question.answers.get(yolo);
            if (correctAnswerCandidate.getCorrect() == 1 &&!
                    allAnswersToDisplay.contains(correctAnswerCandidate) &&
                    correctAnswerCandidate.getParameterID() == parametersToUse) {
                allAnswersToDisplay.add(correctAnswerCandidate);
                correctAnswers.add(correctAnswerCandidate);
                question.setActualAnswer(correctAnswerCandidate);
            }
        }

        Collections.shuffle(allAnswersToDisplay);

        String[] temp = question.getText().split("<");

        String prompt = temp[0];
        for (int i = 1; i < temp.length; i ++){
            temp[i] = temp[i].replace(">", "");
            temp[i] = allAnswersToDisplay.get(0).paramVals.get(i-1) + " " +  temp[i];
            prompt += temp[i];
        }

        System.out.println(hlines(prompt));
        System.out.println(prompt + "  DIFFICULTY: " + question.getDifficulty());
        System.out.println("");

        answerHashMap = new HashMap<>();
        String[] responses = {"a", "b", "c", "d"};
        for (int i = 0; i < allAnswersToDisplay.size(); i ++){
            Answer ans = allAnswersToDisplay.get(i);
            answerHashMap.put(responses[i], ans);
            System.out.println("   " +responses[i] + "): " + answerHashMap.get(responses[i]).getText());
        }
        System.out.println("   e): Hint");


    }

    public void printReport(){
        Scanner scanner = new Scanner(System.in);
        int totalPoints = 0;
        String title = "     RESULTS FOR " + exercise.getName();
        System.out.println(hlines(title));
        System.out.println(title);
        System.out.println(hlines(title));
        for (int i = 0; i < studentResponses.size(); i ++){
            System.out.println("Question " + (i + 1) + ": " );
            System.out.println("  Your response:    " + studentResponses.get(i).getText());
            System.out.println("  Correct response: " + correctAnswers.get(i).getText());
            if(studentResponses.get(i) == correctAnswers.get(i)){
                System.out.println("CORRECT!  + " + (int)exercise.getPointsCorrect());
                totalPoints += exercise.getPointsCorrect();
            }
            else{
                System.out.println("INCORRECT - " + (int)exercise.getPointsIncorrect());
                totalPoints -= exercise.getPointsIncorrect();
            }
            System.out.println("");
        }
        System.out.println("TOTAL POINTS: " + totalPoints + "/" + (int)(studentResponses.size() * exercise.getPointsCorrect()));
        exercise.setFinalScore(totalPoints);

        StudentExerciseModel.getStudentExerciseModel().saveAttemptToDB(questions, student.username, exercise);

        System.out.println("Press enter to continue...");
        scanner.nextLine();

    }
}
