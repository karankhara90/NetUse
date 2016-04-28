package khara.karan.netuse;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ShowNamesActivity extends ListActivity {
    public static final String TAG=ShowNamesActivity.class.getSimpleName();
    protected Button mBtnBackNames;
    //protected List<ParseObject> mObjects;
    //protected ParseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_names);

        mBtnBackNames = (Button) findViewById(R.id.btnBackNames);
        mBtnBackNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ShowNamesActivity.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent2);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        //query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.orderByAscending("createdAt");
        // we limit by 1000. because returning a large no of users will take too long or will crash our app
        //query.setLimit(1000);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> obs, ParseException e) {
                setProgressBarIndeterminateVisibility(false);
                Log.e("myapp", "obs: " + obs);
                // Log.e("myapp","e: "+e);
                if (e == null) {
                    //  String[] usernames = new String[obs.size()];
                    ArrayList<String> usernames = new ArrayList<String>();

                    for (ParseObject a : obs) {
                        //usernames[i] = a.get("fullName").toString();
                        usernames.add(a.get("fullName").toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowNamesActivity.this, android.R.layout.simple_list_item_1, usernames);
                        setListAdapter(adapter);

                    }
                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowNamesActivity.this);
                    builder.setMessage(e.getMessage());
                    builder.setTitle(getString(R.string.error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
