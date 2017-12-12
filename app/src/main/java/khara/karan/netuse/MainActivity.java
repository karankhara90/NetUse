package khara.karan.netuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.FileOutputStream;

//import android.support.v4.view.ViewPager;

//import android.support.v7.app.ActionBar;

public class MainActivity extends AppCompatActivity //ActionBarActivity
{

    protected TextView mFullName;
    protected Button mBtnNames;

    protected Context context;

    String userId;

    private ListView mDrawerListView;
    private ArrayAdapter<String> mAdapter;

    private String file = "mydata";
  //  String FullName;

//    ArrayAdapter<String> mdrawerAdapter;
//    ArrayAdapter<String> mStudAdapter;
//    ListView mDrawerList;
//    ListView mListStudents;


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
   // private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link //#restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        mDrawerListView = (ListView) findViewById(R.id.navListDrawer);
        addDrawerItems();
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "its: " + position, Toast.LENGTH_SHORT).show();

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


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("Inbox"));
//        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
//        tabLayout.addTab(tabLayout.newTab().setText("Friends"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        final PagerAdapter adapter = new PagerAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
//        {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab)
//            {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//.
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });





//        mNavigationDrawerFragment = (NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
//        mTitle = getTitle();
//
//        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));
    }




//    public void restoreActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setTitle(mTitle);
//    }

    private void navigateToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
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

        switch(itemId)
        {

            case R.id.action_logout:
                ParseUser.logOut();
                navigateToLogin();
                break;

//            case R.id.action_users:
//                Intent intent = new Intent(this, ShowUsersActivity.class);
//                startActivity(intent);
//                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void addDrawerItems() {

        ParseUser parseUser = ParseUser.getCurrentUser();
//            parseUser.getObjectId();
//            ParseObject parseObject = parseUser.getParseObject(parseUser.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        query.whereEqualTo("userId", parseUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                String new_univ_name = "";
                new_univ_name = parseObject.getString("newUnivName");

                String[] osArray = {"Your Profile", "Show Universities", "Show all Users", "Evaluation Metrics", new_univ_name};
                mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, osArray);
                mDrawerListView.setAdapter(mAdapter);
                mDrawerListView.setSelector((android.R.color.holo_blue_dark));


                try {
                    FileOutputStream fOut = openFileOutput(file,MODE_WORLD_READABLE);
                    fOut.write(new_univ_name.getBytes());
                    fOut.close();
                    Toast.makeText(getBaseContext(),"file saved", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


}
