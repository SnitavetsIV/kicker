package com.snit.kicker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;
import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.GoalStat;
import com.snit.kicker.entity.User;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class GameScreen extends Fragment {

    private KickerDataManager kickerDataManager;
    private Game game;

    private GoalStat blueDefenceGoalStat;
    private GoalStat blueAttackGoalStat;
    private GoalStat redDefenceGoalStat;
    private GoalStat redAttackGoalStat;

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

        startNewGame(rootView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        startNewGame(getView());
    }

    private void startNewGame(View rootView) {
        if (rootView == null) {
            return;
        }
        game = new Game();

        List<User> users = kickerDataManager.getAllUsers();

        //TODO calculate new users based on statistic
        game.setBlueAttack(users.get(0));
        game.setBlueDefence(users.get(1));
        game.setRedAttack(users.get(2));
        game.setRedDefence(users.get(3));

        blueAttackGoalStat = new GoalStat(game.getBlueAttack(), game);
        blueDefenceGoalStat = new GoalStat(game.getBlueDefence(), game);
        redDefenceGoalStat = new GoalStat(game.getRedDefence(), game);
        redAttackGoalStat = new GoalStat(game.getRedAttack(), game);

        createSpinners(rootView, users);

        addTotalScoreButtonsListener(rootView);
        addUserScoreButtonsListener(rootView);

        addSpinnersListener(rootView);

        updateTotalView(rootView);
    }

    private void createSpinners(View view, List<User> users) {
        Spinner blueAttackSpinner = (Spinner) view.findViewById(R.id.blueAttackSpinner);
        Spinner blueDefenceSpinner = (Spinner) view.findViewById(R.id.blueDefenceSpinner);
        Spinner redAttackSpinner = (Spinner) view.findViewById(R.id.redAttackSpinner);
        Spinner redDefenceSpinner = (Spinner) view.findViewById(R.id.redDefenceSpinner);

        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, 0, users);

        blueAttackSpinner.setAdapter(userArrayAdapter);
        blueAttackSpinner.setSelection(userArrayAdapter.getPosition(game.getBlueAttack()));

        blueDefenceSpinner.setAdapter(userArrayAdapter);
        blueDefenceSpinner.setSelection(userArrayAdapter.getPosition(game.getBlueDefence()));

        redAttackSpinner.setAdapter(userArrayAdapter);
        redAttackSpinner.setSelection(userArrayAdapter.getPosition(game.getRedAttack()));

        redDefenceSpinner.setAdapter(userArrayAdapter);
        redDefenceSpinner.setSelection(userArrayAdapter.getPosition(game.getRedDefence()));
    }

    private void addSpinnersListener(final View rootView){
        Spinner blueAttackSpinner = (Spinner) rootView.findViewById(R.id.blueAttackSpinner);
        Spinner blueDefenceSpinner = (Spinner) rootView.findViewById(R.id.blueDefenceSpinner);
        Spinner redAttackSpinner = (Spinner) rootView.findViewById(R.id.redAttackSpinner);
        Spinner redDefenceSpinner = (Spinner) rootView.findViewById(R.id.redDefenceSpinner);

        AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                User selectedUser = (User) parent.getSelectedItem();
                GoalStat goalStat;
                switch (spinner.getId()) {
                    case R.id.blueAttackSpinner:
                        kickerDataManager.insertOrUpdateGoalStat(blueAttackGoalStat);
                        game.setBlueAttack(selectedUser);
                        goalStat = kickerDataManager.findGoalStat(selectedUser, game);
                        if (goalStat == null) {
                            blueAttackGoalStat = new GoalStat(selectedUser, game);
                        } else {
                            blueAttackGoalStat = goalStat;
                        }
                        break;
                    case R.id.blueDefenceSpinner:
                        kickerDataManager.insertOrUpdateGoalStat(blueDefenceGoalStat);
                        game.setBlueDefence(selectedUser);
                        goalStat = kickerDataManager.findGoalStat(selectedUser, game);
                        if (goalStat == null) {
                            blueDefenceGoalStat = new GoalStat(selectedUser, game);
                        } else {
                            blueDefenceGoalStat = goalStat;
                        }
                        break;
                    case R.id.redAttackSpinner:
                        kickerDataManager.insertOrUpdateGoalStat(redAttackGoalStat);
                        game.setRedAttack(selectedUser);
                        goalStat = kickerDataManager.findGoalStat(selectedUser, game);
                        if (goalStat == null) {
                            redAttackGoalStat = new GoalStat(selectedUser, game);
                        } else {
                            redAttackGoalStat = goalStat;
                        }
                        break;
                    case R.id.redDefenceSpinner:
                        kickerDataManager.insertOrUpdateGoalStat(redDefenceGoalStat);
                        game.setRedDefence(selectedUser);
                        goalStat = kickerDataManager.findGoalStat(selectedUser, game);
                        if (goalStat == null) {
                            redDefenceGoalStat = new GoalStat(selectedUser, game);
                        } else {
                            redDefenceGoalStat = goalStat;
                        }
                        break;
                }
                updateTotalView(view);
                kickerDataManager.insertOrUpdateGame(game);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        blueAttackSpinner.setOnItemSelectedListener(selectedListener);
        blueDefenceSpinner.setOnItemSelectedListener(selectedListener);
        redAttackSpinner.setOnItemSelectedListener(selectedListener);
        redDefenceSpinner.setOnItemSelectedListener(selectedListener);
    }

    private void addUserScoreButtonsListener(final View rootView) {
        Button blueDefenceMinus = (Button) rootView.findViewById(R.id.blueDefenceMinus);
        Button blueDefencePlus = (Button) rootView.findViewById(R.id.blueDefencePlus);
        Button redDefenceMinus = (Button) rootView.findViewById(R.id.redDefenceMinus);
        Button redDefencePlus = (Button) rootView.findViewById(R.id.redDefencePlus);
        Button blueAttackMinus = (Button) rootView.findViewById(R.id.blueAttackMinus);
        Button blueAttackPlus = (Button) rootView.findViewById(R.id.blueAttackPlus);
        Button redAttackMinus = (Button) rootView.findViewById(R.id.redAttackMinus);
        Button redAttackPlus = (Button) rootView.findViewById(R.id.redAttackPlus);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoalStat goalStat = null;
                switch (view.getId()) {
                    case R.id.blueDefenceMinus:
                        blueDefenceGoalStat.minusScore();
                        goalStat = blueDefenceGoalStat;
                        break;
                    case R.id.blueDefencePlus:
                        blueDefenceGoalStat.plusScore();
                        goalStat = blueDefenceGoalStat;
                        break;
                    case R.id.redDefenceMinus:
                        redDefenceGoalStat.minusScore();
                        goalStat = redDefenceGoalStat;
                        break;
                    case R.id.redDefencePlus:
                        redDefenceGoalStat.plusScore();
                        goalStat = redDefenceGoalStat;
                        break;
                    case R.id.blueAttackMinus:
                        blueAttackGoalStat.minusScore();
                        goalStat = blueAttackGoalStat;
                        break;
                    case R.id.blueAttackPlus:
                        blueAttackGoalStat.plusScore();
                        goalStat = blueAttackGoalStat;
                        break;
                    case R.id.redAttackMinus:
                        redAttackGoalStat.minusScore();
                        goalStat = redAttackGoalStat;
                        break;
                    case R.id.redAttackPlus:
                        redAttackGoalStat.plusScore();
                        goalStat = redAttackGoalStat;
                        break;
                }
                kickerDataManager.insertOrUpdateGoalStat(goalStat);
                kickerDataManager.insertOrUpdateGame(game);
                updateTotalView(view);
            }
        };

        blueDefenceMinus.setOnClickListener(clickListener);
        blueDefencePlus.setOnClickListener(clickListener);
        redDefenceMinus.setOnClickListener(clickListener);
        redDefencePlus.setOnClickListener(clickListener);
        blueAttackMinus.setOnClickListener(clickListener);
        blueAttackPlus.setOnClickListener(clickListener);
        redAttackMinus.setOnClickListener(clickListener);
        redAttackPlus.setOnClickListener(clickListener);
    }

    private void addTotalScoreButtonsListener(final View rootView){
        Button blueTotalMinus = (Button) rootView.findViewById(R.id.blueTotalMinus);
        Button blueTotalPlus = (Button) rootView.findViewById(R.id.blueTotalPlus);
        Button redTotalMinus = (Button) rootView.findViewById(R.id.redTotalMinus);
        Button redTotalPlus = (Button) rootView.findViewById(R.id.redTotalPlus);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.blueTotalMinus:
                        game.deleteScoreBlue();
                        break;
                    case R.id.blueTotalPlus:
                        game.addScoreBlue();
                        break;
                    case R.id.redTotalMinus:
                        game.deleteScoreRed();
                        break;
                    case R.id.redTotalPlus:
                        game.addScoreRed();
                        break;
                }
                updateTotalView(view);
                kickerDataManager.insertOrUpdateGame(game);
            }
        };

        blueTotalMinus.setOnClickListener(clickListener);
        blueTotalPlus.setOnClickListener(clickListener);
        redTotalMinus.setOnClickListener(clickListener);
        redTotalPlus.setOnClickListener(clickListener);
    }

    private void updateTotalView(View view) {
        View rootView = view.getRootView();

        AdvancedTextView scoreTotalRed = (AdvancedTextView) rootView.findViewById(R.id.redTotalProgress);
        AdvancedTextView scoreTotalBlue = (AdvancedTextView) rootView.findViewById(R.id.blueTotalProgress);

        scoreTotalRed.setValue(game.getScoreRed());
        scoreTotalBlue.setValue(game.getScoreBlue());

        TextView blueAttackView = (TextView) rootView.findViewById(R.id.blueAttackView);
        TextView redAttackView = (TextView) rootView.findViewById(R.id.redAttackView);
        TextView redDefenceView = (TextView) rootView.findViewById(R.id.redDefenceView);
        TextView blueDefenceView = (TextView) rootView.findViewById(R.id.blueDefenceView);

        blueAttackView.setText(String.valueOf(blueAttackGoalStat.getScore()));
        redAttackView.setText(String.valueOf(redAttackGoalStat.getScore()));
        redDefenceView.setText(String.valueOf(redDefenceGoalStat.getScore()));
        blueDefenceView.setText(String.valueOf(blueDefenceGoalStat.getScore()));

    }
	
}
