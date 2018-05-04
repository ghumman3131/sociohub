package com.example.hp.sociohub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllScheduledPostsActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private List<SchedulePostDataModel> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scheduled_posts);

        DatabaseHelper db = new DatabaseHelper(this);

        list = db.getAllSchedulePostDataModels();


        recyclerView = findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));

        recyclerView.setAdapter(new Adapter());

    }

    public  class view_holder extends RecyclerView.ViewHolder{

        public TextView title_ , description_ , link_ , time_ , share_ ;

        public view_holder(View itemView) {
            super(itemView);

            title_ = itemView.findViewById(R.id.title_);

            description_ = itemView.findViewById(R.id.description_);

            link_ = itemView.findViewById(R.id.link_);

            time_ = itemView.findViewById(R.id.time_);

            share_ = itemView.findViewById(R.id.share_);


        }
    }


    public class Adapter extends  RecyclerView.Adapter<view_holder>
    {

        @NonNull
        @Override
        public view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.scheduled_post_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(@NonNull view_holder holder, int position) {

            SchedulePostDataModel dataModel = list.get(position);

            holder.title_.setText(dataModel.title);

            holder.description_.setText(dataModel.description);

            holder.link_.setText(dataModel.url);

            String share_on = "" ;

            if(dataModel.facebook.equals("yes"))
            {
                share_on = share_on + " Facebook ";
            }

            if(dataModel.instagram.equals("yes"))
            {
                share_on = share_on + " Instagram ";
            }

            if(dataModel.twitter.equals("yes"))
            {
                share_on = share_on + " Twitter ";
            }

            if(dataModel.linkedin.equals("yes"))
            {
                share_on = share_on + " Linkedin ";
            }

            holder.share_.setText(share_on);

            holder.time_.setText(convertTimeInMillisToDateString(Long.parseLong(dataModel.time)));


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public static String convertTimeInMillisToDateString(long timeInMillis) {
        Date d = new Date(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        return sdf.format(d);
    }
}
