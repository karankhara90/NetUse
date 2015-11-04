package khara.karan.netuse;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NearbyPlaces extends ListActivity {
    protected Button mBtnNewPlace, mBtnBackPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);

        mBtnBackPlaces = (Button) findViewById(R.id.btnBackPlaces);
        mBtnBackPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currUser = ParseUser.getCurrentUser();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
                query.whereEqualTo("userId", currUser);
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        String univName = parseObject.get("newUnivName").toString();
                        if(univName.equals("blank")) {
                            Intent intent3 = new Intent(NearbyPlaces.this, FutureStudent.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                            startActivity(intent3);
                        } else if(! univName.equals("blank")){
                            Intent intent3 = new Intent(NearbyPlaces.this, MainActivity.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                            startActivity(intent3);
                        }
                    }
                });
            }
        });

        mBtnNewPlace = (Button) findViewById(R.id.btnAddPlace);
        mBtnNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(NearbyPlaces.this, AddPlace.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlaceNearby");
        //query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.orderByAscending("createdAt");
        // we limit by 1000. because returning a large no of users will take too long or will crash our app
        //query.setLimit(1000);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> obs, ParseException e) {
                setProgressBarIndeterminateVisibility(false);

                if (e == null) {
                    //  String[] usernames = new String[obs.size()];
                    ArrayList<String> placesList = new ArrayList<String>();

                    for (ParseObject a : obs) {
                        //usernames[i] = a.get("fullName").toString();
                        placesList.add(a.get("Name").toString());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NearbyPlaces.this,
                                android.R.layout.simple_list_item_1, placesList);
                        setListAdapter(adapter);
                    }
                } else {
                    //Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(NearbyPlaces.this);
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
