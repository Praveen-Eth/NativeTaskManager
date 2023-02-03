package com.tsaagan.NativeTaskManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class DbHandler extends SQLiteOpenHelper {
    public static final String DataBaseName = "TodoDB";
    public static final int DataBaseVersion = 1;
    public static final String TableName = "TODO_TABLE";
    public static final String ID = "ID";
    public static final String Task = "Task";
    public static final String ReminderTime = "ReminderTime";
    public static final String isChecked = "CheckBoxState";


    public DbHandler(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    public void addData (String task,String ReminderTime,Boolean isChecked){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put("Task",task);
        contentValues.put("ReminderTime",ReminderTime);
        if(isChecked){contentValues.put("isChecked",1);}
        else {contentValues.put("isChecked",0);}

        database.insert(TableName,null,contentValues);
        database.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query  = "CREATE TABLE "+TableName+" ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Task+" TEXT, "+ReminderTime+" TEXT, "+isChecked+" INTEGER NOT NULL DEFAULT 1)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }
}
