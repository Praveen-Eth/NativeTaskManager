package com.tsaagan.cardviewcreater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    RecyclerView recyclerView;

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
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setAdapter(new RvTaskAdapter(todo));





    }
}