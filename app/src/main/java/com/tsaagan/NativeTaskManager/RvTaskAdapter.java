package com.tsaagan.NativeTaskManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvTaskAdapter extends RecyclerView.Adapter<RvViewHolder> {

    public  List<Card_View_Properties> todos ;

    public RvTaskAdapter(List<Card_View_Properties> todos) {
        this.todos = todos;
    }

    public void addTodo(Card_View_Properties todo){
        todos.add(todo);
        notifyItemInserted(todos.size()-1);
    }
    /**note: even this method works fine this was not the optimized way to filter data.
     * how the method works?
     * it just filter the data directly from List<Card_View_Properties> todos
     * and recreate the view from recycler view.
    */

    public  View filterTodo(String s,Context parents,int position){

        View view  = LayoutInflater.from(parents).inflate(R.layout.cardmodel,null);
        TextView tv= view.findViewById(R.id.checkboxText);
        tv.setText(todos.get(position).TaskName);
        CheckBox checkBox = view.findViewById(R.id.checkboxDone);
        checkBox.setChecked(todos.get(position).isChecked);

            return view;



    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardmodel,parent,false);

        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        TextView tv = holder.itemView.findViewById(R.id.checkboxText);
        tv.setText(todos.get(position).TaskName);
        CheckBox cb =holder.itemView.findViewById(R.id.checkboxDone);
        cb.setChecked(todos.get(position).isChecked);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                todos.get(holder.getAdapterPosition()).isChecked = isChecked;
            }
        });


    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}
