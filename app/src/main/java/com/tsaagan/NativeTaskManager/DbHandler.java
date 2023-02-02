package com.tsaagan.NativeTaskManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.security.PublicKey;

public class DbHandler extends SQLiteOpenHelper {
    public static final String DataBaseName = "TaskData";
    public static final int DataBaseVersion = 1;
    public static final String TableName = "task";






    public DbHandler(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String query  = "CREATE TABLE "+TableName+"("+

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
