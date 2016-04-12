package khara.karan.netuse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;


public class FutureStudent extends AppCompatActivity {
    protected Button mBtnSuggestUniv;
    protected Button mBtnSuggestNearby2;
    protected Button mBtnUpdate;
    protected TextView mFullName;
    protected TextView  mTxtAvgRating;
    protected Context context;
    String mScore, mPercent, mUndergradUniv, mFullname;
    String mCurrUserUndergradUnivRating;
    float mTotalAvgRating;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ArrayAdapter<String> mAdapter;
    private RatingBar mRatingBar1;

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();


    int changeBar =0;
    int userChangeBar = 0;
    int i;
    int cc;
    float mRatingRec;

    Calculations calculations = new Calculations();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    public static final String TAG=FutureStudent.class.getSimpleName();

    ParseUser currentUser = ParseUser.getCurrentUser();
    private Button mBtnBackFuture;
    private ProgressDialog progressDialog;
    private String mActivityTitle;

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_student);
        //progress_bar();
        context = this;

        try {
            ParseAnalytics.trackAppOpenedInBackground(getIntent());
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser == null) {
                navigateToLogin();
            } else {
                Log.i(TAG, currentUser.getUsername());
                //System.out.println("Current User is: "+currentUser.getUsername());
            }
        }
        catch (Exception exc){
            Log.e("TAG","exc in parse starting: "+exc);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ---- navigation drawer ----- //
        mDrawerListView = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FutureStudent.this, "its: " + position, Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    Intent intent = new Intent(context, UserProfile.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(context, ShowUnivActivity.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(context, ShowUsersActivity.class);
                    startActivity(intent);
                }
                else if(position == 3){
                    Intent intent = new Intent(context, ShowRateDiffChart.class);
                    startActivity(intent);
                }

            }
        });

//        mRatingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
//        mTxtAvgRating = (TextView) findViewById(R.id.textAvgRating);

        // if rating value is changed and display the current rating value in the result (textview)
        // automatically



