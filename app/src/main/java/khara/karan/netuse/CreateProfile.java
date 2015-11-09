package khara.karan.netuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateProfile extends ActionBarActivity {

    Spinner spinner1, spinner2;
    protected EditText mFullName, mPercent, mGre, mToefl;
    protected TextView mUserName;
    protected Button mNext;
    protected Context context;
    String objId;
    String univName;
    List<String> list1;
    List<String> list2;

    UnivDetail univDetail;
    HashMap<String,UnivDetail> univDetailHashMap;
    String objID, univRate;

    String fullname;
    int percent,gre,toefl;
    String univnameSelected, countrySelected;
    UnivDetail ud;

    ParseObject object;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;


        ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                // Intent intent2 = getIntent();
                //String get_username = intent2.getStringExtra("mUsername");
                String get_username = ParseUser.getCurrentUser().getUsername().toString();
                //get value from edit text fields
                mUserName = (TextView) findViewById(R.id.getUserName);
                mUserName.setText(get_username);

                //Toast.makeText(CreateProfile.this, "passed username: "+mUserName.getText().toString(), Toast.LENGTH_LONG).show();

                mFullName = (EditText) findViewById(R.id.myFullName);
                mPercent = (EditText) findViewById(R.id.myPercent);
                mGre = (EditText) findViewById(R.id.myGre);
                mToefl = (EditText) findViewById(R.id.myToefl);
                mNext = (Button) findViewById(R.id.myNext);
                mGre.setError(null);
                //get value from Spinner fields
                spinner1 = (Spinner) findViewById(R.id.spCountry);
                // s1.setOnItemSelectedListener(this);
                list1 = new ArrayList<String>();
                list1.add("--Choose Country--");
                list1.add("India");
                //        list1.add("US");
                //        list1.add("Australia");

                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, list1);
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter2.notifyDataSetChanged();
                spinner1.setAdapter(dataAdapter2);
                spinner2 = (Spinner) findViewById(R.id.spUniversity);

                list2 = new ArrayList<String>();

