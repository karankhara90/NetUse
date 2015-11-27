package khara.karan.netuse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ReccPlaces extends Activity {
    public static final String TAG=ReccPlaces.class.getSimpleName();
    protected Button mBtnLike;
    protected Button mBtnBackRecc;
    TextView textView;
    ListView listview1;
   // static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recc_places);
        textView = (TextView) findViewById(R.id.textView3);
        recMethod();
        mBtnLike = (Button) findViewById(R.id.btnLike);
        mBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if(count>0)
                // {
                //recMethod();
                //}

                Intent intent2 = new Intent(ReccPlaces.this, ReccPlaces.class);
                //  count = 1;
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent2);
            }
        });

        mBtnBackRecc = (Button) findViewById(R.id.btnBackProfile);
        mBtnBackRecc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ParseUser currUser = ParseUser.getCurrentUser();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
                query.whereEqualTo("userId", currUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        String univName = parseObject.get("newUnivName").toString();
                        if (univName.equals("blank")) {
                            Intent intent3 = new Intent(ReccPlaces.this, FutureStudent.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                            startActivity(intent3);
                        } else if (!univName.equals("blank")) {
                            Intent intent3 = new Intent(ReccPlaces.this, MainActivity.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                            startActivity(intent3);
                        }
                    }
                });
            }
        });

    }

    //@Override
    protected void recMethod() {
        //super.onResume();

        // get the intent from which this activity is called.
        Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        String itemSelected = intent.getStringExtra("selected-place");
        String categSelected = intent.getStringExtra("selected-category");
        textView.setText("You selected " + itemSelected);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceNearby");
        //query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.orderByAscending("createdAt");
        //String category = "Restaurant";
        Toast.makeText(this, "categ :" + categSelected, Toast.LENGTH_LONG).show();
        query.whereEqualTo("Category",categSelected);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e==null){
                    Log.e(TAG, "e nulll::::" + e);
                    listview1 = (ListView) findViewById(R.id.listRecc);
                    ArrayList<String> reccplaces = new ArrayList<String>();
                    for (ParseObject a : list) {
                        String getPlace = a.get("Name").toString();
                        Log.e(TAG,"JAGAH::: "+getPlace);
                        reccplaces.add(getPlace);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReccPlaces.this,
                                R.layout.list_view_row, R.id.listText, reccplaces);
                        listview1.setAdapter(adapter);

                    }
                }
                else{
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReccPlaces.this);
                    builder.setMessage(e.getMessage());
                    builder.setTitle(getString(R.string.error_title));
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }

}
