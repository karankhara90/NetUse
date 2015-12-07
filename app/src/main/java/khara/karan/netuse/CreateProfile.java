package khara.karan.netuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class CreateProfile extends ActionBarActivity {

    Spinner spinner1, spinner2;
    protected EditText mFullName, mPercent, mGre, mToefl;
    protected TextView mUserName;
    protected Button mNext;
    protected Context context;
    String objId;
    String univName,univRank;
    List<Integer> univLogo;
    List<String> list1;
    List<String> list2;
    List<String> list2a;
    List<Integer> list2b;

    UnivDetail univDetail;
    //HashMap<String,UnivDetail> univDetailHashMap;
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


        list2 = new ArrayList<String>();
        list2a = new ArrayList<String>();
        list2b = new ArrayList<Integer>();

        list2b.add(R.drawable.yale_university);
        list2b.add(R.drawable.wright_state_university);
        list2b.add(R.drawable.wichita_state_university);
        list2b.add(R.drawable.west_virginia_university);
        list2b.add(R.drawable.wayne_state_university);
        list2b.add(R.drawable.washington_state_university);
        list2b.add(R.drawable.virginia_tech);
        list2b.add(R.drawable.vanderbilt_university);
        list2b.add(R.drawable.ut_austin);
        list2b.add(R.drawable.university_of_wisconsin);
        list2b.add(R.drawable.university_of_washington);
        list2b.add(R.drawable.university_of_virginia);
        list2b.add(R.drawable.university_of_utah);
        list2b.add(R.drawable.university_of_texas_san_antonio);
        list2b.add(R.drawable.university_of_texas_dallas);
        list2b.add(R.drawable.university_of_texas_arlington);
        list2b.add(R.drawable.university_of_southern_california);
        list2b.add(R.drawable.university_of_south_florida);
        list2b.add(R.drawable.university_of_south_carolina);
        list2b.add(R.drawable.university_of_rochester);
        list2b.add(R.drawable.university_of_pennsylvania);
        list2b.add(R.drawable.university_of_oklahoma);
        list2b.add(R.drawable.university_of_north_carolina_charlotte);
        list2b.add(R.drawable.university_of_north_carolina_chapel_hill);
        list2b.add(R.drawable.university_of_new_mexico);
        list2b.add(R.drawable.university_of_missouri);
        list2b.add(R.drawable.university_of_minnesota_twin_cities);
        list2b.add(R.drawable.university_of_miami);
        list2b.add(R.drawable.university_of_massachusetts_lowell);
        list2b.add(R.drawable.university_of_maryland);
        list2b.add(R.drawable.university_of_kansas);
        list2b.add(R.drawable.university_of_iowa);
        list2b.add(R.drawable.university_of_illinois_chicago);
        list2b.add(R.drawable.university_of_houston_cullen);
        list2b.add(R.drawable.university_of_georgia);
        list2b.add(R.drawable.university_of_florida);
        list2b.add(R.drawable.university_of_connecticut);
        list2b.add(R.drawable.university_of_colorado_boulder);
        list2b.add(R.drawable.university_of_cincinnati);
        list2b.add(R.drawable.university_of_chicago);
        list2b.add(R.drawable.university_of_california_santa_cruz);
        list2b.add(R.drawable.university_of_california_riverside);
        list2b.add(R.drawable.university_of_california_merced);
        list2b.add(R.drawable.university_of_arizona);
        list2b.add(R.drawable.university_of_alabama_huntsville);
        list2b.add(R.drawable.university_at_buffalo_suny);
        list2b.add(R.drawable.university_of_california_santa_barbara);
        list2b.add(R.drawable.university_of_california_san_francisco);
        list2b.add(R.drawable.university_of_california_san_diego);
        list2b.add(R.drawable.university_of_california_los_angeles);
        list2b.add(R.drawable.university_of_california_irvine);
        list2b.add(R.drawable.university_of_california_davis);
        list2b.add(R.drawable.university_of_california_berkeley);
        list2b.add(R.drawable.arizona_state_university_fulton);
        list2b.add(R.drawable.binghamton_university_suny);
        list2b.add(R.drawable.boston_university);
        list2b.add(R.drawable.brown_university);
        list2b.add(R.drawable.california_institute_of_technology);
        list2b.add(R.drawable.california_state_university_bakersfield);
        list2b.add(R.drawable.california_state_university_chico);
        list2b.add(R.drawable.california_state_university_east_bay);
        list2b.add(R.drawable.california_state_university_fresno);
        list2b.add(R.drawable.california_state_university_fullerton);
        list2b.add(R.drawable.california_state_university_long_beach);
        list2b.add(R.drawable.california_state_university_los_angeles);
        list2b.add(R.drawable.california_state_university_sacramento);
        list2b.add(R.drawable.california_state_university_san_francisco);
        list2b.add(R.drawable.carnegie_mellon_university);
        list2b.add(R.drawable.clemson_university);
        list2b.add(R.drawable.colorado_state_university);
        list2b.add(R.drawable.cornell_university);
        list2b.add(R.drawable.drexel_university);
        list2b.add(R.drawable.duke_university);
        list2b.add(R.drawable.georgia_institute_of_technology);
        list2b.add(R.drawable.harvard_university);
        list2b.add(R.drawable.illinois_institute_of_technology);
        list2b.add(R.drawable.iowa_state_university);
        list2b.add(R.drawable.kansas_state_university);
        list2b.add(R.drawable.michigan_state_university);
        list2b.add(R.drawable.mit_university);
        list2b.add(R.drawable.new_jersey_institute_of_technology);
        list2b.add(R.drawable.new_mexico_state_university);
        list2b.add(R.drawable.new_york_university);
        list2b.add(R.drawable.north_carolina_state_university);
        list2b.add(R.drawable.northeastern_university);
        list2b.add(R.drawable.northwestern_university);
        list2b.add(R.drawable.ohio_state_university);
        list2b.add(R.drawable.oregon_state_university);
        list2b.add(R.drawable.pennsylvania_state_university);
        list2b.add(R.drawable.princeton_university);
        list2b.add(R.drawable.purdue_university_west_lafayette);
        list2b.add(R.drawable.rice_university);
        list2b.add(R.drawable.san_diego_state_university);
        list2b.add(R.drawable.san_jose_state_university);
        list2b.add(R.drawable.stanford_university);
        list2b.add(R.drawable.stony_brook_university_suny);
        list2b.add(R.drawable.temple_university);
        list2b.add(R.drawable.texas_a_m_university);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
        query.orderByAscending("Ranking");
        query.whereEqualTo("UnivCountry","India");
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

                //univDetailHashMap = new HashMap<String, UnivDetail>();

               for (ParseObject univDetailObj : list) {

                    univName = univDetailObj.get("univName").toString();
                    univRank = univDetailObj.get("Ranking").toString();
//                    univLogo = univDetailObj.get("univLogo");

                    univDetail = new UnivDetail();
                    univDetail.setUnivCountry(univDetailObj.get("UnivCountry").toString());
                    //if(univDetailObj.get("UnivCountry").toString().equals("India")){
                        univDetail.setUnivName(univName);
                        objID = univDetailObj.getObjectId().toString();
                        univDetail.setObjectID(objID);

                        univDetail.setRanking(univRank);
                        univRate = univDetailObj.get("univRating").toString();
                        univDetail.setUnivRating(univRate);

//                        univDetail.setUnivLogo(univLogo);

                        list2.add(univName);
                        list2a.add(univRank);
                        try{
//                            list2b.add(Integer.parseInt(univLogo));
                        }catch (Exception exc){
                            Log.e("TAG","exc exception in univLogo is: "+exc);
                        }



                        /*try {
                            univDetailHashMap.put(univName, univDetail);
                        } catch (Exception exp) {
                            Log.e("TAG", "uuniv detail HASH MAP exception: " + exp);
                        }*/

//                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(context,
//                                android.R.layout.simple_spinner_item, list2);
                        MyAdapter dataAdapter3 = new MyAdapter(context,
                           R.layout.custom_spinner ,list2);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter3.notifyDataSetChanged();
                        spinner2.setAdapter(dataAdapter3);

                    //}
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
                       // Log.e("TAG","univ selected: "+univnameSelected);
                        countrySelected = spinner1.getSelectedItem().toString();

                        final ParseUser newUser2 = ParseUser.getCurrentUser();
                        final ParseObject userInfo = new ParseObject("UserInfo");

                        ParseQuery<ParseObject> q2 = ParseQuery.getQuery("UnivDetail");
                        q2.whereEqualTo("univName", univnameSelected);
                        setProgressBarIndeterminateVisibility(true);
                        q2.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                try {
                                    setProgressBarIndeterminateVisibility(false);
                                    userInfo.put("userId", newUser2);
                                    userInfo.put("fullName", fullname);
                                    userInfo.put("undergradPercent", percent);
                                    userInfo.put("greScore", gre);
                                    userInfo.put("toeflScore", toefl);
                                    userInfo.put("undergradUniv", univnameSelected);
                                    userInfo.put("prevUnivName", parseObject);
                                    userInfo.put("country", countrySelected);
                                    userInfo.put("newUnivName", "blank");
                                } catch (Exception exct) {
                                    Log.e("TAG", "exct exception:;;; " + exct);
                                }
                                try {
                                    userInfo.put("predScore",0);
                                } catch (Exception exct2) {
                                    Log.e("TAG", "exct2 exception:------------ " + exct2);
                                }

                                userInfo.saveInBackground();
                                //userInfo.saveInBackground(new MySaveCallBack(context));

                                Intent intent = new Intent(CreateProfile.this, FutureStudent.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });


    }

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context ctx, int txtViewResourceId, List<String> objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }
        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, parent,
                    false);
            TextView main_text = (TextView) mySpinner
                    .findViewById(R.id.text_main_univ);
            main_text.setText(list2.get(position));

            TextView subSpinner = (TextView) mySpinner
                    .findViewById(R.id.sub_text_rank);
            subSpinner.setText(list2a.get(position));
            try{
//                ImageView left_icon = (ImageView) mySpinner
//                        .findViewById(R.id.univ_logo_pic);
//                left_icon.setImageResource(list2b.get(position));
            }
            catch (Exception exc2){
                Log.e("TAG","exc2 exception in imageview is: "+exc2);
            }
            return mySpinner;
        }
    }
}