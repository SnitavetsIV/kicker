package com.snit.kicker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snit.kicker.R;

/**
 * @author Ilya Snitavets
 */
public class GameScreen extends Fragment {

    public GameScreen() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_game, container, false);
    }
	
}
