package com.snit.kicker.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;
import com.snit.kicker.entity.Game;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class StatScreen extends Fragment {

	private KickerDataManager kickerDataManager;

	public StatScreen() {
	}

	public void setKickerDataManager(KickerDataManager kickerDataManager) {
		this.kickerDataManager = kickerDataManager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (kickerDataManager == null) {
			kickerDataManager = new KickerDataManager(getContext());
		}

		View rootView = inflater.inflate(R.layout.stat, container, false);

		List<Game> games = kickerDataManager.getGames();

		ViewGroup viewGroup = (ViewGroup) rootView.findViewById(R.id.listStat);

		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

		return rootView;
	}

}
