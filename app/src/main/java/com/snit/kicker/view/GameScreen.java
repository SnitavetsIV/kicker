package com.snit.kicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;
import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.User;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class GameScreen extends Fragment {

    private KickerDataManager kickerDataManager;
    private Game game;

    public GameScreen() {
    }

    public void setKickerDataManager(KickerDataManager kickerDataManager) {
        this.kickerDataManager = kickerDataManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (kickerDataManager == null) {
            kickerDataManager = new KickerDataManager(getContext());
        }

        View rootView = inflater.inflate(R.layout.new_game, container, false);

        game = new Game();
        List<User> users = kickerDataManager.getUsers();

        game.setBlueAttack(users.get(0));
        game.setBlueDefence(users.get(1));
        game.setRedAttack(users.get(2));
        game.setRedDefence(users.get(3));

        TextView blueAttackView = (TextView) rootView.findViewById(R.id.blueAttackView);
        TextView blueDefenceView = (TextView) rootView.findViewById(R.id.blueDefenceView);
        TextView redAttackView = (TextView) rootView.findViewById(R.id.redAttackView);
        TextView redDefenceView = (TextView) rootView.findViewById(R.id.redDefenceView);

        blueAttackView.setText(game.getBlueAttack().getName());
        blueDefenceView.setText(game.getBlueDefence().getName());
        redAttackView.setText(game.getRedAttack().getName());
        redDefenceView.setText(game.getRedDefence().getName());

        return rootView;
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
        TextView scoreRed = (TextView) view.findViewById(R.id.redScoreView);
        TextView scoreBlue = (TextView) view.findViewById(R.id.blueScoreView);
        TextView scoreTotalRed = (TextView) view.findViewById(R.id.redTotalView);
        TextView scoreTotalBlue = (TextView) view.findViewById(R.id.blueTotalView);

        scoreRed.setText(String.valueOf(game.getScoreRed()));
        scoreBlue.setText(String.valueOf(game.getScoreBlue()));
        scoreTotalRed.setText(String.valueOf(game.getScoreRed()));
        scoreTotalBlue.setText(String.valueOf(game.getScoreBlue()));
    }

    public void saveGame(View view) {
        kickerDataManager.saveGame(game);
    }
	
}
