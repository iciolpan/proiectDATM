package com.beginner.iciolpan.questionnaire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iciolpan on 11/15/2016.
 */

public class QuestionnaireDatabase extends SQLiteOpenHelper{

    /*public static final String DATABASE_NAME = "Questionnaire.db";
    public static final String TABLE_NAME = "Questions_table";*/
    public static final String KEY_ID = "_id";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_FIRST_ANSWER = "first_answer";
    public static final String KEY_SECOND_ANSWER = "second_answer";
    public static final String KEY_THIRD_ANSWER = "third_answer";
    public static final String KEY_CORRECT_ANSWER = "correct_answer";
    private static final String DB_NAME = "questionnaire.sqlite";
    public static final String TABLE_NAME = "quizz";
    String DB_LOCATION = "";
    private final Context myContext;
    private SQLiteDatabase mysqldb;



    public QuestionnaireDatabase(Context context) {
        super(context, DB_NAME, null, 1);
        //DB_LOCATION = context.getDatabasePath(DB_NAME).toString();
        this.DB_LOCATION="/data/data/"+context.getPackageName()+"/"+"databases/";
        this.myContext = context;
    }

    public boolean checkDataBase(){
        SQLiteDatabase checkDatabase = null;
        try{
            String myPath = DB_LOCATION + DB_NAME;
            checkDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
        }
        if(checkDatabase != null){
            checkDatabase.close();
        }
        return checkDatabase != null ? true : false;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_LOCATION + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[2048];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDataBase() throws IOException{
        boolean dbExists = checkDataBase();
        if(dbExists){
            Log.i("ionut","db already exists");
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
                Log.i("ionut","copydatabase method has been successfully executed");
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public void openDataBase() throws SQLException{
        //Open the database
        String myPath = DB_LOCATION + DB_NAME;
        mysqldb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        Log.i("ionut","opendatabase method has been successfully executed-readonly");

    }

    @Override
    public synchronized void close() {
        if(mysqldb != null)
            mysqldb.close();
        super.close();
        Log.i("ionut","database connection has been successfully closed");
    }

    public List<Question> getQuestion(int id){
        List<Question> question_content = new ArrayList<Question>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,
        KEY_QUESTION, KEY_FIRST_ANSWER, KEY_SECOND_ANSWER, KEY_THIRD_ANSWER, KEY_CORRECT_ANSWER}, KEY_ID + "=?",
                new String[]{String.valueOf(id) }, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            Question question = new Question();
            question.set_id(Integer.parseInt(cursor.getString(0)));
            question.set_question(cursor.getString(1));
            question.set_first_answer(cursor.getString(2));
            question.set_second_answer(cursor.getString(3));
            question.set_third_answer(cursor.getString(4));
            question.set_correct_answer(cursor.getString(5));

            question_content.add(question);
        }

        return question_content;
    }

    public int countRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        long value = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return (int)value;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion)
            try{
                copyDataBase();
            }catch(IOException e){
                e.printStackTrace();
            }
    }


}
