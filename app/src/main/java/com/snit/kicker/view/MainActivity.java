package com.snit.kicker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;

/**
 * @author Ilya Snitavets
 */
public class MainActivity extends AppCompatActivity {

    private final KickerDataManager kickerDataManager = new KickerDataManager(this);

    private String[] kickerTitles;
    private DrawerLayout drawerLayout;
    private ListView listView;

    private StatScreen statScreen;
    private GameScreen gameScreen;
    private AddGameScreen addGameScreen;
    private UserScreen userScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        kickerTitles = getResources().getStringArray(R.array.screen_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, kickerTitles));

        listView.setOnItemClickListener(new DrawerItemClickListener());

        statScreen = new StatScreen();
        statScreen.setKickerDataManager(kickerDataManager);

        gameScreen = new GameScreen();
        gameScreen.setKickerDataManager(kickerDataManager);

        userScreen = new UserScreen();
        userScreen.setKickerDataManager(kickerDataManager);

        showStartScreen();
    }

    private void showStartScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, gameScreen).commit();

        listView.setItemChecked(0, true);
        setTitle(kickerTitles[0]);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = gameScreen;
                break;
            case 1:
                f = addGameScreen;
                break;
            case 2:
                f = statScreen;
                break;
            case 3:
                f = userScreen;
        }
        if (f != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, f).commit();

            listView.setItemChecked(position, true);
            setTitle(kickerTitles[position]);
            drawerLayout.closeDrawer(listView);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(title);
        }

    }

}
