package khara.karan.netuse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfile extends ActionBarActivity {

    protected Button mDone;
    protected Context context;
    Spinner spinner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        mDone = (Button) findViewById(R.id.myDone);
        spinner3 = (Spinner) findViewById(R.id.spNewUniv);

        List<String> list3 = new ArrayList<String>();
        list3.add("--Choose New University--");
        list3.add("MIT");
        list3.add("Stanford Univ");
        list3.add("Princeton Univ ");
        list3.add("Yale Univ ");
        list3.add("UC Santa Barbara");
        list3.add("UT Austin");
        list3.add("Boston Univ ");
        list3.add("Ohio St Univ ");
        list3.add("Penn St Univ ");
        list3.add("UC Irvine ");
        list3.add("UT Dallas ");
        list3.add("Northwestern Univ ");
        list3.add("San Jose St Univ ");
        list3.add("CSU Univ ");

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter4);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser currentUser = ParseUser.getCurrentUser();
                //ParseUser currentUser3= ParseUser.getCurrentUser();
                //ParseObject userUpdate = new ParseObject("UserUpdate");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");

                query.whereEqualTo("userId", currentUser);

                query.getFirstInBackground(new GetCallback<ParseObject>() {

                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            String newUnivName = spinner3.getSelectedItem().toString();
                            //object.get("fullName").toString();
                            Log.e("myapp",""+newUnivName);
                            Log.e("myapp","object--- "+object);
                            object.put("newUnivName", newUnivName);
                            object.saveInBackground();
                            Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                            startActivity(intent);


                            //mFullName = (TextView) findViewById(R.id.name);
                            //mFullName.setText(FullName);

                        } else {
                            // error
                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);

                            builder.setMessage(e.getMessage());
                            builder.setTitle("Error hai ethe ji");
                            builder.setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                                /* ParseObject userUpdate = new ParseObject("UserInfo");


                                 //userUpdate.put("updateId",currentUser);

                                 userUpdate.put("newUnivName", newUnivName);
                                 //userUpdate.saveInBackground();
                                 userUpdate.saveInBackground(new MySaveCallBack(context));

                                 Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                 startActivity(intent);

                             }
                         }); */
                    }
                });

            }
        });
    }
}