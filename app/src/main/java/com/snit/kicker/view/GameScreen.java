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
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;
import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.User;

import java.util.ArrayList;
import java.util.Collections;
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

        createSpinners(rootView, users);

        addButtonsListener(rootView);
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

    private void addSpinnersListener(View rootView){
        Spinner blueAttackSpinner = (Spinner) rootView.findViewById(R.id.blueAttackSpinner);
        Spinner blueDefenceSpinner = (Spinner) rootView.findViewById(R.id.blueDefenceSpinner);
        Spinner redAttackSpinner = (Spinner) rootView.findViewById(R.id.redAttackSpinner);
        Spinner redDefenceSpinner = (Spinner) rootView.findViewById(R.id.redDefenceSpinner);

        AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                User selectedUser = (User) parent.getSelectedItem();
                switch (spinner.getId()) {
                    case R.id.blueAttackSpinner:
                        game.setBlueAttack(selectedUser);
                        break;
                    case R.id.blueDefenceSpinner:
                        game.setBlueDefence(selectedUser);
                        break;
                    case R.id.redAttackSpinner:
                        game.setRedAttack(selectedUser);
                        break;
                    case R.id.redDefenceSpinner:
                        game.setRedDefence(selectedUser);
                        break;
                }
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

    private void addButtonsListener(View rootView){
        Button blueTotalMinus = (Button) rootView.findViewById(R.id.blueTotalMinus);
        Button blueTotalPlus = (Button) rootView.findViewById(R.id.blueTotalPlus);
        Button redTotalMinus = (Button) rootView.findViewById(R.id.redTotalMinus);
        Button redTotalPlus = (Button) rootView.findViewById(R.id.redTotalPlus);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.blueTotalMinus:
                        GameScreen.this.deleteScoreBlue(view);
                        break;
                    case R.id.blueTotalPlus:
                        GameScreen.this.addScoreBlue(view);
                        break;
                    case R.id.redTotalMinus:
                        GameScreen.this.deleteScoreRed(view);
                        break;
                    case R.id.redTotalPlus:
                        GameScreen.this.addScoreRed(view);
                        break;
                }
                kickerDataManager.insertOrUpdateGame(game);
            }
        };

        blueTotalMinus.setOnClickListener(clickListener);
        blueTotalPlus.setOnClickListener(clickListener);
        redTotalMinus.setOnClickListener(clickListener);
        redTotalPlus.setOnClickListener(clickListener);
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

        AdvancedTextView scoreTotalRed = (AdvancedTextView) rootView.findViewById(R.id.redTotalProgress);
        AdvancedTextView scoreTotalBlue = (AdvancedTextView) rootView.findViewById(R.id.blueTotalProgress);

        scoreTotalRed.setValue(game.getScoreRed());
        scoreTotalBlue.setValue(game.getScoreBlue());
    }
	
}
