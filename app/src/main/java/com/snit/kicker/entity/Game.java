package com.snit.kicker.entity;

/**
 * @author Ilya Snitavets
 */
public class Game {

    public static final String TABLE = "game";
    public static final String ID = "id";
    public static final String BLUE_ATTACK = "blueAttack";
    public static final String BLUE_DEFENCE = "blueDefence";
    public static final String RED_ATTACK = "redAttack";
    public static final String RED_DEFENCE = "redDefence";
    public static final String BLUE_SCORE = "blueScore";
    public static final String RED_SCORE = "redScore";

    private int id = -1;

    private User blueAttack;
    private User blueDefence;

    private User redAttack;
    private User redDefence;

    private int scoreBlue;
    private int scoreRed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getBlueAttack() {
        return blueAttack;
    }

    public void setBlueAttack(User blueAttack) {
        this.blueAttack = blueAttack;
    }

    public User getBlueDefence() {
        return blueDefence;
    }

    public void setBlueDefence(User blueDefence) {
        this.blueDefence = blueDefence;
    }

    public User getRedAttack() {
        return redAttack;
    }

    public void setRedAttack(User redAttack) {
        this.redAttack = redAttack;
    }

    public User getRedDefence() {
        return redDefence;
    }

    public void setRedDefence(User redDefence) {
        this.redDefence = redDefence;
    }

    public int getScoreBlue() {
        return scoreBlue;
    }

    public void setScoreBlue(int scoreBlue) {
        this.scoreBlue = scoreBlue;
        checkScore();
    }

    public int getScoreRed() {
        return scoreRed;
    }

    public void setScoreRed(int scoreRed) {
        this.scoreRed = scoreRed;
        checkScore();
    }

    public void addScoreBlue() {
        scoreBlue++;
        checkScore();
    }

    public void addScoreRed() {
        scoreRed++;
        checkScore();
    }

    public void deleteScoreRed() {
        scoreRed--;
        checkScore();
    }

    public void deleteScoreBlue() {
        scoreBlue--;
        checkScore();
    }

    private void checkScore() {
        if (scoreBlue > 10) {
            scoreBlue = 10;
        } else if (scoreBlue < 0){
            scoreBlue = 0;
        }
        if (scoreRed > 10) {
            scoreRed = 10;
        } else if (scoreRed < 0){
            scoreRed = 0;
        }
    }


}
