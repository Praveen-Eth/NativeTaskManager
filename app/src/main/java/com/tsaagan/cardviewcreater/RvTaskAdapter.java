package com.tsaagan.cardviewcreater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RvTaskAdapter extends RecyclerView.Adapter<ViewModel> {

    public  List<Card_View_Properties> todos ;

    public RvTaskAdapter(List<Card_View_Properties> todos) {
        this.todos = todos;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmodel,parent,false);

        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        TextView tv = holder.itemView.findViewById(R.id.checkboxText);
        tv.setText(todos.get(position).TaskName);
        CheckBox cb =holder.itemView.findViewById(R.id.checkboxDone);
        cb.setChecked(todos.get(position).isChecked);


    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}
