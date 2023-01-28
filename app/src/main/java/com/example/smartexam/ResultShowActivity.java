package com.example.smartexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.smartexam.Adapters.ResultSetAdapter;
import com.example.smartexam.Model.ResultSetModel;

import java.util.ArrayList;

public class ResultShowActivity extends AppCompatActivity {

    RecyclerView allResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_show);
        allResult = findViewById(R.id.rv_all_result);
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(ResultShowActivity.this);
        ArrayList<ResultSetModel> list = databaseHelper.getResultSet();
        ResultSetAdapter adapter = new ResultSetAdapter(ResultShowActivity.this,list);
        allResult.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ResultShowActivity.this);
        allResult.setLayoutManager(layoutManager);

    }
}