package com.example.hp.sociohub;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by charanghumman on 04/05/18.
 */

public class SchedulePostDIalog extends Dialog {

    private TextView select_date , select_time , select_date_heading , select_time_heading ;

    private int curr_day , curr_month , curr_year , curr_hour , curr_minute ;

    private Calendar calendar ;

    private Context context ;

    private int selected_day , selected_month , selected_year , selected_hour , selected_minute ;

    private Button schedule ;

    private String title , description , url , facebook , twitter , instagram , linkedin ;

    public SchedulePostDIalog(@NonNull Context context, int themeResId , String title, String description , String url , String facebook , String instagram , String twitter , String linkedin )
    {
        super(context, themeResId);

        this.context = context ;

        setContentView(R.layout.schedule_post_dialog);

        this.title = title;
        this.description = description ;
        this.url = url;
        this.facebook = facebook ;
        this.twitter = twitter;
        this.instagram = instagram;
        this.linkedin = linkedin;

        select_date = findViewById(R.id.selected_date);

        select_time = findViewById(R.id.set_time);

        select_date_heading = findViewById(R.id.set_date_heading);

        schedule = findViewById(R.id.schedule_btn);


        select_time_heading = findViewById(R.id.set_time_heading);

        calendar = Calendar.getInstance();

        curr_day = calendar.get(Calendar.DAY_OF_WEEK);

        curr_month = calendar.get(Calendar.MONTH);

        curr_year = calendar.get(Calendar.YEAR);

        curr_hour = calendar.get(Calendar.HOUR);

        curr_minute = calendar.get(Calendar.MINUTE);



        select_date_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_date_picker();
            }
        });

        select_time_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                show_time_picker();
            }
        });



        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                    calendar.set(selected_year, selected_month, selected_day,
                            selected_hour, selected_minute, 0);

                setAlarm(calendar.getTimeInMillis());


            }
        });


    }

   private void show_date_picker()
   {
       DatePickerDialog dp = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

               select_date.setText(String.valueOf(i2)+" / "+String.valueOf(i1)+" / "+String.valueOf(i));

               selected_year = i;

               selected_month = i1;

               selected_day = i2 ;

           }
       } , curr_year, curr_month, curr_day);

       dp.getDatePicker().setMinDate(System.currentTimeMillis());

       dp.show();
   }

   private void show_time_picker()
   {
       TimePickerDialog tp = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker timePicker, int i, int i1) {

               select_time.setText(getTime(i , i1));

               selected_hour = i ;

               selected_minute = i1;

           }
       } , curr_hour , curr_minute , false);

       tp.show();
   }



    private String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h : mm a");
        return formatter.format(tme);
    }

    private void setAlarm(long time) {
        //getting the alarm manager

        DatabaseHelper db = new DatabaseHelper(context);

        db.insertSchedulePostDataModel(title , description , url , facebook , instagram , twitter , linkedin , String.valueOf(time));

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(context, ScheduleAlarm.class);

        i.putExtra("time" , String.valueOf(time));

        //creating a pending intent using the intent

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);

        PendingIntent pi = PendingIntent.getBroadcast(context, uniqueInt, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(context, "Post Scheduled", Toast.LENGTH_SHORT).show();


        dismiss();

        ((Activity)context).finish();
    }


}
