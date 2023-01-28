package com.example.smartexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.smartexam.Model.AnswerModel;
import com.example.smartexam.Model.AnswerSetModel;
import com.example.smartexam.Model.ResultModel;
import com.example.smartexam.Model.ResultSetModel;

import java.util.ArrayList;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    static final String NAME = "SmartExamDB";
    static final int VERSION = 2;
    Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION );

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Answer" +
                        "(a_id INTEGER primary key autoincrement," +
                        "uid text," +
                        "question_no int," +
                        "answer text)"
        );

        db.execSQL(
                "create table AnswerSet" +
                        "(exam_name text primary key," +
                        "min_answer_id int," +
                        "max_answer_id int)"
        );

        db.execSQL(
                "create table Result" +
                        "(r_id INTEGER primary key autoincrement," +
                        "uid text," +
                        "roll int," +
                        "min_answer_id int," +
                        "max_answer_id int," +
                        "total_match int)"
        );

        db.execSQL(
                "create table ResultSet" +
                        "(exam_name text primary key," +
                        "min_result_id int," +
                        "max_result_id int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Answer");
        db.execSQL("drop table if exists AnswerSet");
        db.execSQL("drop table if exists Result");
        db.execSQL("drop table if exists ResultSet");

        onCreate(db);
    }

    public int insertAnswer(AnswerModel answerModel){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("uid",answerModel.getUid());
        values.put("question_no",answerModel.getQuestion_no());
        values.put("answer",answerModel.getOption());

        long pushed = database.insert("Answer",null,values);

        if(pushed<=0){
            //Toast.makeText(context, "Data insertion Fail.", Toast.LENGTH_SHORT).show();
            Log.d("---database","Answer insertion Fail.");

            database.close();
            return 0;
        }else{
            //Toast.makeText(context, "Answer insertion success.", Toast.LENGTH_SHORT).show();
            Log.d("---database","Answer insertion Success.");
            Cursor cursor = database.rawQuery("select * from Answer where uid="+answerModel.getUid(),null);

            if(cursor.moveToFirst()){
                int id = cursor.getInt(0);
                cursor.close();
                database.close();
                return id;
            }cursor.close();
            database.close();
            return 0;
        }
    }

    public ArrayList<AnswerModel> getAnswer(int start,int end){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Answer where a_id between "+start+" and "+end, null);

        ArrayList<AnswerModel> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                AnswerModel model = new AnswerModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getInt(2),cursor.getString(3));
                list.add(model);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return list;
    }

    public void insertAnswerSet(AnswerSetModel model){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("exam_name",model.getExamName());
        values.put("min_answer_id",model.getMinId());
        values.put("max_answer_id",model.getMaxId());

        long pushed = database.insert("AnswerSet",null,values);

        if(pushed<=0){
            //Toast.makeText(context, "Data insertion Fail.", Toast.LENGTH_SHORT).show();
            Log.d("---database","AnswerSet insertion Fail.");
        }else{
            Toast.makeText(context, "Answer Set Success.", Toast.LENGTH_SHORT).show();
            Log.d("---database","AnswerSet insertion Success.");
        }
        database.close();
    }

    public AnswerSetModel getAnswerSet(String exam_name){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from AnswerSet " +
                "where exam_name = '"+exam_name+"'", null);

        AnswerSetModel model = new AnswerSetModel();
        if(cursor.moveToFirst()){
            model = new AnswerSetModel(cursor.getString(0),cursor.getInt(1),cursor.getInt(2));
            Log.d("---database","AnswerSet exam found.");
        }
        cursor.close();
        database.close();
        return model;
    }

    public ArrayList<AnswerSetModel> getAllAnswerSet(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from AnswerSet", null);

        ArrayList<AnswerSetModel> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                AnswerSetModel model = new AnswerSetModel(cursor.getString(0),
                        cursor.getInt(1),cursor.getInt(2));
                list.add(model);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return list;

    }
    public boolean isExist(String exam_name){
        SQLiteDatabase database = getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("select * from AnswerSet " +
                    "where exam_name = '"+exam_name+"'", null);
            if(cursor.moveToFirst()){
                cursor.close();
                database.close();
                Log.d("---database","AnswerSet exam found.");
                return true;
            }else{
                cursor.close();
                database.close();
                Log.d("---database","AnswerSet exam not found");
                return false;
            }
        }catch (Exception e){
            database.close();
            Log.d("---database","AnswerSet exception found."+e.getMessage());
            return false;
        }
    }

    public int insertResult(ResultModel model){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("uid",model.getUid());
        values.put("roll",model.getRoll());
        values.put("min_answer_id",model.getMin_ans_id());
        values.put("max_answer_id",model.getMax_ans_id());
        values.put("total_match",model.getTotalMatch());

        long pushed = database.insert("Result",null,values);

        if(pushed<=0){
            //Toast.makeText(context, "Data insertion Fail.", Toast.LENGTH_SHORT).show();
            Log.d("---database","Result insertion Fail.");

            database.close();
            return 0;
        }else{
            //Toast.makeText(context, "Answer insertion success.", Toast.LENGTH_SHORT).show();
            Log.d("---database","Result insertion Success.");
            Cursor cursor = database.rawQuery("select * from Result where uid = '"+model.getUid()+"'",null);

            if(cursor.moveToFirst()){
                int id = cursor.getInt(0);
                cursor.close();
                database.close();
                return id;
            }cursor.close();
            database.close();
            return 0;
        }
    }

    public void insertResultSet(ResultSetModel model){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("exam_name",model.getExamName());
        values.put("min_result_id",model.getMinResultId());
        values.put("max_result_id",model.getMaxResultId());

        long pushed = database.insert("ResultSet",null,values);

        if(pushed<=0){
            Toast.makeText(context, "Result insertion Fail.", Toast.LENGTH_SHORT).show();
            Log.d("---database","ResultSet insertion Fail.");
        }else{
            Toast.makeText(context, "Result save Success.", Toast.LENGTH_SHORT).show();
            Log.d("---database","Result set insertion Success.");
        }
        database.close();
    }

    public ArrayList<ResultSetModel> getResultSet(){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from ResultSet ", null);

        ArrayList<ResultSetModel> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                ResultSetModel model = new ResultSetModel(cursor.getString(0),cursor.getInt(1),
                        cursor.getInt(2));
                list.add(model);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return list;
    }

    public ArrayList<ResultModel> getResult(int start,int end){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from Result where r_id between "+start+" and "+end, null);

        ArrayList<ResultModel> list = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                ResultModel model = new ResultModel(cursor.getInt(0),cursor.getString(1),
                        cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),
                        cursor.getInt(5));
                list.add(model);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return list;
    }

}
