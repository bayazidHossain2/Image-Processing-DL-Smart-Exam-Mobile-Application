package com.example.smartexam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartexam.Model.AnswerModel;
import com.example.smartexam.Model.ResultModel;
import com.example.smartexam.R;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.viewHolder>{
    Context context;
    ArrayList<ResultModel> list;

    public ResultAdapter(Context context, ArrayList<ResultModel> list) {
        this.context = context;
        this.list = list;
    }

    public void add(ResultModel model){
        list.add(model);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.
                simple_answer_design,parent,false);
        return new ResultAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.viewHolder holder, int position) {
        ResultModel model = list.get(position);
        holder.id.setText("Roll "+String.valueOf(model.getRoll())+" : ");
        holder.option.setText("    "+model.getTotalMatch()+" ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView id,option;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_question_no);
            option = itemView.findViewById(R.id.tv_answer_option);
        }
    }
}
