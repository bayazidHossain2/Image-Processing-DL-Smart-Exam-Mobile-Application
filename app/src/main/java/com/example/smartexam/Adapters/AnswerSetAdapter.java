package com.example.smartexam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartexam.Model.AnswerModel;
import com.example.smartexam.Model.AnswerSetModel;
import com.example.smartexam.MyDatabaseHelper;
import com.example.smartexam.R;

import java.util.ArrayList;

public class AnswerSetAdapter extends RecyclerView.Adapter<AnswerSetAdapter.viewHolder>{

    Context context;
    ArrayList<AnswerSetModel> list;

    public AnswerSetAdapter(Context context, ArrayList<AnswerSetModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.answer_show_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AnswerSetModel model = list.get(position);

        holder.examName.setText(model.getExamName());
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.details.getText().toString().equals("Show Details >")){
                    holder.details.setText("Hide Details <");
                    holder.answerDetails.setVisibility(View.VISIBLE);
                    ArrayList<AnswerModel> answerList = dbHelper.getAnswer(model.getMinId(),model.getMaxId());
                    AnswerAdapter answerAdapter = new AnswerAdapter(context,answerList);
                    holder.answerDetails.setAdapter(answerAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    holder.answerDetails.setLayoutManager(layoutManager);
                }else{
                    holder.details.setText("Show Details >");
                    holder.answerDetails.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView examName,details;
        RecyclerView answerDetails;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.tv_examdetails_examname);
            details = itemView.findViewById(R.id.tv_answer_details);
            answerDetails = itemView.findViewById(R.id.rv_result_details);
        }
    }
}
