package com.snit.kicker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.GoalStat;
import com.snit.kicker.entity.User;

/**
 * @author Ilya Snitavets
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

    private static final String DATABASE_NAME = "kicker.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table User
        String tableUser = "CREATE TABLE " + User.TABLE  + "("
                + User.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.NAME + " TEXT)";

        db.execSQL(tableUser);

        //Create table Game
        String tableGame = "CREATE TABLE " + Game.TABLE  + "("
                + Game.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Game.BLUE_ATTACK + " INTEGER,"
                + Game.BLUE_DEFENCE + " INTEGER,"
                + Game.RED_ATTACK + " INTEGER,"
                + Game.RED_DEFENCE + " INTEGER,"
                + Game.BLUE_SCORE + " INTEGER,"
                + Game.RED_SCORE + " INTEGER,"
                + "FOREIGN KEY(" + Game.BLUE_ATTACK + ") REFERENCES " + User.TABLE + "(" + User.ID + "),"
                + "FOREIGN KEY(" + Game.BLUE_DEFENCE + ") REFERENCES " + User.TABLE + "(" + User.ID + "),"
                + "FOREIGN KEY(" + Game.RED_ATTACK + ") REFERENCES " + User.TABLE + "(" + User.ID + "),"
                + "FOREIGN KEY(" + Game.RED_DEFENCE + ") REFERENCES " + User.TABLE + "(" + User.ID + "))";

        //Create table
        String tableGoalStat = "CREATE TABLE " + GoalStat.TABLE  + "("
                + GoalStat.ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + GoalStat.GAME + " INTEGER,"
                + GoalStat.USER + " INTEGER,"
                + GoalStat.USER_SCORE + " INTEGER,"
                + "FOREIGN KEY(" + GoalStat.GAME + ") REFERENCES " + Game.TABLE + "(" + Game.ID + "),"
                + "FOREIGN KEY(" + GoalStat.USER + ") REFERENCES " + User.TABLE + "(" + User.ID + "))";

        db.execSQL(tableGame);

        //Insert test values
        fillDebugData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Game.TABLE);
        //TODO: migrating data
        onCreate(db);
    }

    private void fillDebugData(SQLiteDatabase db) {
        for (int i = 0; i < 10; i++) {
            ContentValues cv = new ContentValues();
            cv.put(User.NAME, "user" + i);
            db.insert(User.TABLE, null, cv);
        }
    }

}
