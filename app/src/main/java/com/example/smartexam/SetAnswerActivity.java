package com.example.smartexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartexam.Adapters.AnswerAdapter;
import com.example.smartexam.Model.AnswerModel;

import java.util.ArrayList;

public class SetAnswerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner options;
    TextView question;
    Button add;
    String option="";
    RecyclerView seted_answer;
    ArrayList<AnswerModel> answerModels;
    int question_no=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_answer);

        question = findViewById(R.id.tv_question_no);
        options = findViewById(R.id.spinner);
        seted_answer = findViewById(R.id.rv_answer);
        add = findViewById(R.id.btn_add);
        answerModels = new ArrayList<>();

        AnswerAdapter ansAdapter = new AnswerAdapter(SetAnswerActivity.this,answerModels);
        seted_answer.setAdapter(ansAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SetAnswerActivity.this);
        seted_answer.setLayoutManager(layoutManager);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SetAnswerActivity.this,
                R.array.answer_options,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);
        options.setOnItemSelectedListener(SetAnswerActivity.this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(option.equals("")){
                    Toast.makeText(SetAnswerActivity.this, "Please Select a Option", Toast.LENGTH_SHORT).show();
                    return;
                }
                ansAdapter.add(new AnswerModel(question_no,option));
                question_no++;
                question.setText("Answer for Question No "+String.valueOf(question_no)+" :");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                option = "";
                break;
            case 1:
                option = "A";
                break;
            case 2:
                option = "B";
                break;
            case 3:
                option = "C";
                break;
            case 4:
                option = "D";
                break;
            default:
                Toast.makeText(SetAnswerActivity.this, "Default. ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(SetAnswerActivity.this, "Nothing selected.", Toast.LENGTH_SHORT).show();
    }
}