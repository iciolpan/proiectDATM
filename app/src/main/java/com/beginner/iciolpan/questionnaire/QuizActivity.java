package com.beginner.iciolpan.questionnaire;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by iciolpan on 10/25/2016.
 */

public class QuizActivity  extends AppCompatActivity {

    TextView questionTextBtn, questionNumberTextBtn;
    CheckedTextView firstAnswerBtn, secondAnswerBtn, thirdAnswerBtn;
    Button nextQuestionBtn, exitQuizBtn, returnToMainMenuQuizBtn;
    QuestionnaireDatabase ob;
    String correctAnswer=null;
    String selectedAnswer=null;
    //int questionNumber=1;
    int questionNumber=0; //new
    int displayedquestionNumber;
    int numberOfQuestions=15; //new
    int result = 0;
    Integer[] array;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_screen);
        //onBackPressed();
        init();
    }
    public void init(){

        questionNumberTextBtn = (TextView)findViewById(R.id.QuestionNumberTextView);
        questionTextBtn = (TextView)findViewById(R.id.QuestiontextView);
        firstAnswerBtn = (CheckedTextView)findViewById(R.id.FirstAnswerCheckedTextView);
        secondAnswerBtn = (CheckedTextView)findViewById(R.id.SecondCheckedTextView);
        thirdAnswerBtn = (CheckedTextView)findViewById(R.id.ThirdAnswerCheckedTextView);
        nextQuestionBtn = (Button)findViewById(R.id.NextQuestionButton);
        exitQuizBtn = (Button)findViewById(R.id.exitQuizButton);
        returnToMainMenuQuizBtn = (Button)findViewById(R.id.ReturnToMainMenuQuizzButton);

        ob = new QuestionnaireDatabase(QuizActivity.this);

        firstAnswerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (firstAnswerBtn.isChecked()){
                    firstAnswerBtn.setChecked(false);
                    selectedAnswer = null;
                }else {
                    firstAnswerBtn.setChecked(true);
                    secondAnswerBtn.setChecked(false);
                    thirdAnswerBtn.setChecked(false);
                    selectedAnswer=firstAnswerBtn.getText().toString();

                }
            }
        });

        secondAnswerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (secondAnswerBtn.isChecked()){
                    secondAnswerBtn.setChecked(false);
                    selectedAnswer=null;
                }else {
                    secondAnswerBtn.setChecked(true);
                    firstAnswerBtn.setChecked(false);
                    thirdAnswerBtn.setChecked(false);
                    selectedAnswer=secondAnswerBtn.getText().toString();

                }
            }
        });

        thirdAnswerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(thirdAnswerBtn.isChecked()) {
                    thirdAnswerBtn.setChecked(false);
                    selectedAnswer=null;
                }else {
                    thirdAnswerBtn.setChecked(true);
                    firstAnswerBtn.setChecked(false);
                    secondAnswerBtn.setChecked(false);
                    selectedAnswer=thirdAnswerBtn.getText().toString();

                }
            }
        });

        exitQuizBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Context context = getApplicationContext();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
                // set title
                alertDialogBuilder.setTitle("EXIT");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure that you want to exit and close the application?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                //close();
            }
        });

        returnToMainMenuQuizBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Context context = getApplicationContext();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
                // set title
                alertDialogBuilder.setTitle("Return to main menu");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to return to the main menu?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                Intent nextScreen = new Intent(QuizActivity.this, MainActivity.class);
                                startActivity(nextScreen);
                                QuizActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                //close();
            }
        });

        try{
            ob.createDataBase();
            //ob.checkAndOpenDataBase();
        }catch(IOException ioe){
            throw new Error("unable to create the db");

        }
        Log.i("ionut","database was successfully open");
        try{
            ob.openDataBase();
        }catch(SQLException sqle) {
            throw sqle;
        }

        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstAnswerBtn.isChecked() && !secondAnswerBtn.isChecked() && !thirdAnswerBtn.isChecked()){
                    //popup message
                    Context context = getApplicationContext();
                    CharSequence text = "Please select your answer!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast.makeText(context, text, duration).show();
                }else {
                    //check result
                    checkResult(selectedAnswer, correctAnswer);

                    questionNumber++;
                    //if(questionNumber <= 20) {
                    if(questionNumber < array.length) { //new
                        //showQuestion(questionNumber);
                        displayedquestionNumber = questionNumber+1;
                        questionNumberTextBtn.setText(displayedquestionNumber+" of "+numberOfQuestions);

                        showQuestion(array[questionNumber]);
                        if(firstAnswerBtn.isChecked())
                            firstAnswerBtn.setChecked(false);

                        if(secondAnswerBtn.isChecked())
                            secondAnswerBtn.setChecked(false);

                        if(thirdAnswerBtn.isChecked())
                            thirdAnswerBtn.setChecked(false);

                    }else{
                        //goto result activity
                        //checkResult(selectedAnswer, correctAnswer);
                        ob.close();
                        Intent nextScreen = new Intent(QuizActivity.this, ResultActivity.class);
                        nextScreen.putExtra("result", result);
                        startActivity(nextScreen);
                        Log.i("ionut",Integer.toString(result));
                        QuizActivity.this.finish();
                    }
                }
            }
        });

        //new
        Set<Integer> set = new HashSet<Integer>();
        Random random = new Random();

        int numberOfDBRows = ob.countRows();

        while (set.size() < numberOfQuestions) {
            set.add(random.nextInt(numberOfDBRows)+1);
        }
        array = new Integer[numberOfQuestions];
        set.toArray(array); // convert set to array of Integers

        //showQuestion(questionNumber);
        displayedquestionNumber = questionNumber+1;
        questionNumberTextBtn.setText(displayedquestionNumber+" of "+numberOfQuestions);
        showQuestion(array[questionNumber]); //new

    }

    public void showQuestion(int i){
        List<Question> query_content = ob.getQuestion(i);
        for(Question q : query_content){
            questionTextBtn.setText(q.get_question());
            firstAnswerBtn.setText(q.get_first_answer());
            secondAnswerBtn.setText(q.get_second_answer());
            thirdAnswerBtn.setText(q.get_third_answer());
            correctAnswer = q.get_correct_answer();
        }

    }

    @Override
    public void onBackPressed(){

    }
    public void checkResult( String selected, String correct){

        if(selected.equals(correct)){
            result++;
        }
        //return result;
    }

}
