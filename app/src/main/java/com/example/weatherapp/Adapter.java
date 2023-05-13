package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyCard> {
    ArrayList<String> tempData, textData, hourData;

    public Adapter(ArrayList<String> tempData, ArrayList<String> textData, ArrayList<String> hourData){
        this.tempData = tempData;
        this.textData = textData;
        this.hourData = hourData;
    }

    @NonNull
    @Override
    public MyCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_card_view, parent, false);
        return new MyCard(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCard holder, int position) {
        holder.temp.setText(tempData.get(position) + " °C");
        holder.time.setText(hourData.get(position));
        holder.desc.setText(textData.get(position));
    }

    @Override
    public int getItemCount() {
        return tempData.size();
    }

    public static class MyCard extends RecyclerView.ViewHolder{
        TextView temp, desc, time;
        public MyCard(@NonNull View itemView) {
            super(itemView);
            temp = itemView.findViewById(R.id.temp);
            time = itemView.findViewById(R.id.time);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}
