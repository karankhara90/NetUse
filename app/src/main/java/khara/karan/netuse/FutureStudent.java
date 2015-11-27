package khara.karan.netuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FutureStudent extends ActionBarActivity {
    protected Button mBtnSuggestUniv;
    protected Button mBtnSuggestNearby2;
    protected Button mBtnUpdate;
    protected TextView mFullName;
    protected Context context;
    String mScore, mPercent, mUndergradUniv, mFullname;
    String mCurrUserUndergradUnivRating;

    private ListView mDrawerListView;
    private ArrayAdapter<String> mAdapter;

    int i;

    String mUnivTypePrefer;
    Spinner mSpUnivTypes, spinner2;
    List<String> mListUnivType;
    ArrayList<String> mListUnivStates;
    MultiSelectionSpinner multiSelectionSpinner;
    protected Button mBtnChooseStates;

    List<String> mListUnivStatesPrefer;

    Calculations calculations = new Calculations();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    public static final String TAG=FutureStudent.class.getSimpleName();

    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_student);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        mDrawerListView = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FutureStudent.this, "its: "+position, Toast.LENGTH_SHORT).show();

                if(position == 0){
                    Intent intent = new Intent(context, UserProfile.class);
                    startActivity(intent);
                }
                else if(position == 1){
                    Intent intent = new Intent(context, ShowUnivActivity.class);
                    startActivity(intent);
                }
                else if(position == 2){
                    Intent intent = new Intent(context, ShowUsersActivity.class);
                    startActivity(intent);
                }

            }
        });

//        mBtnChooseStates = (Button) findViewById(R.id.btnChooseStates);

        ParseUser currentUser2 = ParseUser.getCurrentUser();
        //Toast.makeText(this, "userID :" + userId, Toast.LENGTH_LONG).show();

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("UserInfo");

        query2.whereEqualTo("userId", currentUser2);
        query2.getFirstInBackground(new GetCallback<ParseObject>() {

            public void done(final ParseObject object2, ParseException e) {
                //if (e == null) {
                try {
                    try {
                        mFullname = object2.get("fullName").toString();
                        mScore = object2.get("greScore").toString();
                        mPercent = object2.get("undergradPercent").toString();
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

                } catch (Exception exc) {
                    Log.e("TAG", "exc exception:------------ " + exc);
                }
                //multiSelectionSpinner = (MultiSelectionSpinner)findViewById(R.id.getSelected);
                multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.spChooseStates);

                mSpUnivTypes = (Spinner) findViewById(R.id.spUnivType);
                mListUnivType = new ArrayList<String>();
                mListUnivStates = new ArrayList<String>();

                /*  *********************************************************** */
                mListUnivType.add("-- Select Type you prefer --");
                mListUnivType.add("public");
                mListUnivType.add("private");

                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mListUnivType);
                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter3.notifyDataSetChanged();
                mSpUnivTypes.setAdapter(dataAdapter3);


                /*  *********************************************************** */


                mListUnivStates.add("-- Select States you prefer --");
                mListUnivStates.add("California");
                mListUnivStates.add("Ohio");
                mListUnivStates.add("North Carolina");
                mListUnivStates.add("New Mexico");
                mListUnivStates.add("New York");
                mListUnivStates.add("Florida");
                mListUnivStates.add("Texas");
                mListUnivStates.add("Washington");
                mListUnivStates.add("Oregon");
                mListUnivStates.add("Utah");
                mListUnivStates.add("Michigan");
                mListUnivStates.add("Iowa");
                mListUnivStates.add("Pennsylvania");
                mListUnivStates.add("Illinois");
                mListUnivStates.add("Massachusetts");
                mListUnivStates.add("Connecticut");
                mListUnivStates.add("New Jersey");

                multiSelectionSpinner.setItems(mListUnivStates);
//                multiSelectionSpinner.setSelection(new int[]{2, 3});
                /*  *********************************************************** */


                mBtnSuggestUniv = (Button) findViewById(R.id.btnSuggestUniv2);
                mBtnSuggestUniv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUnivTypePrefer = mSpUnivTypes.getSelectedItem().toString();    // get selected Univ type
                        mListUnivStatesPrefer = multiSelectionSpinner.getSelectedStrings();

                        Log.e(TAG, "univ type selected::-::" + mUnivTypePrefer);
                        Log.e(TAG, "univ states selected::=::" + mListUnivStatesPrefer.toString());
                        object2.put("UnivStatesPrefer", mListUnivStatesPrefer);
                        object2.put("UnivTypePrefer", mUnivTypePrefer);
                        object2.saveInBackground();   // important to save after we put() into database in order to update database

                        Intent intent3 = new Intent(FutureStudent.this, SuggestUnivActivity.class);
                        Log.e("TAG", "SCORE=========== " + mScore + " , PERCENT ============ " + mPercent
                                + ",  Univ rating =============" + mCurrUserUndergradUnivRating);

                        Bundle bundle = new Bundle();
                        bundle.putString("mListUnivStatesPrefer", mListUnivStatesPrefer.toString());
                        bundle.putString("mUnivTypePrefer", mUnivTypePrefer);
                        bundle.putString("mScore", mScore);
                        bundle.putString("mPercent", mPercent);
                        bundle.putString("mFullname", mFullname);
                        bundle.putString("mUndergradUniv", mUndergradUniv);
                        bundle.putString("currUserUnderUnivRating", mCurrUserUndergradUnivRating);

                        intent3.putExtras(bundle);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                        startActivity(intent3);

                    }
                });
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


//        mBtnSuggestNearby2 = (Button) findViewById(R.id.btnSuggestNearby2);
//        mBtnSuggestNearby2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent3 = new Intent(FutureStudent.this, NearbyPlaces.class);
//                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                startActivity(intent3);
//            }
//        });

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

//        mNavigationDrawerFragment = (NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
//        mTitle = getTitle();
//
//        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

//        @Override
//        public void onNavigationDrawerItemSelected(int position) {
//            // update the main content by replacing fragments
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                    .commit();
//        }
//
//    public void onSectionAttached(int number) {
//        switch (number) {
//            case 1:
//                mTitle="Menu";
//                break;
//            case 2:
//                mTitle = getString(R.string.title_section1);
//                break;
//            case 3:
//                mTitle = getString(R.string.title_section2);
//                break;
//            //case 3:
//            //mTitle = getString(R.string.title_section3);
//            //break;
//        }
//    }

//    public void restoreActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
//    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if (!mNavigationDrawerFragment.isDrawerOpen()) {
//            // Only show items in the action bar relevant to this screen
//            // if the drawer is not showing. Otherwise, let the drawer
//            // decide what to show in the action bar.
//            getMenuInflater().inflate(R.menu.main, menu);
//            restoreActionBar();
//            return true;
//        }
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        switch (itemId) {

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

//        @Override
//        public void onAttach(Activity activity) {
//            super.onAttach(activity);
//            ((FutureStudent) activity).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
//        }
    }

    private void addDrawerItems() {
        String[] osArray = {"Your Profile","Show Universities", "Show all Users", "Nearby Places"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerListView.setAdapter(mAdapter);
        mDrawerListView.setSelector((android.R.color.holo_blue_dark));
    }


}

