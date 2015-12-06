package com.snit.kicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.DataProvider;
import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.User;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class GameActivity extends AppCompatActivity {

    public static final String EXTRA_BLUE_ATTACK = "EXTRA_BLUE_ATTACK";
    public static final String EXTRA_BLUE_DEFENCE = "EXTRA_BLUE_DEFENCE";
    public static final String EXTRA_RED_ATTACK = "EXTRA_RED_ATTACK";
    public static final String EXTRA_RED_DEFENCE = "EXTRA_RED_DEFENCE";

    private DataProvider dataProvider;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        dataProvider = new DataProvider(this);

        game = new Game();
        List<User> users = dataProvider.getUsers();

        game.setBlueAttack(users.get(0));
        game.setBlueDefence(users.get(1));
        game.setRedAttack(users.get(2));
        game.setRedDefence(users.get(3));

        TextView blueAttackView = (TextView) findViewById(R.id.blueAttackView);
        TextView blueDefenceView = (TextView) findViewById(R.id.blueDefenceView);
        TextView redAttackView = (TextView) findViewById(R.id.redAttackView);
        TextView redDefenceView = (TextView) findViewById(R.id.redDefenceView);

        blueAttackView.setText(game.getBlueAttack().getName());
        blueDefenceView.setText(game.getBlueDefence().getName());
        redAttackView.setText(game.getRedAttack().getName());
        redDefenceView.setText(game.getRedDefence().getName());
    }

    public void addScoreBlue(View view) {
        game.addScoreBlue();
        updateView(view);
    }

    public void addScoreRed(View view) {
        game.addScoreRed();
        updateView(view);
    }

    public void deleteScoreBlue(View view) {
        game.deleteScoreBlue();
        updateView(view);
    }

    public void deleteScoreRed(View view) {
        game.deleteScoreRed();
        updateView(view);
    }

    private void updateView(View view) {
        TextView scoreRed = (TextView) findViewById(R.id.redScoreView);
        TextView scoreBlue = (TextView) findViewById(R.id.blueScoreView);
        TextView scoreTotalRed = (TextView) findViewById(R.id.redTotalView);
        TextView scoreTotalBlue = (TextView) findViewById(R.id.blueTotalView);

        scoreRed.setText(String.valueOf(game.getScoreRed()));
        scoreBlue.setText(String.valueOf(game.getScoreBlue()));
        scoreTotalRed.setText(String.valueOf(game.getScoreRed()));
        scoreTotalBlue.setText(String.valueOf(game.getScoreBlue()));
    }

    public void saveGame(View view) {
        dataProvider.saveGame(game);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
