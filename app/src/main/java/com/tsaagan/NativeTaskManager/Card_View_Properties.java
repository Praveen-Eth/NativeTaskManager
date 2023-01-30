package com.tsaagan.NativeTaskManager;

public class Card_View_Properties {
    int cardViewColor;
    String TaskName ;
    Boolean isChecked;
    String ReminderTime;

    public Card_View_Properties(String taskName, Boolean isChecked,String Reminder,int cardViewColor) {
        TaskName = taskName;
        this.isChecked = isChecked;
        ReminderTime = Reminder;
        this.cardViewColor = cardViewColor;
    }
}
