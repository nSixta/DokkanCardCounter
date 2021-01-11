package com.example.dokkancardcounter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Activity activity;
    List<MyDataList> myDataLists;

    public MyAdapter(Activity activity, List<MyDataList> myDataLists) {
        this.activity = activity;
        this.myDataLists = myDataLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        MyDataList dataList = myDataLists.get(position);
        AtomicInteger number = new AtomicInteger(dataList.getCopies());
        Picasso.get().load(dataList.getPicture()).into(holder.unitImageView);
        holder.unitNameTextView.setText(dataList.getName());
        holder.deleteButton.setOnClickListener(v -> {
            MainActivity.myDatabase.getItemInterface().delete(dataList);
            if (myDataLists.size() != 0) {
                myDataLists.remove(position);
                notifyItemRemoved(position);
            }
            notifyItemRemoved(position);
        });
        holder.numberOfCopiesText.setText(String.valueOf(dataList.getCopies()));
        holder.addButton.setOnClickListener(v -> {
            number.getAndIncrement();
            MainActivity.myDatabase.getItemInterface().update(dataList.getId(), number.get());
            holder.numberOfCopiesText.setText(String.valueOf(number.get()));
        });
        holder.subtractButton.setOnClickListener(v -> {
            number.getAndDecrement();
            MainActivity.myDatabase.getItemInterface().update(dataList.getId(), number.get());
            holder.numberOfCopiesText.setText(String.valueOf(number.get()));
            if(number.get() <= 0){
                number.set(0);
                holder.numberOfCopiesText.setText(String.valueOf(number.get()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDataLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView unitImageView;
        TextView unitNameTextView;
        ImageButton deleteButton;

        ImageButton addButton;
        ImageButton subtractButton;
        TextView numberOfCopiesText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            unitImageView = itemView.findViewById(R.id.unitImageView);
            unitNameTextView = itemView.findViewById(R.id.unitNameTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            addButton = itemView.findViewById(R.id.addButton);
            subtractButton = itemView.findViewById(R.id.subtractButton);
            numberOfCopiesText = itemView.findViewById(R.id.numberOfCopiesText);
        }
    }
}
