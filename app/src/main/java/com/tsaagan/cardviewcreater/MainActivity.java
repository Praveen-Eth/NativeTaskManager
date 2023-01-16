package com.tsaagan.cardviewcreater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab ;
    RecyclerView recyclerView;
    EditText popupEditBox;
    Button setTimer,flag;
    ImageButton popupExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
         List<Card_View_Properties> todo = new ArrayList<>();
         todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));
        todo.add(new Card_View_Properties("wash clothes",true));


        
         recyclerView  = findViewById(R.id.recyclerView);
         fab = findViewById(R.id.floatingButton);
         Dialog popup = new Dialog(this);
         popup.setContentView(R.layout.popup_assign_task);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(new RvTaskAdapter(todo));

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
                         //bug on below codek
                         RvTaskAdapter obj = new RvTaskAdapter(todo);
                         obj.addTodo(new Card_View_Properties(popupEditBox.getText().toString(),false));
                         popup.dismiss();
                     }
                 });

                 popupExit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         popup.dismiss();
                     }
                 });



             }
         });





    }
}