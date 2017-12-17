//package khara.karan.netuse;
//
//import android.app.AlertDialog;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//
//import java.util.List;
//
//public class ShowUsersActivity extends AppCompatActivity {
//
//    public static final String TAG=ShowUsersActivity.class.getSimpleName();
//    //protected ParseRelation<ParseUser> mFriendsRelation;
//    protected List<ParseUser> mUsers;
//    protected ParseUser mCurrentUser;
//    protected Button mBtnBackUser;
//    ListView listAllUsers;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_users);
//        listAllUsers=(ListView)findViewById(R.id.listShowAllUsers);
//
//        //Show the up button in the action bar
//       // setupActionBar();
//
//
//        //getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//    }
//    //private void setupActionBar() {
//      //  getActionBar().setDisplayHomeAsUpEnabled(true);
//    //}
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        mCurrentUser = ParseUser.getCurrentUser();
//        //mFriendsRelation= mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
//        setProgressBarIndeterminateVisibility(true);
//
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        //query.orderByAscending(ParseConstants.KEY_USERNAME);
//
//        // we limit by 1000. because returning a large no of users will take too long or will crash our app
//        query.setLimit(1000);
//        query.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> users, ParseException e) {
//                setProgressBarIndeterminateVisibility(false);
//                if (e == null) {
//                    mUsers = users;
//                    String[] usernames = new String[mUsers.size()];
//                    int i = 0;
//                    for (ParseUser user : mUsers)
//                    {
//                        usernames[i] = user.get("username").toString();
//                        //if(! mCurrentUser.getUsername().equals(usernames[i]))
//                       // {
//                        i++;
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowUsersActivity.this,
//                                android.R.layout.simple_selectable_list_item, usernames);
//                                //android.R.layout.simple_selectable_list_item, usernames);
//                        listAllUsers.setAdapter(adapter);
//                    }
//                } else {
//                    System.out.println(" e is not null");
//                    Log.e(TAG, e.getMessage());
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowUsersActivity.this);
//
//                    builder.setMessage(e.getMessage());
//                    builder.setTitle(getString(R.string.error_title));
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//
//                mBtnBackUser = (Button) findViewById(R.id.btnBackUser);
//                mBtnBackUser.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        Intent intent = new Intent(ShowUsersActivity.this, FutureStudent.class);
////                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
////                        startActivity(intent);
//                          onBackPressed();
//                    }
//                });
//
//            }
//        });
//
//    }
//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//    }
//
//    /*private void addFriendCheckmarks()
//    {
//        mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> friends, ParseException e) {
//                if (e == null) {
//                    //list returned- look for a match
//                    for (int i = 0; i < mUsers.size(); i++) {
//                        ParseUser user = mUsers.get(i);
//
//                        for (ParseUser friend : friends) {
//                            if (friend.getObjectId().equals(user.getObjectId())) {
//                                getListView().setItemChecked(i, true);
//                            }
//                        }
//                    }
//                } else {
//                    Log.e(TAG, e.getMessage());
//                }
//            }
//        });
//    }  */
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}
