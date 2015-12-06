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
public class AddGameActivity extends AppCompatActivity {

    private DataProvider dataProvider;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);

        dataProvider = new DataProvider(this);

        game = new Game();

        List<User> users = dataProvider.getUsers();

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
