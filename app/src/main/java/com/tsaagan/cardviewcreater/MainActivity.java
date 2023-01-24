package com.tsaagan.cardviewcreater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab ;
    RecyclerView recyclerView;
    EditText popupEditBox;
    Button setTimer,flag;
    ImageButton popupExit;
    ListView listView;
    ArrayList<String> taskList ;//data
    ArrayAdapter<String> taskListArrayAdapter;  //binder
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
         List<Card_View_Properties> todo = new ArrayList<>();

        
         recyclerView  = findViewById(R.id.recyclerView);
         fab = findViewById(R.id.floatingButton);
         Dialog popup = new Dialog(this);
         popup.setContentView(R.layout.popup_assign_task);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RvTaskAdapter rvTaskAdapter = new RvTaskAdapter(todo);
         recyclerView.setAdapter(rvTaskAdapter);
         listView = findViewById(R.id.list_item);
         taskList  = new ArrayList<>();
         taskListArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,taskList);
        listView.setAdapter(taskListArrayAdapter);
        searchView = findViewById(R.id.searchView);


         //Views that  are inside the popup
         setTimer = (Button) popup.findViewById(R.id.timer_button);
         flag = (Button) popup.findViewById(R.id.flag_button);
         popupEditBox = (EditText) popup.findViewById(R.id.edit_text);
         popupExit  = (ImageButton) popup.findViewById(R.id.dismiss1);




         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 popup.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                 popup.getWindow().setGravity(Gravity.BOTTOM);
                 popup.getWindow().setBackgroundDrawableResource(R.color.transparent);
                 popup.show();
                 flag.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         rvTaskAdapter.addTodo(new Card_View_Properties(popupEditBox.getText().toString(),false));
                         taskListArrayAdapter.add (popupEditBox.getText().toString());
                         popupEditBox.setText(null);
                         popup.dismiss();
                     }
                 });

                 popupExit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         popupEditBox.setText(null);
                         popup.dismiss();
                     }
                 });



             }
         });

         searchView.setOnSearchClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 recyclerView.setVisibility(View.GONE);
                 listView.setVisibility(View.VISIBLE);
             }
         });

         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 if(taskList.contains(query)){
                     taskListArrayAdapter.getFilter().filter(query);
                 }
                 else {
                     Toast.makeText(MainActivity.this, "no results found", Toast.LENGTH_SHORT).show();
                 }
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 taskListArrayAdapter.getFilter().filter(newText);
                 return false;
             }
         });

         searchView.setOnCloseListener(new SearchView.OnCloseListener() {
             @Override
             public boolean onClose() {
                 listView.setVisibility(View.GONE);
                 recyclerView.setVisibility(View.VISIBLE);
                 return false;
             }

         });


        //this item touch helper is used to swipe the card view inside of a recycler view .


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
             @Override
             public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                 return false;
             }

             @Override
             public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                 Card_View_Properties deletedView  =   rvTaskAdapter.todos.get(viewHolder.getAdapterPosition());
                 int position  = viewHolder.getAdapterPosition();
                 taskListArrayAdapter.remove(deletedView.TaskName);
                 taskListArrayAdapter.notifyDataSetChanged();
                 rvTaskAdapter.todos.remove(position);
                 rvTaskAdapter.notifyDataSetChanged();

                 Toast.makeText(MainActivity.this, deletedView.TaskName + " task removed", Toast.LENGTH_SHORT).show();



             }
         }).attachToRecyclerView(recyclerView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                linearLayout.addView(rvTaskAdapter.filterTodo(listView.toString(),MainActivity.this));


            }
        });








    }
}