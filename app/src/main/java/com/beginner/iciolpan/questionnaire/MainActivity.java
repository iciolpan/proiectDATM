package com.beginner.iciolpan.questionnaire;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    QuestionnaireDatabase db;
    Button startQuizBtn, exitBtn;
    ImageView imageview;
    QuizActivity qa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new QuestionnaireDatabase(this);
        qa = new QuizActivity();
        init();
    }

    public void init(){

        startQuizBtn = (Button)findViewById(R.id.startQuizzButton);
        exitBtn = (Button)findViewById(R.id.exitButton);

        startQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(nextScreen);
                MainActivity.this.finish();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                // set title
                alertDialogBuilder.setTitle("Close application");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure that you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close the app

                                System.exit(0);
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

            }
        });


    }

    @Override
    public void onBackPressed(){
       //disable the back button of your android phone
    }

    }
