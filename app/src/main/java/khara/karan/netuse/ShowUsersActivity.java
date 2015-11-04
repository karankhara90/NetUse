package khara.karan.netuse;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ShowUsersActivity extends ListActivity {

    public static final String TAG=ShowUsersActivity.class.getSimpleName();
    //protected ParseRelation<ParseUser> mFriendsRelation;
    protected List<ParseUser> mUsers;
    protected ParseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        //Show the up button in the action bar
       // setupActionBar();


        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    //private void setupActionBar() {
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
    //}


    @Override
    protected void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
       // Toast.makeText(ShowUsersActivity.this, "mCurrent: "+mCurrentUser.getUsername(), Toast.LENGTH_LONG).show();
        //mFriendsRelation= mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
        setProgressBarIndeterminateVisibility(true);

        //ParseQuery<ParseUser> query = ParseQuery.getQuery("User"); // means query is going to return list of ParseUser objects.
        //query.orderByAscending(ParseConstants.KEY_USERNAME);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        //query.orderByAscending(ParseConstants.KEY_USERNAME);

        // we limit by 1000. because returning a large no of users will take too long or will crash our app
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // success
                    mUsers = users;
                    String[] usernames = new String[mUsers.size()];
                    //Toast.makeText(ShowUsersActivity.this, " (2..) "+mUsers.size(), Toast.LENGTH_SHORT).show();
                    int i = 0;
                    //if(user.getuserna)
                    for (ParseUser user : mUsers)
                    {
                        //Toast.makeText(ShowUsersActivity.this, " (2..) ", Toast.LENGTH_SHORT).show();
                        usernames[i] = user.get("username").toString();
                        //if(! mCurrentUser.getUsername().equals(usernames[i]))
                       // {
                            i++;
                            //create array adapter
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowUsersActivity.this,
                                    android.R.layout.simple_list_item_checked, usernames);
                                    //android.R.layout.simple_selectable_list_item, usernames);
                            setListAdapter(adapter);

                            //addFriendCheckmarks();
                       // }
                        //System.out.println("user[" + i + "]: " + usernames[i]);
                    }
                } else {
                    System.out.println(" e is not null");
                    Log.e(TAG, e.getMessage());

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowUsersActivity.this);

                    builder.setMessage(e.getMessage());
                    builder.setTitle(getString(R.string.error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }

    /*private void addFriendCheckmarks()
    {
        mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {
                if (e == null) {
                    //list returned- look for a match
                    for (int i = 0; i < mUsers.size(); i++) {
                        ParseUser user = mUsers.get(i);

                        for (ParseUser friend : friends) {
                            if (friend.getObjectId().equals(user.getObjectId())) {
                                getListView().setItemChecked(i, true);
                            }
                        }
                    }
                } else {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }  */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
