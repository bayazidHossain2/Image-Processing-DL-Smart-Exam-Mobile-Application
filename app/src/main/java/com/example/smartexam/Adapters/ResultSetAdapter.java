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
import com.example.smartexam.Model.ResultModel;
import com.example.smartexam.Model.ResultSetModel;
import com.example.smartexam.MyDatabaseHelper;
import com.example.smartexam.R;

import java.util.ArrayList;

public class ResultSetAdapter extends RecyclerView.Adapter<ResultSetAdapter.viewHolder>{

    Context context;
    ArrayList<ResultSetModel> list;

    public ResultSetAdapter(Context context, ArrayList<ResultSetModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_set_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ResultSetModel model = list.get(position);
        holder.resultName.setText(model.getExamName());
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(context);
        holder.resultDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.resultDetails.getText().toString().equals("Show Details >")){
                    holder.resultDetails.setText("Hide Details <");
                    holder.Details.setVisibility(View.VISIBLE);
                    ArrayList<ResultModel> result = dbHelper.getResult(model.getMinResultId(),model.getMaxResultId());
                    ResultAdapter resultAdapter = new ResultAdapter(context,result);
                    holder.Details.setAdapter(resultAdapter);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    holder.Details.setLayoutManager(layoutManager);
                }else{
                    holder.resultDetails.setText("Show Details >");
                    holder.Details.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView resultName,resultDetails;
        RecyclerView Details;
       public viewHolder(@NonNull View itemView) {
           super(itemView);
           resultName = itemView.findViewById(R.id.tv_result_name);
           resultDetails = itemView.findViewById(R.id.tv_result_details);
           Details = itemView.findViewById(R.id.rv_result_details);
       }
   }
}
