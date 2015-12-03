package com.example.dewartan.chronosoptim;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ChronosOptim.db";
    public static final int DATABASE_VERSION = 1;
    public static final String EVENT_TABLE_NAME = "event";
    public static final String TEAM_TABLE_NAME = "team";

    private static final String[] EVENT_COLUMNS = new String[]{"_id","title","description","eventdate","starttime","endtime","location","subtitle"};
    private static final String[] TEAM_COLUMNS = new String[]{"_id","name","description","members"};

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table event ( _id integer primary key, title text, description text, eventdate text, starttime text, endtime text, location text, subtitle text)");
        db.execSQL("create table team ( _id integer primary key, name text, description text, members text)");

        insert(db,new Event("Vertica", "12-12-2015", "17:00", "18:00", "Meeting, We will go over the different views that need fixing and additionally, go over the backend server stuff", "MAD Project Meeting", "Fix views on the events page"));
        insert(db,new Event("SSC", "12-12-2015", "13:00", "14:00", "We need to finalize the columns in the migration table and different routes for calling CRUD operations", "NanoTwitter", "105B NanoTwitter Project"));
        insert(db,new Event("Shapiro", "12-22-2015", "11:30", "12:30", "Meet with bob to discuss the different internet plans Comcast has to offer for the apartment", "Food", "Lunch with Bob"));
        insert(db,new Event("Cambridge, MA", "12-24-2015", "18:00", "19:00", "Prepare for interview with company x, Things to do: research products, pratice questions, and iron clothes ", "Interview", "Interview with Company"));
        insert(db,new Event("Home", "12-31-2015", "18:00", "19:00", "Bring korean pot, Things to grab at Shaws: Chocolate & Flowers ", "Date Night", "Dinner with Fay"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS event");
        db.execSQL("DROP TABLE IF EXISTS team");
        onCreate(db);
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ EVENT_TABLE_NAME);
        db.execSQL("DELETE FROM "+ TEAM_TABLE_NAME);
    }

    public void insert(Event event){
        insert(getWritableDatabase(),event);
    }
    public void insert(SQLiteDatabase db,Event event){
        db.insert("event", null, event.content());
    }
    public void insert(Team team){
        insert(getWritableDatabase(), team);
    }
    public void insert(SQLiteDatabase db,Team team){
        db.insert("team", null, team.content());
    }

    public void delete (Event e){
        // _id,title,description,eventdate,starttime,endtime,location,subtitle
        SQLiteDatabase db=getWritableDatabase();
        String where=EVENT_COLUMNS[1]+"='"+e.getTitle()+"' AND "+
                EVENT_COLUMNS[2]+"='"+e.getDescription()+"' AND "+
                EVENT_COLUMNS[3]+"='"+e.getDate()+"' AND "+
                EVENT_COLUMNS[4]+"='"+e.getStartTime()+"' AND "+
                EVENT_COLUMNS[5]+"='"+e.getEndTime()+"' AND "+
                EVENT_COLUMNS[6]+"='"+e.getLocation()+"' AND "+
                EVENT_COLUMNS[7]+"='"+e.getSubtitle()+"'";

        Log.w("shit",where);


        // which, where, whereArgs, groupBy, having, orderBy, limit
        Cursor cursor = db.query(EVENT_TABLE_NAME, new String[]{"rowid"}, where, null, null, null, null, "1");
        if(cursor.moveToFirst()){
            int rowid=cursor.getInt(0);
            db.delete(EVENT_TABLE_NAME, "rowid=" + rowid, null);
        }
    }

    public ArrayList<Event> pull(){
        ArrayList<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from event", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            String title = res.getString(1);
            String description = res.getString(2);
            String eventdate = res.getString(3);
            String starttime = res.getString(4);
            String endtime = res.getString(5);
            String location = res.getString(6);
            String subtitle = res.getString(7);
            events.add(new Event(location, eventdate, starttime, endtime, description, title, subtitle));
            res.moveToNext();
        }
        return events;
    }
}