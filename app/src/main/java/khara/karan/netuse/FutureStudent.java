package khara.karan.netuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class FutureStudent extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    protected Button mBtnSuggestUniv;
    protected Button mBtnSuggestNearby2;
    protected Button mBtnUpdate;
    protected TextView mFullName;
    protected Context context;
    String score,percent, undergradUniv, fullname;
    String objId;
    int i;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    public static final String TAG=FutureStudent.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_student);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        ParseUser currentUser2 = ParseUser.getCurrentUser();
        //Toast.makeText(this, "userID :" + userId, Toast.LENGTH_LONG).show();

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserInfo");

        query2.whereEqualTo("userId", currentUser2);
        query2.getFirstInBackground(new GetCallback<ParseObject>() {

            public void done(ParseObject object2, ParseException e) {
                //if (e == null) {
                try{
                    try{
                       fullname = object2.get("fullName").toString();
                        score = object2.get("greScore").toString();
                        percent = object2.get("undergradPercent").toString();
                    }catch (Exception exc1){
                        Log.e("TAG","exc1 exception:------------ "+exc1);
                    }

                    try{
                        undergradUniv = object2.get("undergradUniv").toString();
                        //Log.e("TAG","undergrad univ:::::::"+undergradUniv);
                    }catch (Exception exc2){
                        Log.e("TAG","exc2 exception:------------ "+exc2);
                    }
                    try{
                        //set text box with full name
                        mFullName = (TextView) findViewById(R.id.name2);
                        mFullName.setText(fullname);
                    }catch (Exception exc3){
                        Log.e("TAG","exc3 exception:------------ "+exc3);
                    }

                }catch (Exception exc){
                    Log.e("TAG","exc exception:------------ "+exc);
                }

               /* } else {
                    // error
                    AlertDialog.Builder builder = new AlertDialog.Builder(FutureStudent.this);

                    builder.setMessage(e.getMessage());
                    builder.setTitle("Error hai ethe ji");
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } */
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

        mBtnSuggestUniv = (Button) findViewById(R.id.btnSuggestUniv2);
        mBtnSuggestUniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(FutureStudent.this, SuggestUnivActivity.class);
                //Log.e("TAG","SCORE---- "+score+" ,,, PERCENT ---- "+percent);


             /*   Bundle bundle = new Bundle();
                bundle.putString("score", score);
                bundle.putString("percent", percent);
                bundle.putString("fullname",fullname);
                bundle.putString("undergradUniv",undergradUniv);

                intent3.putExtras(bundle); */
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent3);

            }
        });

        mBtnSuggestNearby2 = (Button) findViewById(R.id.btnSuggestNearby2);
        mBtnSuggestNearby2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(FutureStudent.this, NearbyPlaces.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent3);

            }
        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

        @Override
        public void onNavigationDrawerItemSelected(int position) {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle="Menu";
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            //case 3:
            //mTitle = getString(R.string.title_section3);
            //break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        switch(itemId)
        {

            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogin();
                break;

            case R.id.action_users:
                Intent intent = new Intent(this, ShowUsersActivity.class);
                startActivity(intent);
                break;

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

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FutureStudent) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}