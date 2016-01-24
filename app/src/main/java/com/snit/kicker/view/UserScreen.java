package com.snit.kicker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.snit.kicker.R;
import com.snit.kicker.db.KickerDataManager;
import com.snit.kicker.entity.User;

import java.util.List;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (kickerDataManager == null) {
            kickerDataManager = new KickerDataManager(getContext());
        }

        View rootView = inflater.inflate(R.layout.user, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listUser);

        List<User> users = kickerDataManager.getAllUsers();
        UserAdapter userAdapter = new UserAdapter(getContext(), R.layout.user_list_item, users);

        listView.setAdapter(userAdapter);

        return rootView;
    }


}
