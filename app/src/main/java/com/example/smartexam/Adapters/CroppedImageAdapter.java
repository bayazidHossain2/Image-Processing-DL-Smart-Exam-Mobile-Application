package com.example.smartexam.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartexam.Model.AnswerModel;
import com.example.smartexam.Model.Option_Image;
import com.example.smartexam.R;

import java.util.ArrayList;

public class CroppedImageAdapter extends RecyclerView.Adapter<CroppedImageAdapter.viewHolder>{
    Context context;
    ArrayList<Option_Image> list;

    public CroppedImageAdapter(Context context, ArrayList<Option_Image> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_image_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Option_Image model = list.get(position);
        holder.id.setText(model.getNumber());
        holder.image.setImageBitmap(model.getBitmap());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView id;
        ImageView image;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_answer_paper_option);
            image = itemView.findViewById(R.id.iv_answer_paper_option);
        }
    }
}

