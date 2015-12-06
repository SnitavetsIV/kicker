package com.snit.kicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.snit.kicker.R;

/**
 * @author Ilya Snitavets
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    public void newGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void stat(View view) {
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }
}
