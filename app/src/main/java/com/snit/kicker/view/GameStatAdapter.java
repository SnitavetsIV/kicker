package com.snit.kicker.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;
import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.GoalStat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class GameStatAdapter extends BaseAdapter {

    private List<Game> games;
    KickerDataManager kickerDataManager;
    private LayoutInflater layoutInflater;

    public GameStatAdapter(Context context, List<Game> listData, KickerDataManager kickerDataManager) {
        this.games = listData;
        layoutInflater = LayoutInflater.from(context);
        this.kickerDataManager = kickerDataManager;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.stat_list_item, parent, false);

        Game game = games.get(position);

        GoalStat blueAttackStat = kickerDataManager.findGoalStat(game.getBlueAttack(), game);
        GoalStat blueDefenceStat = kickerDataManager.findGoalStat(game.getBlueDefence(), game);
        GoalStat redAttackStat = kickerDataManager.findGoalStat(game.getRedAttack(), game);
        GoalStat redDefenceStat = kickerDataManager.findGoalStat(game.getRedDefence(), game);

        TextView teamsView = (TextView) convertView.findViewById(R.id.teamsView);
        String teams = game.getBlueAttack().getName() + "(" + blueAttackStat.getScore() + ")" +
                " + " + game.getBlueDefence().getName() + "(" + blueDefenceStat.getScore() + ")" +
                " : " + game.getRedAttack().getName() + "(" + redAttackStat.getScore() + ")" +
                " + " + game.getRedDefence().getName() + "(" + redDefenceStat.getScore() + ")";
        teamsView.setText(teams);
        teamsView.setTextColor(Color.BLACK);

        TextView scoreView = (TextView) convertView.findViewById(R.id.scoreView);
        String score = game.getScoreBlue() + " : " + game.getScoreRed();
        scoreView.setText(score);
        scoreView.setTextColor(Color.BLACK);
        return convertView;
    }
}
