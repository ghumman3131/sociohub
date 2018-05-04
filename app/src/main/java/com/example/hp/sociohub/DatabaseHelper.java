package com.example.hp.sociohub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SchedulePostDataModels_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create SchedulePostDataModels table
        db.execSQL(SchedulePostDataModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SchedulePostDataModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public long insertSchedulePostDataModel(String title, String description , String url , String facebook , String instagram , String twitter , String linkedin , String time) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SchedulePostDataModel.COLUMN_POST_TITLE, title);
        values.put(SchedulePostDataModel.COLUMN_POST_DESCRIPTION, description);
        values.put(SchedulePostDataModel.COLUMN_POST_URL, url);
        values.put(SchedulePostDataModel.COLUMN_POST_FACEBOOK, facebook);
        values.put(SchedulePostDataModel.COLUMN_POST_INSTAGRAM, instagram);
        values.put(SchedulePostDataModel.COLUMN_POST_TWITER, twitter);
        values.put(SchedulePostDataModel.COLUMN_POST_LINKEDIN, linkedin);
        values.put(SchedulePostDataModel.COLUMN_POST_TIME, time);

        // insert row
        long id = db.insert(SchedulePostDataModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }



    public List<SchedulePostDataModel> getAllSchedulePostDataModels() {
        List<SchedulePostDataModel> SchedulePostDataModels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SchedulePostDataModel.TABLE_NAME + " ORDER BY " +
                SchedulePostDataModel.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SchedulePostDataModel data = new SchedulePostDataModel();
                data.id = cursor.getInt(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_ID));
                data.title = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_TITLE));
                data.description = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_DESCRIPTION));
                data.url = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_URL));
                data.facebook = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_FACEBOOK));
                data.instagram = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_INSTAGRAM));
                data.twitter = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_TWITER));
                data.linkedin = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_LINKEDIN));
                data.time = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_TIME));

                SchedulePostDataModels.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return SchedulePostDataModels list
        return SchedulePostDataModels;
    }


    public SchedulePostDataModel getPOST(String  time) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SchedulePostDataModel.TABLE_NAME,
                new String[]{SchedulePostDataModel.COLUMN_ID, SchedulePostDataModel.COLUMN_POST_TITLE , SchedulePostDataModel.COLUMN_POST_DESCRIPTION,
                        SchedulePostDataModel.COLUMN_POST_URL , SchedulePostDataModel.COLUMN_POST_FACEBOOK , SchedulePostDataModel.COLUMN_POST_INSTAGRAM ,
                        SchedulePostDataModel.COLUMN_POST_TWITER , SchedulePostDataModel.COLUMN_POST_LINKEDIN , SchedulePostDataModel.COLUMN_POST_TIME},
                SchedulePostDataModel.COLUMN_POST_TIME + "=?",
                new String[]{time}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        SchedulePostDataModel data = new SchedulePostDataModel();
        data.id = cursor.getInt(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_ID));
        data.title = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_TITLE));
        data.description = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_DESCRIPTION));
        data.url = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_URL));
        data.facebook = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_FACEBOOK));
        data.instagram = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_INSTAGRAM));
        data.twitter = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_TWITER));
        data.linkedin = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_LINKEDIN));
        data.time = cursor.getString(cursor.getColumnIndex(SchedulePostDataModel.COLUMN_POST_TIME));

        // close the db connection
        cursor.close();

        return data;
    }

}
