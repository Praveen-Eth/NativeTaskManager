package com.tsaagan.NativeTaskManager;

public class TaskData {
    int id ;
    String Task ;
    String ReminderTime ;
    int isChecked ;
    String notificationId;

    public TaskData(int id, String task, String reminderTime, int isChecked,String notificationId) {
        this.id = id;
        Task = task;
        ReminderTime = reminderTime;
        this.isChecked = isChecked;
        this.notificationId = notificationId;
    }
}
