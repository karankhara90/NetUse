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
    String newUnivSelected;

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
        list3.add("MIT Univ");list3.add("Stanford Univ");list3.add("UC Berkeley");
        list3.add("Univ of Southern California");list3.add("Univ of Chicago");
        list3.add("UT Austin");list3.add("UC Los Angeles");list3.add("UC San Diego");list3.add("Princeton Univ");
        list3.add("Northwestern Univ");list3.add("UC Santa Barbara");
        list3.add("UC San Francisco");list3.add("Penn St Univ");list3.add("Univ of Washington");
        list3.add("Duke University");list3.add("Rice University");list3.add("Ohio St Univ");
        list3.add("UC Davis");list3.add("Yale Univ");list3.add("Boston Univ");
        list3.add("UC Irvine");list3.add("Iowa State University");
        list3.add("Michigan State University");list3.add("University of Utah");list3.add("University at Buffalo SUNY");
        list3.add("Stony Brook University SUNY");list3.add("University of California Riverside");
        list3.add("UT Dallas");list3.add("Oregon State University");list3.add("Washington State University");
        list3.add("University of California Santa Cruz");list3.add("University of Texas Arlington");
        list3.add("San Jose St Univ");list3.add("University of South Florida");
        list3.add("Binghamton University SUNY");list3.add("New Mexico State University");
        list3.add("University of North Carolina Charlotte");list3.add("Wright State University");
        list3.add("University of California Merced");list3.add("San Diego State University");
        list3.add("California St University Fresno");list3.add("California St University Fullerton");
        list3.add("California St University Long Beach");


        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.notifyDataSetChanged();
        spinner3.setAdapter(dataAdapter4);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser currentUser = ParseUser.getCurrentUser();
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRating");
//
//                query.whereEqualTo("userId", currentUser);
//
//                query.getFirstInBackground(new GetCallback<ParseObject>() {
//
//                    public void done(ParseObject object, ParseException e) {
//                        if (e == null) {
//                            String newUnivName = spinner3.getSelectedItem().toString();
//                            object.put("newUnivName", newUnivName);
//                            object.put("nowUnivName", object);
//                            object.saveInBackground();
//                            Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                            startActivity(intent);
//
//
//                        } else {
//                            // error
//                            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
//
//                            builder.setMessage(e.getMessage());
//                            builder.setTitle("Error hai ethe ji");
//                            builder.setPositiveButton(android.R.string.ok, null);
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                        }
//
//                    }
//                });

                newUnivSelected = spinner3.getSelectedItem().toString();
                if(! newUnivSelected.equals("--Choose New University--") ){
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
                    query.whereEqualTo("userId", currentUser);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(final ParseObject parseObject, ParseException e) {
                            if(e == null){
                                ParseQuery<ParseObject> q2 = ParseQuery.getQuery("UnivDetail");
                                q2.whereEqualTo("univName", newUnivSelected);
                                q2.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject parseObject2, ParseException e) {
                                        parseObject.put("nowUnivName", parseObject2);
                                        parseObject.put("newUnivName", newUnivSelected);


                                        parseObject.saveInBackground();
                                        Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                Log.e("TAG","e is not null in update profile");
                            }

                        }
                    });
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);

                    builder.setMessage("Choose some University first");
                    builder.setTitle("Error hai ethe ji");
                    builder.setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }
}