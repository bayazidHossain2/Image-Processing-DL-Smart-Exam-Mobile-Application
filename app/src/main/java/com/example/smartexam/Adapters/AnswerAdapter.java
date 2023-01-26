package com.example.smartexam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartexam.Model.AnswerModel;
import com.example.smartexam.R;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.viewHolder> {

    Context context;
    ArrayList<AnswerModel> list;

    public AnswerAdapter(Context context, ArrayList<AnswerModel> list) {
        this.context = context;
        this.list = list;
    }

    public void add(AnswerModel model){
        list.add(model);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.simple_answer_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AnswerModel model = list.get(position);
        holder.id.setText("Question "+String.valueOf(model.getId())+" : ");
        holder.option.setText("   ( "+model.getOption()+" )");
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
