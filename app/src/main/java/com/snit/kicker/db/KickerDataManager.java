package com.snit.kicker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.GoalStat;
import com.snit.kicker.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class KickerDataManager {

    private final DBHelper dbHelper;

    private static final String ALL_USERS = "SELECT " + User.ID + "," + User.NAME + " FROM " + User.TABLE;
    private static final String ALL_GAMES = "SELECT " +
            "g." + Game.ID + "," +
            "blueAttack." + User.ID + "," + "blueAttack." + User.NAME + "," +
            "blueDefence." + User.ID + "," + "blueDefence." + User.NAME + "," +
            "redAttack." + User.ID + "," + "redAttack." + User.NAME + "," +
            "redDefence." + User.ID + "," + "redDefence." + User.NAME + "," +
            "g." + Game.BLUE_SCORE + ","+
            "g." + Game.RED_SCORE + " FROM " + Game.TABLE + " g" +
            " LEFT JOIN " + User.TABLE + " blueAttack ON blueAttack." + User.ID + "=g." + Game.BLUE_ATTACK +
            " LEFT JOIN " + User.TABLE + " blueDefence ON blueDefence." + User.ID + "=g." + Game.BLUE_DEFENCE +
            " LEFT JOIN " + User.TABLE + " redAttack ON redAttack." + User.ID + "=g." + Game.RED_ATTACK +
            " LEFT JOIN " + User.TABLE + " redDefence ON redDefence." + User.ID + "=g." + Game.RED_DEFENCE;

    private static final String GOAL_STAT_BY_USER_GAME = "SELECT " +
            "gs." + GoalStat.ID + "," +
            "gs." + GoalStat.USER_SCORE + " FROM " + GoalStat.TABLE + " gs " +
            "WHERE " + " gs." + GoalStat.USER + " = ? AND gs." + GoalStat.GAME + " = ? " +
            " LIMIT 1";



    public KickerDataManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(ALL_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                User user = new User();
                user.setId(id);
                user.setName(name);
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public List<Game> getGames() {
        ArrayList<Game> games = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(ALL_GAMES, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);

                int idBlueAttack = cursor.getInt(1);
                String nameBlueAttack = cursor.getString(2);
                User blueAttack = new User();
                blueAttack.setId(idBlueAttack);
                blueAttack.setName(nameBlueAttack);

                int idBlueDefence = cursor.getInt(3);
                String nameBlueDefence = cursor.getString(4);
                User blueDefence = new User();
                blueDefence.setId(idBlueDefence);
                blueDefence.setName(nameBlueDefence);

                int idRedAttack = cursor.getInt(5);
                String nameRedAttack = cursor.getString(6);
                User redAttack = new User();
                redAttack.setId(idRedAttack);
                redAttack.setName(nameRedAttack);

                int idRedDefence = cursor.getInt(7);
                String nameRedDefence = cursor.getString(8);
                User redDefence = new User();
                redDefence.setId(idRedDefence);
                redDefence.setName(nameRedDefence);

                int scoreBlue = cursor.getInt(9);
                int scoreRed = cursor.getInt(10);

                Game game = new Game();
                game.setId(id);
                game.setBlueAttack(blueAttack);
                game.setBlueDefence(blueDefence);
                game.setRedAttack(redAttack);
                game.setRedDefence(redDefence);
                game.setScoreBlue(scoreBlue);
                game.setScoreRed(scoreRed);
                games.add(game);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return games;
    }

    public void insertOrUpdateGame(Game game) {
        if (game == null) {
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Game.BLUE_ATTACK, game.getBlueAttack().getId());
        cv.put(Game.BLUE_DEFENCE, game.getBlueDefence().getId());
        cv.put(Game.RED_ATTACK, game.getRedAttack().getId());
        cv.put(Game.RED_DEFENCE, game.getRedDefence().getId());
        cv.put(Game.BLUE_SCORE, game.getScoreBlue());
        cv.put(Game.RED_SCORE, game.getScoreRed());
        if (game.getId() > 0) {
            db.update(Game.TABLE, cv, "id = ?", new String[]{String.valueOf(game.getId())});
        } else {
            int id = (int) db.insert(Game.TABLE, null, cv);
            game.setId(id);
        }
        db.close();
    }

    public void insertOrUpdateGoalStat(GoalStat goalStat) {
        if (goalStat == null) {
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GoalStat.USER, goalStat.getUser().getId());
        cv.put(GoalStat.GAME, goalStat.getGame().getId());
        cv.put(GoalStat.USER_SCORE, goalStat.getScore());
        if (goalStat.getId() > 0) {
            db.update(GoalStat.TABLE, cv, "id = ?", new String[]{String.valueOf(goalStat.getId())});
        } else {
            int id = (int) db.insert(GoalStat.TABLE, null, cv);
            goalStat.setId(id);
        }
        db.close();
    }

    public GoalStat findGoalStat(User user, Game game) {
        if (user == null || game == null) {
            return null;
        }
        GoalStat ret = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(GOAL_STAT_BY_USER_GAME,
                new String[] {String.valueOf(user.getId()), String.valueOf(game.getId())});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            int score = cursor.getInt(1);
            ret = new GoalStat(user, game);
            ret.setScore(score);
            ret.setId(id);
        }
        cursor.close();
        db.close();
        return ret;
    }

    public void deleteUser(User user) {
        if (user == null) {
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(User.TABLE, User.ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void insertOrUpdateUser(User user) {
        if (user == null) {
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(User.NAME, user.getName());
        if (user.getId() > 0) {
            db.update(User.TABLE, cv, "id = ?", new String[]{String.valueOf(user.getId())});
        } else {
            int id = (int) db.insert(User.TABLE, null, cv);
            user.setId(id);
        }
        db.close();
    }
}
