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
public class AddGameScreen extends Fragment {

	public AddGameScreen() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.new_game, container, false);
	}

}
