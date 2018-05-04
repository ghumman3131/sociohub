package com.example.hp.sociohub;

/**
 * Created by charanghumman on 04/05/18.
 */

public class SchedulePostDataModel {

    public static final String TABLE_NAME = "posts";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POST_TITLE = "title";
    public static final String COLUMN_POST_DESCRIPTION = "description";
    public static final String COLUMN_POST_URL = "url";
    public static final String COLUMN_POST_FACEBOOK = "facebook";
    public static final String COLUMN_POST_INSTAGRAM = "instagram";
    public static final String COLUMN_POST_TWITER = "twitter";
    public static final String COLUMN_POST_LINKEDIN = "linkedin";
    public static final String COLUMN_POST_TIME = "date_time";

    public int id;
    public String title ;
    public  String description ;
    public String url ;
    public String facebook ;
    public String instagram ;
    public  String twitter ;
    public  String linkedin ;
    public  String time;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_POST_TITLE + " TEXT,"
                    + COLUMN_POST_DESCRIPTION + " TEXT,"
                    + COLUMN_POST_URL + " TEXT,"
                    + COLUMN_POST_FACEBOOK + " TEXT,"
                    + COLUMN_POST_INSTAGRAM + " TEXT,"
                    + COLUMN_POST_TWITER + " TEXT,"
                    + COLUMN_POST_LINKEDIN + " TEXT,"
                    + COLUMN_POST_TIME + " TEXT"
                    + ")";

    public SchedulePostDataModel() {
    }

    public SchedulePostDataModel(int id, String title, String description , String url , String facebook , String instagram , String twitter , String linkedin , String time) {
        this.id = id;

        this.title = title;

        this.description = description;

        this.url = url;
        this.facebook = facebook ;
        this.instagram = instagram ;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.time = time;

    }




}