package org.weibeld.example.tabs;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.weibeld.example.R;

import java.util.ArrayList;
import java.util.List;

/* Fragment used as page 4 */
public class Page4Fragment extends Fragment {
    ArrayList<PostModel> mPosts;
    MessageAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page4, container, false);
        getAll();
        mAdapter = new MessageAdapter(this.getContext(), mPosts);
        // Attach the adapter to a ListView
        ListView listView = (ListView) rootView.findViewById(R.id.action_list);
        listView.setAdapter(mAdapter);
        return rootView;
    }

    private void getAll() {
        mPosts = new ArrayList<>();
        if(mPosts.size() > 0){
            mPosts.clear();
            mAdapter.clear();
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("Posts")
                .child("MeetingIDGoesHere");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()) {

                    DataSnapshot snapshot = singleSnapshot;

                    try {//need to catch null pointer here because the initial welcome message to the
                        //chatroom has no user id
                        PostModel message = new PostModel();
                        message.setName(snapshot.getValue(PostModel.class).getName());
                        message.setMessage(snapshot.getValue(PostModel.class).getMessage());
                        message.setTimestamp(snapshot.getValue(PostModel.class).getTimestamp());
                        //post.setPost_id(postId);
                        message.setChecked(snapshot.getValue(PostModel.class).getChecked());
                        message.setType(snapshot.getValue(PostModel.class).getType());
                        if (message.getType().equals("action")) {
                            mPosts.add(message);
                        }


                    } catch (NullPointerException e) {
                        Log.e("Error", "onDataChange: NullPointerException: " + e.getMessage());
                    }

                }
                Log.i("Test", "onDataChange: NullPointerException");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
