package com.tsaagan.NativeTaskManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

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
    int code = 0;
     long NotificationTime = 0;

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
        TextView textView = popup.findViewById(R.id.text2);

         //Views that are inside the setTimer Dialog
        Dialog setTimer = new Dialog(this);
        setTimer.setContentView(R.layout.set_timer_layout);
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
                         setNotification("you May Have InComplete Task✨",popupEditBox.getText().toString(),code,NotificationTime);
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
                 int[] mYear = {0};
                 int[] mMonth = {0};
                 int[] mDayOfMonth = {0};
                 int[] mHour = {0};
                 int[] mMinute = {0};
                setTimer.show();
              datePopup.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog  = new DatePickerDialog(MainActivity.this);
                        datePickerDialog.show();
                        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mYear[0] = year;
                                mMonth[0] = month;
                                mDayOfMonth[0] =  dayOfMonth;
                            }
                        });
                    }
                });
                hour_picker.setMaxValue(12);
                hour_picker.setMinValue(1);
                minute_picker.setMinValue(0);
                minute_picker.setMaxValue(59);
                setButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mYear[0]==0 ){
                            Toast.makeText(MainActivity.this, "please set date", Toast.LENGTH_SHORT).show();
                        }else{

                            mHour[0] = hour_picker.getValue();
                            mMinute[0] = minute_picker.getValue();
                            setTimer.dismiss();
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(mYear[0], mMonth[0], mDayOfMonth[0], mHour[0], mMinute[0],0);
                            NotificationTime = calendar.getTimeInMillis();

                            Toast.makeText(MainActivity.this, "alarm set to"+ DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                            String time  = mYear[0] +"|"+ mMonth[0] +"|"+ mDayOfMonth[0] +"|"+DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                            textView.setText(time);
                        }
                    }
                });


            }
        });







    }
    public void setNotification(String title, String body,int requestCode,long reminder){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this,MyReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,reminder,pendingIntent);
        code++;
    }
}