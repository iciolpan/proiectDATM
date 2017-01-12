package com.beginner.iciolpan.questionnaire;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by iciolpan on 10/25/2016.
 */

public class ResultActivity  extends AppCompatActivity {

    TextView displayResultBtn;
    Button returnToMainMenuBtn, exitResultBtn;
    int result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);
        //onBackPressed();
        init();
    }
    public void init(){

        returnToMainMenuBtn = (Button)findViewById(R.id.returnToMainMenuButton);
        exitResultBtn = (Button)findViewById(R.id.exitResultButton);
        displayResultBtn = (TextView)findViewById(R.id.ResultTextView);

        //exitResultBtn.setBackgroundColor(Color.RED);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            result  = extras.getInt("result");
        }
        displayResultBtn.setText("Result: "+ Integer.toString(result)+ " correct answers");

        returnToMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(nextScreen);
                ResultActivity.this.finish();
            }
        });
        exitResultBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Context context = getApplicationContext();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ResultActivity.this);
                // set title
                alertDialogBuilder.setTitle("EXIT");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure that you want to exit?")
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

    }
    @Override
    public void onBackPressed(){

    }

}