//        if(cc == 0){
            ParseUser currentUser2 = ParseUser.getCurrentUser();
            //Toast.makeText(this, "userID :" + userId, Toast.LENGTH_LONG).show();

            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserInfo");

            query2.whereEqualTo("userId", currentUser2);
            query2.getFirstInBackground(new GetCallback<ParseObject>() {

                public void done(ParseObject object2, ParseException e) {
                    //if (e == null) {
                    try {
                        try {
//                            if(changeBar == 0){
//                                object2.put("ratingToRec","3.0");
//                            }
//                            if( object2.get("ratingToRec") !=null){
//                                mRatingBar1.setRating(Float.valueOf( object2.getString("ratingToRec")));
//                            }

                            mFullname = object2.get("fullName").toString();
                            mScore = object2.get("greScore").toString();
                            mPercent = object2.get("undergradPercent").toString();
                            Log.e(TAG, mFullname + ",. "+ mScore+ ",. "+mPercent);
                        } catch (Exception exc1) {
                            Log.e("TAG", "exc1 exception:------------ " + exc1);
                        }

                        try {
                            mUndergradUniv = object2.get("undergradUniv").toString();
                            //Log.e("TAG","undergrad univ:::::::"+mUndergradUniv);
                        } catch (Exception exc2) {
                            Log.e("TAG", "exc2 exception:------------ " + exc2);
                        }
                        try {
                            //set text box with full name
                            mFullName = (TextView) findViewById(R.id.name2);
                            mFullName.setText(mFullname);
                        } catch (Exception exc3) {
                            Log.e("TAG", "exc3 exception:------------ " + exc3);
                        }

                        try {
                            ParseObject preunivname2 = object2.getParseObject("prevUnivName");
                            mCurrUserUndergradUnivRating = preunivname2.fetchIfNeeded().get("univRating").toString();
                        } catch (Exception ex2) {
                            Log.e(TAG, "ex2 exception--" + ex2);
                        }

                        object2.saveInBackground();
                    } catch (Exception exc) {
                        Log.e("TAG", "exc exception:------------ " + exc);
                    }
                    //multiSelectionSpinner = (MultiSelectionSpinner)findViewById(R.id.getSelected);
//                    ParseQuery<ParseObject> queryavgRating = ParseQuery.getQuery("ratingRecList");
//                    queryavgRating.getFirstInBackground(new GetCallback<ParseObject>() {
//                        @Override
//                        public void done(ParseObject parseObject, ParseException e) {
//
//                            float getAvgRating2 = Float.valueOf(parseObject.get("ratingRec").toString());
//                            mTxtAvgRating.setText(String.valueOf(getAvgRating2));
//
//
////                            mRatingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
////                                public void onRatingChanged(RatingBar ratingBar, final float rating,
////                                                            boolean fromUser) {
////                                    changeBar = 1;
////                                    userChangeBar = 1;
////                                    mRatingRec = rating;
////                                    //Log.e(TAG,"rating::"+mRatingRec);
////                                    ParseQuery<ParseObject> query11 = ParseQuery.getQuery("ratingRecList");
////                                    query11.getFirstInBackground(new GetCallback<ParseObject>() {
////                                        @Override
////                                        public void done(ParseObject parseObject, ParseException e) {
////                                            float getAvgRating = Float.valueOf(parseObject.get("ratingRec").toString());
////                                            //mTxtAvgRating.setText(String.valueOf(getAvgRating));
////                                            int getTotalRatings = Integer.parseInt(parseObject.get("totalRatings").toString());
////                                            //mTotalAvgRating = (getAvgRating + mRatingRec) / (getTotalRatings + 1);
////
////
////                                            mTotalAvgRating = ((getAvgRating * (float)getTotalRatings) + mRatingRec) / (getTotalRatings + 1);
////
////                                           // mTotalAvgRating = Math.round(mTotalAvgRating * 1000) / 1000;
////                                            Log.e(TAG,"avg rating"+getAvgRating);
////                                            Log.e(TAG,"total rating"+getTotalRatings);
////                                            Log.e(TAG,"rating given"+mRatingRec);
////                                            Log.e(TAG,"total avg"+mTotalAvgRating);
////
////
////                                            parseObject.put("ratingRec", mTotalAvgRating);
////
////                                            if(userChangeBar<=1){
////                                                parseObject.put("totalRatings", getTotalRatings + 1);
////                                                userChangeBar++;
////                                            }
////
////                                            mTxtAvgRating.setText(String.valueOf(mTotalAvgRating));
////                                            parseObject.saveInBackground();
////
////
////                                            ParseQuery<ParseObject> query12 = ParseQuery.getQuery("UserInfo");
////                                            query12.whereEqualTo("userId", currentUser);
////                                            query12.getFirstInBackground(new GetCallback<ParseObject>() {
////                                                @Override
////                                                public void done(ParseObject parseObject, ParseException e) {
////                                                    //if()
////                                                    parseObject.put("ratingToRec", mRatingRec);
////                                                    parseObject.saveInBackground();
////                                                }
////                                            });
////
////                                        }
////                                    });
////
////                                }
////                            });
//                        }
//                    });
                }
            });

        mBtnSuggestUniv = (Button) findViewById(R.id.btnSuggestUniv2);
        mBtnSuggestUniv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //start the progress dialog

               Intent intent3 = new Intent(FutureStudent.this, SuggestUnivActivity.class);
               Log.e("TAG", "SCORE=========== " + mScore + " , PERCENT ============ " + mPercent
                       + ",  Univ rating =============" + mCurrUserUndergradUnivRating);

               Bundle bundle = new Bundle();
               bundle.putString("mScore", mScore);
               bundle.putString("mPercent", mPercent);
               bundle.putString("mFullname", mFullname);
               bundle.putString("mUndergradUniv", mUndergradUniv);
               bundle.putString("currUserUnderUnivRating", mCurrUserUndergradUnivRating);

               intent3.putExtras(bundle);
               intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
               progress_bar();
               startActivity(intent3);
           }
        });

        mBtnUpdate = (Button) findViewById(R.id.btnAddUniv2);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(FutureStudent.this, UpdateProfile.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent2);
            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }
//    @Override
//    protected void onDestroy() {
//        if (progressDialog!=null) {
//            progressDialog.dismiss();
//            mBtnSuggestUniv.setEnabled(true);
//
//        }
//        super.onDestroy();
//    }

    @Override
    public void onBackPressed(){
        finish();
    }

    void progress_bar(){
        progressDialog = new ProgressDialog(FutureStudent.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Calculating Results...");
        progressDialog.setTitle("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread() {

            public void run() {

                try{
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        sleep(1000);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }

                    }
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }.start();

    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.incrementProgressBy(1);
        }
    };

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogin();
                break;

//            case R.id.action_users:
//                Intent intent = new Intent(this, ShowUsersActivity.class);
//                startActivity(intent);
//                break;

//            case R.id.action_profile:
//                Intent intent2 = new Intent(this, UserProfile.class);
//                startActivity(intent2);
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }

    private void addDrawerItems() {
//        String[] osArray = {"Your Profile","Show Universities", "Show all Users", "Evaluation Metrics"};
//        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
//        mDrawerListView.setAdapter(mAdapter);
//        mDrawerListView.setSelector((android.R.color.holo_blue_dark));

        mNavItems.add(new NavItem("Your Profile", "View your profile", R.drawable.ic_username));
        mNavItems.add(new NavItem("Show Universities", "List of all US universities", R.drawable.ic_offline));
        mNavItems.add(new NavItem("Show Users", "List of all users", R.drawable.ic_username));
        mNavItems.add(new NavItem("Evaluation Metrics", "Evaluation graph", R.drawable.ic_offline));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerListView.setAdapter(adapter);

    }

    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.drawer_item, null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( mNavItems.get(position).mTitle );
            subtitleView.setText( mNavItems.get(position).mSubtitle );
            iconView.setImageResource(mNavItems.get(position).mIcon);

            return view;
        }
    }
}

