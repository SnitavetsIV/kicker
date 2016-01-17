package com.snit.kicker.entity;

/**
 * @author Ilya Snitavets
 */
public class GoalStat {

    public static final String TABLE = "goal_stat";
    public static final String ID = "id";
    public static final String USER = "user";
    public static final String GAME = "game";
    public static final String USER_SCORE = "user_score";

    private int id;
    private Game game;
    private User user;
    private int score;

    public GoalStat(User user, Game game) {
        this.user = user;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void plusScore() {
        score++;
        if (score > 10) {
            score = 10;
        }
    }

    public void minusScore() {
        score--;
        if (score < 0) {
            score = 0;
        }
    }
}
