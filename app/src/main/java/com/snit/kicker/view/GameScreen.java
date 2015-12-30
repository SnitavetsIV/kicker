package com.snit.kicker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        createUserView(rootView);

        addButtonListeners(rootView);

        return rootView;
    }

    private void createUserView(View view) {
        List<User> users = kickerDataManager.getUsers();

        game.setBlueAttack(users.get(0));
        game.setBlueDefence(users.get(1));
        game.setRedAttack(users.get(2));
        game.setRedDefence(users.get(3));

        TextView blueAttackView = (TextView) view.findViewById(R.id.blueAttackView);
        TextView blueDefenceView = (TextView) view.findViewById(R.id.blueDefenceView);
        TextView redAttackView = (TextView) view.findViewById(R.id.redAttackView);
        TextView redDefenceView = (TextView) view.findViewById(R.id.redDefenceView);

        blueAttackView.setText(game.getBlueAttack().getName());
        blueDefenceView.setText(game.getBlueDefence().getName());
        redAttackView.setText(game.getRedAttack().getName());
        redDefenceView.setText(game.getRedDefence().getName());
    }

    private void addButtonListeners(View view){
        Button blueTotalMinus = (Button) view.findViewById(R.id.blueTotalMinus);
        Button blueTotalPlus = (Button) view.findViewById(R.id.blueTotalPlus);

        Button redTotalMinus = (Button) view.findViewById(R.id.redTotalMinus);
        Button redTotalPlus = (Button) view.findViewById(R.id.redTotalPlus);

        blueTotalMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteScoreBlue(v);
            }
        });

        blueTotalPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScoreBlue(v);
            }
        });

        redTotalMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteScoreRed(v);
            }
        });

        redTotalPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScoreRed(v);
            }
        });
    }


    public void addScoreBlue(View view) {
        game.addScoreBlue();
        updateTotalView(view);
    }

    public void addScoreRed(View view) {
        game.addScoreRed();
        updateTotalView(view);
    }

    public void deleteScoreBlue(View view) {
        game.deleteScoreBlue();
        updateTotalView(view);
    }

    public void deleteScoreRed(View view) {
        game.deleteScoreRed();
        updateTotalView(view);
    }

    private void updateTotalView(View view) {
        View rootView = view.getRootView();

        TextView scoreTotalRed = (TextView) rootView.findViewById(R.id.redTotalProgress);
        TextView scoreTotalBlue = (TextView) rootView.findViewById(R.id.blueTotalProgress);

        scoreTotalRed.setText(String.valueOf(game.getScoreRed()));
        scoreTotalBlue.setText(String.valueOf(game.getScoreBlue()));
    }
	
}
