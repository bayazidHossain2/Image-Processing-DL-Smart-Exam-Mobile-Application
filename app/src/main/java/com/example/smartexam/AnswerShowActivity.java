package com.example.smartexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.smartexam.Adapters.AnswerSetAdapter;
import com.example.smartexam.Model.AnswerSetModel;

import java.util.ArrayList;

public class AnswerShowActivity extends AppCompatActivity {

    RecyclerView answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_show);

        answer = findViewById(R.id.rv_all_result);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(AnswerShowActivity.this);
        ArrayList<AnswerSetModel> list = dbHelper.getAllAnswerSet();
        AnswerSetAdapter adapter = new AnswerSetAdapter(AnswerShowActivity.this,list);
        answer.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AnswerShowActivity.this);
        answer.setLayoutManager(layoutManager);
    }
}