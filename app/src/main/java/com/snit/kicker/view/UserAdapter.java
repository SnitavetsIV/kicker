package com.snit.kicker.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.snit.kicker.R;
import com.snit.kicker.entity.User;

import java.util.List;

/**
 * @author Ilya Snitavets
 */
public class UserAdapter extends ArrayAdapter<User> {

    private List<User> items;
    private int layoutResourceId;
    private Context context;

    public UserAdapter(Context context, int layoutResourceId, List<User> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(layoutResourceId, parent, false);

        UserHolder holder = new UserHolder();
        holder.user = items.get(position);
        holder.removePaymentButton = (ImageButton)row.findViewById(R.id.userRemove);
        holder.removePaymentButton.setTag(holder.user);

        holder.name = (TextView)row.findViewById(R.id.userName);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(UserHolder holder) {
        holder.name.setText(holder.user.getName());
    }

    public static class UserHolder {
        User user;
        TextView name;
        ImageButton removePaymentButton;
    }
}
