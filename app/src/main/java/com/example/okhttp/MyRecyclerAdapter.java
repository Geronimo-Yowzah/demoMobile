package com.example.okhttp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.Holder>{
    private final List<String> mDataSet = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setItem(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textDescription;

        public Holder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textDescription = itemView.findViewById(R.id.text_description);
        }

        public void setItem(int position){
            textTitle.setText(mDataSet.get(position));
            textDescription.setText("index = " + position);
        }

    }
    public MyRecyclerAdapter(List<String> dataSet){
        mDataSet.addAll(dataSet);
    }

    public void setData(List<String> data){
        mDataSet.clear();
        mDataSet.addAll(data);
        notifyDataSetChanged();
    }
}

