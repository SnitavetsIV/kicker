package com.snit.kicker.view;

import android.support.v4.app.Fragment;

import com.snit.kicker.db.KickerDataManager;

/**
 * @author Ilya Snitavets
 */
public class UserScreen extends Fragment {

    private KickerDataManager kickerDataManager;

    public void setKickerDataManager(KickerDataManager kickerDataManager) {
        this.kickerDataManager = kickerDataManager;
    }

    public UserScreen(){
    }



}
