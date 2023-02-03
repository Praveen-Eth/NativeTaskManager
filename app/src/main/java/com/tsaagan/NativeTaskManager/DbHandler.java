package com.tsaagan.NativeTaskManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    public static final String DataBaseName = "TodoDB";
    public static final int DataBaseVersion = 1;
    public static final String TableName = "TODO_TABLE";
    public static final String ID = "ID";
    public static final String Task = "Task";
    public static final String ReminderTime = "ReminderTime";
    public static final String isChecked = "CheckBoxState";
    public static final String notificationId = "notificationId";


    public DbHandler(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    public void addData (String task,String ReminderTime,Boolean isChecked,String notificationId){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues  contentValues = new ContentValues();
        contentValues.put("Task",task);
        contentValues.put("ReminderTime",ReminderTime);
        if(isChecked){contentValues.put("CheckBoxState",1);}
        else {contentValues.put("CheckBoxState",0);}
        contentValues.put("notificationId",notificationId);

        database.insert(TableName,null,contentValues);
        database.close();

    }

    // the code is depreciated.
      /*  public ArrayList<String> getData() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TableName;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String task = cursor.getString(1);
                String reminderTime = cursor.getString(2);
                int checked = cursor.getInt(3);
                boolean isChecked = checked != 0;
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return taskList;
    }  */


    public ArrayList<TaskData> getData(){
        SQLiteDatabase readData = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TableName;
        Cursor cursor  = readData.rawQuery(selectQuery,null);
        ArrayList<TaskData> taskDataArrayList  = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String Task = cursor.getString(1);
                String ReminderTime = cursor.getString(2);
                int isChecked = cursor.getInt(3);
                String notificationId =  cursor.getString(4);
                TaskData taskData1 = new TaskData(id,Task,ReminderTime,isChecked,notificationId);
                taskDataArrayList.add(taskData1);
            }while (cursor.moveToNext());
        }
        cursor.close();
        readData.close();
        return taskDataArrayList;
    }
    public void deleteTask(String swipedData){
        SQLiteDatabase database = getWritableDatabase();
        /**@param "name=?" is passed to delete a string if passed null , the whole table will be deleted
         * @param swipedData is passed to remove the entire row which contains
         * @param swipedData.
         */
        database.delete(TableName,"name=?",new String[]{swipedData});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query  = "CREATE TABLE "+TableName+
                " ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Task+" TEXT, "+ReminderTime+" TEXT, "+isChecked+" INTEGER,"+notificationId+" INTEGER )";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }
}
