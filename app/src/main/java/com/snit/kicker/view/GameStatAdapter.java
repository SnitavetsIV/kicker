package com.snit.kicker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.snit.kicker.entity.Game;

import java.util.ArrayList;

/**
 * @author Ilya Snitavets
 */
public class GameStatAdapter extends BaseAdapter {

    private ArrayList<Game> games;
    private LayoutInflater layoutInflater;


    public GameStatAdapter(Context context, ArrayList<Game> listData) {
        this.games = listData;
        layoutInflater = LayoutInflater.from(context);
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
        return null;
    }
}
