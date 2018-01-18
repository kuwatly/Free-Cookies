package org.weibeld.example.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.weibeld.example.R;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<PostModel> {
    public MessageAdapter(Context context, ArrayList<PostModel> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PostModel post = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
        }
        // Lookup view for data population
        TextView tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvMessage.setText(post.getMessage());
        tvName.setText(post.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}