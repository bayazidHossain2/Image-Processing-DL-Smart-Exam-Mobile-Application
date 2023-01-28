package com.example.smartexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button setAnswer,makeResult,showResult,showAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAnswer = findViewById(R.id.btn_set_ans);
        makeResult = findViewById(R.id.btn_make_result);
        showResult = findViewById(R.id.btn_show_result);
        showAnswer = findViewById(R.id.btn_show_answer);

        setAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SetAnswerActivity.class);
                startActivity(intent);
            }
        });

        showResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ResultShowActivity.class);
                startActivity(intent);
            }
        });
        makeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MakeResultActivity.class);
                startActivity(intent);
            }
        });

        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AnswerShowActivity.class);
                startActivity(intent);
            }
        });
    }
}