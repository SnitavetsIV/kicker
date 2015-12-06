package com.snit.kicker.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.DataProvider;
import com.snit.kicker.entity.Game;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class StatActivity extends AppCompatActivity {

    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stat);

        dataProvider = new DataProvider(this);

        List<Game> games = dataProvider.getGames();

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.listStat);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (Game game: games) {
            View view = vi.inflate(R.layout.stat_list_item, viewGroup, false);

            TextView teamsView = (TextView) view.findViewById(R.id.teamsView);
            String teams = game.getBlueAttack().getName() + " + " + game.getBlueDefence().getName()
                    + " : " + game.getRedAttack().getName() + " + " + game.getRedDefence().getName();
            teamsView.setText(teams);
            teamsView.setTextColor(Color.BLACK);

            TextView scoreView = (TextView) view.findViewById(R.id.scoreView);
            String score = game.getScoreBlue() + " : " + game.getScoreRed();
            scoreView.setText(score);
            scoreView.setTextColor(Color.BLACK);

            viewGroup.addView(view, params);
        }
    }

}
