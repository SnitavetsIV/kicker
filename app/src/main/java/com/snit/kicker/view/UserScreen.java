package com.snit.kicker.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        final ListView listView = (ListView) rootView.findViewById(R.id.listUser);

        List<User> users = kickerDataManager.getAllUsers();
        final UserAdapter userAdapter = new UserAdapter(getContext(), R.layout.user_list_item, users, kickerDataManager);
        listView.setAdapter(userAdapter);

        View footer = inflater.inflate(R.layout.user_footer, container, false);

        Button btnForward = (Button)footer.findViewById(R.id.newUser);

        btnForward.setEnabled(true);

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("New User");
                alert.setMessage("Please enter name");

                final EditText input = new EditText(getContext());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String s = String.valueOf(input.getText());
                        if (s != null && s.length() != 0) {
                            User user = new User();
                            user.setName(s);
                            kickerDataManager.insertOrUpdateUser(user);
                            userAdapter.add(user);
                            userAdapter.notifyDataSetChanged();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", null);

                alert.show();
            }
        });
        btnForward.setEnabled(true);
        listView.addFooterView(footer);


        return rootView;
    }

}
