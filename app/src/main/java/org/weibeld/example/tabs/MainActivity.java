package org.weibeld.example.tabs;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;

import org.weibeld.example.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    // Titles of the individual pages (displayed in tabs)
    private final String[] PAGE_TITLES = new String[] {
            "Good",
            "Wondering",
            "Bad",
            "Action Items"
    };

    private final int[] PAGE_ICONS = new int[] {
            R.drawable.check_mark_icon,
            R.drawable.question_mark,
            R.drawable.close_black,
            R.drawable.clipboard
    };

    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[] {
            new Page1Fragment(),
            new Page2Fragment(),
            new Page3Fragment(),
            new Page4Fragment()
    };

    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the Toolbar as the activity's app bar (instead of the default ActionBar)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Connect the ViewPager to our custom PagerAdapter. The PagerAdapter supplies the pages
        // (fragments) to the ViewPager, which the ViewPager needs to display.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Context context = this;

        FloatingActionButton addFab = (FloatingActionButton) findViewById(R.id.add_item_fab);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog().show();
            }
        });
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater= getLayoutInflater();
        builder.setTitle("Add Retro Item")
                .setView(inflater.inflate(R.layout.add_item_dialog,null));
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                EditText name = (EditText) builder.findViewById(R.id.postName);
//                EditText message = (EditText) findViewById(R.id.postMessage);
//                addPost(name.getText().toString(), message.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }

    /* PagerAdapter for supplying the ViewPager with the pages (fragments) to display. */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }
    }

        private void deletePost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Posts")
                .child("MeetingIDGoesHere")
                .child("-L394xC68ls2uBbHp8W0")
                .removeValue();
    }

    private void getAll() {
        final List<PostModel> mPosts = new ArrayList<>();
        if(mPosts.size() > 0){
            mPosts.clear();
//            mAdapter.clear();
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
                        message.setMessage(snapshot.getValue(PostModel.class).getMessage());
                        message.setTimestamp(snapshot.getValue(PostModel.class).getTimestamp());
                        //post.setPost_id(postId);
                        message.setChecked(snapshot.getValue(PostModel.class).getChecked());
                        message.setType(snapshot.getValue(PostModel.class).getType());
                        mPosts.add(message);


                    } catch (NullPointerException e) {
                        Log.e("Error", "onDataChange: NullPointerException: " + e.getMessage());
                    }

                }
                Log.i("Test", "onDataChange: NullPointerException");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void addPost(String name, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String postId = reference
                .child("Posts")
                .child("MeetingIDGoesHere")
                .push().getKey();

        PostModel post = new PostModel();
        post.setName(name);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        post.setTimestamp(ts);
        post.setMessage(message);
        post.setPost_id(postId);
        post.setChecked("false");
        post.setType("question");
        reference
                .child("Posts")
                .child("MeetingIDGoesHere")
                .child(postId)
                .setValue(post);
    }
}