//                list2.add("--Choose University--"); list2.add("IIT Delhi"); list2.add("IIT Kharagpur");         list2.add("IIT Bombay"); list2.add("IIT Madras");
//                list2.add("IIT Guwahati"); list2.add("IIT Kanpur");  list2.add("IIT (others)");  list2.add("BITS Pilani");  list2.add("Delhi Technological University");
//                list2.add("NIT Surathkal");  list2.add("NIT Trichy");   list2.add("NIT Durgapur");      list2.add("Malaviya National Institute of Technology");
//                list2.add("NIT Rourkela");  list2.add("BIT Mesra");  list2.add("Thapar University"); list2.add("NIT Hamirpur");
//                list2.add("Jaypee Institute of Information Technology");  list2.add("PEC University"); list2.add("NIT Warangal");
//                list2.add("Indraprastha Institute of Information Technology, Delhi");  list2.add("College of Engineering, Pune");
//                list2.add("Manipal Institute of Technology");  list2.add("Pune Institute of Computer Technology");
//                list2.add("R.V. College of Engineering");   list2.add("Vellore Institute of Technology");  list2.add("Nirma Institute of Technology");
//                list2.add("UIET Panjab University");    list2.add("Anna University");  list2.add("Maharaja Agrasen Institute of Technology");
//                list2.add("Madras Institute of Technology");  list2.add("Narula Institute of Technology");  list2.add("Maharashtra Institute of Technology");
//                list2.add("Guru Tegh Bahadur Institute of Technology");  list2.add("Meerut Institute of Engineering & Technology");
//                list2.add("NIT Delhi");   list2.add("Birsa Institute of Technology");  list2.add("Chandigarh College of Engineering & Technology");
//                list2.add("Apeejay College of Engineering");   list2.add("Chitkara Institute of Engineering & Technology");
//                list2.add("Beant College of Engineering & Technology");  list2.add("IET Bhaddal Punjab");
//                list2.add("Lovely Professional University");    list2.add("Bangalore Institute of Technology");


                univDetailHashMap = new HashMap<String, UnivDetail>();

               for (ParseObject univDetailObj : list) {

                    univName = univDetailObj.get("univName").toString();
                    univDetail = new UnivDetail();
                    univDetail.setUnivCountry(univDetailObj.get("UnivCountry").toString());
                    if(univDetailObj.get("UnivCountry").toString().equals("India")){
                        univDetail.setUnivName(univName);
                        // try {
                        objID = univDetailObj.getObjectId().toString();
                        univDetail.setObjectID(objID);

                        univDetail.setRanking(univDetailObj.get("Ranking").toString());
                        univRate = univDetailObj.get("univRating").toString();
                        univDetail.setUnivRating(univRate);
                        list2.add(univName);

                        try {
                            univDetailHashMap.put(univName, univDetail);
                        } catch (Exception exp) {
                            Log.e("TAG", "uuniv detail HASH MAP exception: " + exp);
                        }

                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, list2);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter3.notifyDataSetChanged();
                        spinner2.setAdapter(dataAdapter3);

                    }
                }

                mNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String username = ParseUser.getCurrentUser().getUsername().toString();
                        String useremail = ParseUser.getCurrentUser().getEmail().toString();

                        Toast.makeText(CreateProfile.this, "getting username: " + username, Toast.LENGTH_LONG).show();
                        fullname = mFullName.getText().toString();
                        percent = Integer.parseInt(mPercent.getText().toString());
                        gre = Integer.parseInt(mGre.getText().toString());
                        toefl = Integer.parseInt(mToefl.getText().toString());
                        univnameSelected = spinner2.getSelectedItem().toString();
                        Log.e("TAG","univ selected: "+univnameSelected);
                        countrySelected = spinner1.getSelectedItem().toString();

                        final ParseUser newUser2 = ParseUser.getCurrentUser();
                        final ParseObject userInfo = new ParseObject("UserInfo");

                        ParseQuery<ParseObject> q2 = ParseQuery.getQuery("UnivDetail");
                        q2.whereEqualTo("univName", univnameSelected);
                        q2.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {

                                //ud = univDetailHashMap.get(univnameSelected);
                                //Log.e("TAG", "ud =====" + ud);
                                //String abc= ud.getObjectID();
                                //Log.e("TAG","abc))))))))))))))): "+abc);
                                userInfo.put("userId", newUser2);
                                //   userInfo.put("univId", newUser2);
                                userInfo.put("fullName", fullname);
                                userInfo.put("undergradPercent", percent);
                                userInfo.put("greScore", gre);
                                userInfo.put("toeflScore", toefl);
                                userInfo.put("undergradUniv", univnameSelected);
                                userInfo.put("prevUnivName", parseObject);
                                userInfo.put("country", countrySelected);
                                userInfo.put("newUnivName", "blank");
                                userInfo.saveInBackground();
                                //userInfo.saveInBackground(new MySaveCallBack(context));


                                Intent intent = new Intent(CreateProfile.this, FutureStudent.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                startActivity(intent);
                    }
                    });
                //});
                              /*  Intent intent = new Intent(CreateProfile.this, FutureStudent.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                startActivity(intent); */
                    }
                });
            }
        });

        /*List<String> list2 = new ArrayList<String>();   list2.add("--Choose University--"); list2.add("IIT Delhi"); list2.add("IIT Kharagpur");         list2.add("IIT Bombay"); list2.add("IIT Madras");
        list2.add("IIT Guwahati"); list2.add("IIT Kanpur");  list2.add("IIT (others)");  list2.add("BITS Pilani");  list2.add("Delhi Technological University");
        list2.add("NIT Surathkal");  list2.add("NIT Trichy");   list2.add("NIT Durgapur");      list2.add("Malaviya National Institute of Technology");
        list2.add("NIT Rourkela");  list2.add("BIT Mesra");  list2.add("Thapar University"); list2.add("NIT Hamirpur");
        list2.add("Jaypee Institute of Information Technology");  list2.add("PEC University"); list2.add("NIT Warangal");
        list2.add("Indraprastha Institute of Information Technology, Delhi");  list2.add("College of Engineering, Pune");
        list2.add("Manipal Institute of Technology");  list2.add("Pune Institute of Computer Technology");
        list2.add("R.V. College of Engineering");   list2.add("Vellore Institute of Technology");  list2.add("Nirma Institute of Technology");
        list2.add("UIET Panjab University");    list2.add("Anna University");  list2.add("Maharaja Agrasen Institute of Technology");
        list2.add("Madras Institute of Technology");  list2.add("Narula Institute of Technology");  list2.add("Maharashtra Institute of Technology");
        list2.add("Guru Tegh Bahadur Institute of Technology");  list2.add("Meerut Institute of Engineering & Technology");
        list2.add("NIT Delhi");   list2.add("Birsa Institute of Technology");  list2.add("Chandigarh College of Engineering & Technology");
        list2.add("Apeejay College of Engineering");   list2.add("Chitkara Institute of Engineering & Technology");
        list2.add("Beant College of Engineering & Technology");  list2.add("IET Bhaddal Punjab");
        list2.add("Lovely Professional University");    list2.add("Bangalore Institute of Technology"); */

    }

}
