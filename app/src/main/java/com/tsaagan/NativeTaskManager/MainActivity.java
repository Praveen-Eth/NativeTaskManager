package com.tsaagan.NativeTaskManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab ;
    RecyclerView recyclerView;
    EditText popupEditBox;
    Button setTimerButton,flag;
    ImageButton popupExit;
    ListView listView;
    ArrayList<String> taskList ;//data
    ArrayAdapter<String> taskListArrayAdapter;  //binder
    SearchView searchView;
    View tempViewForSearchInRecyclerView;
    final static int request1 = 1;
    String a = "0";

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RvTaskAdapter rvTaskAdapter = new RvTaskAdapter(todo);
         recyclerView.setAdapter(rvTaskAdapter);
         listView = findViewById(R.id.list_item);
         taskList  = new ArrayList<>();
         taskListArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,taskList);
        listView.setAdapter(taskListArrayAdapter);
        searchView = findViewById(R.id.searchView);


         //Views that  are inside the popup
         setTimerButton = (Button) popup.findViewById(R.id.timer_button);
         flag = (Button) popup.findViewById(R.id.flag_button);
         popupEditBox = (EditText) popup.findViewById(R.id.edit_text);
         popupExit  = (ImageButton) popup.findViewById(R.id.dismiss1);

         //Views that are inside the setTimer Dialog
        Dialog setTimer = new Dialog(this);
        ImageButton datePopup = setTimer.findViewById(R.id.datePopup);
        NumberPicker hour_picker = setTimer.findViewById(R.id.hour_picker);
        NumberPicker minute_picker = setTimer.findViewById(R.id.minute_picker);
        Switch AmPmCatcher  = setTimer.findViewById(R.id.noon_picker);
        Button setButton  = setTimer.findViewById(R.id.time_set_button);




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
                 linearLayout.removeView(tempViewForSearchInRecyclerView);
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
                tempViewForSearchInRecyclerView = rvTaskAdapter.filterTodo(listView.toString(),MainActivity.this,position);
                linearLayout.addView(tempViewForSearchInRecyclerView);


            }
        });

        setTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2023,0,27,8,2,20);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                MyReceiver myReceiver = new MyReceiver();
                Intent intent  = new Intent(MainActivity.this,MyReceiver.class);
                intent.putExtra("title","You Received a Notification") ;
                intent.putExtra("body","The Task You Assigned will reached Dead end😉") ;
                sendBroadcast(intent);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,1,intent,0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                //todo: need to complete ui design for set timer button;
                Toast.makeText(MainActivity.this, "alarm set to"+ DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()), Toast.LENGTH_SHORT).show();


            }
        });




    }
}