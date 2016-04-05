package khara.karan.netuse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    TextView text;
    protected TextView mFullName;
    protected Button mBtnUpdate2;
    protected Button mBtnNames;
    protected Button mBtnSuggestUniv2;
    protected Button mBtnSuggestNearby;
    protected String mUserid;
    protected Context context;

    String userId;
  //  String FullName;

    ArrayAdapter<String> mdrawerAdapter;
    ArrayAdapter<String> mStudAdapter;
    ListView mDrawerList;
    ListView mListStudents;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        mListStudents = (ListView)findViewById(R.id.lvStudents);
        mDrawerList = (ListView)findViewById(R.id.navListMain);
        addDrawerListMain();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    String uni_name= mDrawerList.getItemAtPosition(0).toString();
                    //Log.e("TAG","==============================="+name);
                    //Intent intentMajor = new Intent(this, );
                    ParseUser user = ParseUser.getCurrentUser();
                    ParseQuery<ParseObject> qu = ParseQuery.getQuery("UserInfo");
                    qu.whereEqualTo("newUnivName",uni_name);
                    qu.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            ArrayList<String> stu_list = new ArrayList<String>();
                            for(ParseObject ob : list){
                                String stu_name = ob.getString("fullName");

                                stu_list.add(stu_name);
                                mStudAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_selectable_list_item,stu_list);
                                mListStudents.setAdapter(mStudAdapter);
                                mListStudents.setSelector(android.R.color.background_dark);
                                mListStudents.setBackgroundColor(0xff444444);
                            }
                        }
                    });

                }
                else if(position == 1){

                }

            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        //Toast.makeText(this, "userID :" + userId, Toast.LENGTH_LONG).show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");

        query.whereEqualTo("userId",currentUser);
         query.getFirstInBackground(new GetCallback<ParseObject>() {

             public void done(ParseObject object, ParseException e) {
                 if (e == null) {
                     String FullName = object.get("fullName").toString();
                     mFullName = (TextView) findViewById(R.id.name);
                     mFullName.setText(FullName);

                 } else {
                     // error
                     AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                     builder.setMessage(e.getMessage());
                     builder.setTitle("Error hai ethe ji");
                     builder.setPositiveButton(android.R.string.ok, null);
                     AlertDialog dialog = builder.create();
                     dialog.show();
                 }
             }
         });



        mBtnNames = (Button) findViewById(R.id.btnNames);
        mBtnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, ShowNamesActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent2);
            }
        });


//        mBtnSuggestNearby = (Button) findViewById(R.id.btnSuggestNearby);
//        mBtnSuggestNearby.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent3 = new Intent(MainActivity.this, NearbyPlaces.class);
//                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                startActivity(intent3);
//
//            }
//        });


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
     /*   FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit(); */
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
  /*  public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
     /*   private static final String ARG_SECTION_NUMBER = "section_number"; */

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
  /*      public static PlaceholderFragment newInstance(int sectionNumber) {
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
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    } */

    public void addDrawerListMain()
    {
        ParseUser parseUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        query.whereEqualTo("userId",parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                String stu_univ = parseObject.getString("newUnivName");
                // String stu_univ = parseObject.getObjectId();
                //String stu_univ2 = parseObject.getString("newUnivName");
                //Log.e("TAG","stu_uni...================ "+stu_univ+ " ====== "+stu_univ2);


                String[] drawerArr = {stu_univ};
                mdrawerAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_selectable_list_item,drawerArr);
                mDrawerList.setAdapter(mdrawerAdapter);
                mDrawerList.setSelector(android.R.color.holo_green_dark);
                mDrawerList.setBackgroundColor(0xff444444);
            }
        });




    }

}
