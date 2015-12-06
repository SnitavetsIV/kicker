package com.snit.kicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.snit.kicker.R;
import com.snit.kicker.db.DataProvider;
import com.snit.kicker.entity.Game;
import com.snit.kicker.entity.User;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_game);

        DataProvider dataProvider = new DataProvider(this);

        List<User> users = dataProvider.getUsers();

        Spinner blueAttackSpin = (Spinner) findViewById(R.id.blueAttackSpin);
        Spinner blueDefenceSpin = (Spinner) findViewById(R.id.blueDefenceSpin);
        Spinner redAttackSpin = (Spinner) findViewById(R.id.redAttackSpin);
        Spinner redDefenceSpin = (Spinner) findViewById(R.id.redDefenceSpin);

        ArrayAdapter<User> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);

        blueAttackSpin.setAdapter(userAdapter);
        blueDefenceSpin.setAdapter(userAdapter);
        redAttackSpin.setAdapter(userAdapter);
        redDefenceSpin.setAdapter(userAdapter);
    }

    public void startGame(View view) {
        Spinner blueAttackSpin = (Spinner) view.findViewById(R.id.blueAttackSpin);
        Spinner blueDefenceSpin = (Spinner) view.findViewById(R.id.blueDefenceSpin);
        Spinner redAttackSpin = (Spinner) view.findViewById(R.id.redAttackSpin);
        Spinner redDefenceSpin = (Spinner) view.findViewById(R.id.redDefenceSpin);

        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

}
