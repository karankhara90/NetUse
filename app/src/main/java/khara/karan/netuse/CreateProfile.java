package khara.karan.netuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import khara.karan.netuse.MyPOJO.CountryCRUD;
import khara.karan.netuse.MyPOJO.UserInfo;

//import khara.karan.netuse.MyPOJO.CountryPOJO;

public class CreateProfile extends AppCompatActivity {

    Spinner spinnerCountry, spinnerUniversity;
    protected EditText mFullName, mPercent, mGre, mToefl;
    protected TextView mUserName;
    protected Button mNext;
    protected Context context;


//    List<Integer> univLogo;
    List<String> arrayListCountry;
    List<String> list2;
    List<String> list2a;
    List<String> arrayListUniversities;
    List<String> arrayListRanking;
    List<String> arrayListRating;
    List<String> arrayListLogos;


    private String userId="";
    String fullname="";

    private String univId="";
    private String univName ="";
    private String univRank="";
    private String univRate="";
    private String univCountry="";
    private String univState="";
    private String univCity="";
    private String univLogo;
    private String univType;

    private String univnameSelected="";
    private String countrySelected="";
    int percent,gre,toefl;

    DatabaseReference mUserProfileDB;
    DatabaseReference mUnivDetailsDB;

    CountryCRUD countryCRUD;
    private UserInfo userInfo;
    private UnivDetail[] univDetail;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        mUserProfileDB = FirebaseDatabase.getInstance().getReference().child("UserProfile");
        mUnivDetailsDB = FirebaseDatabase.getInstance().getReference().child("UniversityProfile");

        mUnivDetailsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readUniversityDetails(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        list2 = new ArrayList<String>();
        list2a = new ArrayList<String>();
//        arrayListUniversities = new ArrayList<String>();
//
//        arrayListUniversities.add(R.drawable.yale_university);
//        arrayListUniversities.add(R.drawable.wright_state_university);
//        arrayListUniversities.add(R.drawable.wichita_state_university);
//        arrayListUniversities.add(R.drawable.west_virginia_university);
//        arrayListUniversities.add(R.drawable.wayne_state_university);
//        arrayListUniversities.add(R.drawable.washington_state_university);
//        arrayListUniversities.add(R.drawable.virginia_tech);
//        arrayListUniversities.add(R.drawable.vanderbilt_university);
//        arrayListUniversities.add(R.drawable.ut_austin);
//        arrayListUniversities.add(R.drawable.university_of_wisconsin);
//        arrayListUniversities.add(R.drawable.university_of_washington);
//        arrayListUniversities.add(R.drawable.university_of_virginia);
//        arrayListUniversities.add(R.drawable.university_of_utah);
//        arrayListUniversities.add(R.drawable.university_of_texas_san_antonio);
//        arrayListUniversities.add(R.drawable.university_of_texas_dallas);
//        arrayListUniversities.add(R.drawable.university_of_texas_arlington);
//        arrayListUniversities.add(R.drawable.university_of_southern_california);
//        arrayListUniversities.add(R.drawable.university_of_south_florida);
//        arrayListUniversities.add(R.drawable.university_of_south_carolina);
//        arrayListUniversities.add(R.drawable.university_of_rochester);
//        arrayListUniversities.add(R.drawable.university_of_pennsylvania);
//        arrayListUniversities.add(R.drawable.university_of_oklahoma);
//        arrayListUniversities.add(R.drawable.university_of_north_carolina_charlotte);
//        arrayListUniversities.add(R.drawable.university_of_north_carolina_chapel_hill);
//        arrayListUniversities.add(R.drawable.university_of_new_mexico);
//        arrayListUniversities.add(R.drawable.university_of_missouri);
//        arrayListUniversities.add(R.drawable.university_of_minnesota_twin_cities);
//        arrayListUniversities.add(R.drawable.university_of_miami);
//        arrayListUniversities.add(R.drawable.university_of_massachusetts_lowell);
//        arrayListUniversities.add(R.drawable.university_of_maryland);
//        arrayListUniversities.add(R.drawable.university_of_kansas);
//        arrayListUniversities.add(R.drawable.university_of_iowa);
//        arrayListUniversities.add(R.drawable.university_of_illinois_chicago);
//        arrayListUniversities.add(R.drawable.university_of_houston_cullen);
//        arrayListUniversities.add(R.drawable.university_of_georgia);
//        arrayListUniversities.add(R.drawable.university_of_florida);
//        arrayListUniversities.add(R.drawable.university_of_connecticut);
//        arrayListUniversities.add(R.drawable.university_of_colorado_boulder);
//        arrayListUniversities.add(R.drawable.university_of_cincinnati);
//        arrayListUniversities.add(R.drawable.university_of_chicago);
//        arrayListUniversities.add(R.drawable.university_of_california_santa_cruz);
//        arrayListUniversities.add(R.drawable.university_of_california_riverside);
//        arrayListUniversities.add(R.drawable.university_of_california_merced);
//        arrayListUniversities.add(R.drawable.university_of_arizona);
//        arrayListUniversities.add(R.drawable.university_of_alabama_huntsville);
//        arrayListUniversities.add(R.drawable.university_at_buffalo_suny);
//        arrayListUniversities.add(R.drawable.university_of_california_santa_barbara);
//        arrayListUniversities.add(R.drawable.university_of_california_san_francisco);
//        arrayListUniversities.add(R.drawable.university_of_california_san_diego);
//        arrayListUniversities.add(R.drawable.university_of_california_los_angeles);
//        arrayListUniversities.add(R.drawable.university_of_california_irvine);
//        arrayListUniversities.add(R.drawable.university_of_california_davis);
//        arrayListUniversities.add(R.drawable.university_of_california_berkeley);
//        arrayListUniversities.add(R.drawable.arizona_state_university_fulton);
//        arrayListUniversities.add(R.drawable.binghamton_university_suny);
//        arrayListUniversities.add(R.drawable.boston_university);
//        arrayListUniversities.add(R.drawable.brown_university);
//        arrayListUniversities.add(R.drawable.california_institute_of_technology);
//        arrayListUniversities.add(R.drawable.california_state_university_bakersfield);
//        arrayListUniversities.add(R.drawable.california_state_university_chico);
//        arrayListUniversities.add(R.drawable.california_state_university_east_bay);
//        arrayListUniversities.add(R.drawable.california_state_university_fresno);
//        arrayListUniversities.add(R.drawable.california_state_university_fullerton);
//        arrayListUniversities.add(R.drawable.california_state_university_long_beach);
//        arrayListUniversities.add(R.drawable.california_state_university_los_angeles);
//        arrayListUniversities.add(R.drawable.california_state_university_sacramento);
//        arrayListUniversities.add(R.drawable.california_state_university_san_francisco);
//        arrayListUniversities.add(R.drawable.carnegie_mellon_university);
//        arrayListUniversities.add(R.drawable.clemson_university);
//        arrayListUniversities.add(R.drawable.colorado_state_university);
//        arrayListUniversities.add(R.drawable.cornell_university);
//        arrayListUniversities.add(R.drawable.drexel_university);
//        arrayListUniversities.add(R.drawable.duke_university);
//        arrayListUniversities.add(R.drawable.georgia_institute_of_technology);
//        arrayListUniversities.add(R.drawable.harvard_university);
//        arrayListUniversities.add(R.drawable.illinois_institute_of_technology);
//        arrayListUniversities.add(R.drawable.iowa_state_university);
//        arrayListUniversities.add(R.drawable.kansas_state_university);
//        arrayListUniversities.add(R.drawable.michigan_state_university);
//        arrayListUniversities.add(R.drawable.mit_university);
//        arrayListUniversities.add(R.drawable.new_jersey_institute_of_technology);
//        arrayListUniversities.add(R.drawable.new_mexico_state_university);
//        arrayListUniversities.add(R.drawable.new_york_university);
//        arrayListUniversities.add(R.drawable.north_carolina_state_university);
//        arrayListUniversities.add(R.drawable.northeastern_university);
//        arrayListUniversities.add(R.drawable.northwestern_university);
//        arrayListUniversities.add(R.drawable.ohio_state_university);
//        arrayListUniversities.add(R.drawable.oregon_state_university);
//        arrayListUniversities.add(R.drawable.pennsylvania_state_university);
//        arrayListUniversities.add(R.drawable.princeton_university);
//        arrayListUniversities.add(R.drawable.purdue_university_west_lafayette);
//        arrayListUniversities.add(R.drawable.rice_university);
//        arrayListUniversities.add(R.drawable.san_diego_state_university);
//        arrayListUniversities.add(R.drawable.san_jose_state_university);
//        arrayListUniversities.add(R.drawable.stanford_university);
//        arrayListUniversities.add(R.drawable.stony_brook_university_suny);
//        arrayListUniversities.add(R.drawable.temple_university);
//        arrayListUniversities.add(R.drawable.texas_a_m_university);


        mFullName = (EditText) findViewById(R.id.myFullName);
        mPercent = (EditText) findViewById(R.id.myPercent);
        mGre = (EditText) findViewById(R.id.myGre);
        mToefl = (EditText) findViewById(R.id.myToefl);
        mNext = (Button) findViewById(R.id.myNext);
        mGre.setError(null);

        //get value from Spinner fields
        spinnerCountry = (Spinner) findViewById(R.id.spCountry);
        spinnerUniversity = (Spinner) findViewById(R.id.spUniversity);

        mNext = (Button) findViewById(R.id.myNext);

        // add countries to arraylist
        arrayListCountry = new ArrayList<String>();
        arrayListCountry.add("--Choose Country--");
        arrayListCountry.add("India");
        arrayListCountry.add("USA");
        arrayListCountry.add("Canada");
        arrayListCountry.add("England");

        ArrayAdapter<String> dataAdapterCountry = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, arrayListCountry);
        dataAdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterCountry.notifyDataSetChanged();
        spinnerCountry.setAdapter(dataAdapterCountry);


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    userId = FirebaseAuth.getInstance().getUid();

                    Log.e("TAG","userId: "+ userId);
                    fullname = mFullName.getText().toString();
                    percent = Integer.parseInt(mPercent.getText().toString());
                    gre = Integer.parseInt(mGre.getText().toString());
                    toefl = Integer.parseInt(mToefl.getText().toString());
                    univnameSelected = spinnerUniversity.getSelectedItem().toString();
                    Log.e("TAG","univ selected: "+univnameSelected);
                    countrySelected = spinnerCountry.getSelectedItem().toString();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                userInfo = new UserInfo(userId,fullname,percent,gre,toefl,univnameSelected,countrySelected);

                Log.e("TAG","userInfo.getCountrySelected(): "+userInfo.getCountrySelected());
                Log.e("TAG","userInfo.getUserId(): "+userInfo.getUserId());

                mUserProfileDB.child(FirebaseAuth.getInstance().getUid()).setValue(userInfo);

                Toast.makeText(CreateProfile.this, "Success", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(CreateProfile.this, LoginActivity.class));
            }
        });




//
//               for (ParseObject univDetailObj : list) {
//
//                    univName = univDetailObj.get("univName").toString();
//                    univRank = univDetailObj.get("Ranking").toString();
////                    univLogo = univDetailObj.get("univLogo");
//
//                    univDetail = new UnivDetail();
//                    univDetail.setUnivCountry(univDetailObj.get("UnivCountry").toString());
//                    //if(univDetailObj.get("UnivCountry").toString().equals("India")){
//                        univDetail.setUnivName(univName);
//                        objID = univDetailObj.getObjectId().toString();
//                        univDetail.setUnivId(objID);
//
//                        univDetail.setRanking(univRank);
//                        univRate = univDetailObj.get("univRating").toString();
//                        univDetail.setUnivRating(univRate);
//
////                        univDetail.setUnivLogo(univLogo);
//
//                        list2.add(univName);
//                        list2a.add(univRank);
//                        try{
////                            list2b.add(Integer.parseInt(univLogo));
//                        }catch (Exception exc){
//                            Log.e("TAG","exc exception in univLogo is: "+exc);
//                        }
//
//
//
//                        /*try {
//                            univDetailHashMap.put(univName, univDetail);
//                        } catch (Exception exp) {
//                            Log.e("TAG", "uuniv detail HASH MAP exception: " + exp);
//                        }*/
//
////                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(context,
////                                android.R.layout.simple_spinner_item, list2);
//                        MyAdapter dataAdapter3 = new MyAdapter(context,
//                           R.layout.custom_spinner ,list2);
//                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        dataAdapter3.notifyDataSetChanged();
//                        spinner2.setAdapter(dataAdapter3);
//
//                    //}
//               }
//
//                mNext.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        String username = ParseUser.getCurrentUser().getUsername().toString();
//                        String useremail = ParseUser.getCurrentUser().getEmail().toString();
//
//                        Toast.makeText(CreateProfile.this, "getting username: " + username, Toast.LENGTH_LONG).show();
//                        fullname = mFullName.getText().toString();
//                        percent = Integer.parseInt(mPercent.getText().toString());
//                        gre = Integer.parseInt(mGre.getText().toString());
//                        toefl = Integer.parseInt(mToefl.getText().toString());
//                        univnameSelected = spinner2.getSelectedItem().toString();
//                       // Log.e("TAG","univ selected: "+univnameSelected);
//                        countrySelected = spinner1.getSelectedItem().toString();
//
//                        final ParseUser newUser2 = ParseUser.getCurrentUser();
//                        final ParseObject userInfo = new ParseObject("UserInfo");
//
//                        ParseQuery<ParseObject> q2 = ParseQuery.getQuery("UnivDetail");
//                        q2.whereEqualTo("univName", univnameSelected);
//                        setProgressBarIndeterminateVisibility(true);
////                        q2.getFirstInBackground(new GetCallback<ParseObject>() {
////                            @Override
////                            public void done(ParseObject parseObject, ParseException e) {
////                                try {
////                                    setProgressBarIndeterminateVisibility(false);
////                                    userInfo.put("userId", newUser2);
////                                    userInfo.put("fullName", fullname);
////                                    userInfo.put("undergradPercent", percent);
////                                    userInfo.put("greScore", gre);
////                                    userInfo.put("toeflScore", toefl);
////                                    userInfo.put("undergradUniv", univnameSelected);
////                                    userInfo.put("prevUnivName", parseObject);
////                                    userInfo.put("country", countrySelected);
////                                    userInfo.put("newUnivName", "blank");
////                                } catch (Exception exct) {
////                                    Log.e("TAG", "exct exception:;;; " + exct);
////                                }
////                                try {
////                                    userInfo.put("predScore",0);
////                                } catch (Exception exct2) {
////                                    Log.e("TAG", "exct2 exception:------------ " + exct2);
////                                }
////
//////                                userInfo.saveInBackground();
////                                //userInfo.saveInBackground(new MySaveCallBack(context));
////
////                                Intent intent = new Intent(CreateProfile.this, FutureStudent.class);
////                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
////                                startActivity(intent);
////                            }
////                        });
//                    }
//                });
//            }
//        });


    }

    private void readUniversityDetails(DataSnapshot dataSnapshot){
        int size = (int) dataSnapshot.getChildrenCount();
        Log.e("TAG","size: "+size);
        int i=0;
        arrayListUniversities = new ArrayList<String>();
        arrayListRanking = new ArrayList<String>();
        arrayListRating = new ArrayList<String>();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            univDetail = new UnivDetail[size+1];

            Map<String, String>  univMap = (Map<String, String>) ds.getValue();

                univId =   univMap.get("univId");
                univName = univMap.get("univName");
                univRank = String.valueOf(univMap.get("univRanking"));
                univRate = String.valueOf(univMap.get("univRating"));
                univCountry = univMap.get("univCountry");
                univState = univMap.get("univState");
                univCity = univMap.get("univCity");
                univLogo = univMap.get("univLogo");
                univType = univMap.get("univType");

                Log.e("TAG","univName from Map: "+univName);

                if(i < size){
                    Log.e("TAG","i is: "+i);
                    univDetail[i] = new UnivDetail();
                    univDetail[i].setUnivId(univId);
                    Log.e("TAG","univ id: "+univDetail[i].getUnivId());
                    univDetail[i].setUnivName(univName);
                    univDetail[i].setUnivCountry(univCountry);
                    univDetail[i].setUnivState(univState);
                    univDetail[i].setUnivCity(univCity);
                    univDetail[i].setUnivRanking(univRank);
                    univDetail[i].setUnivRating(univRate);
                    univDetail[i].setUnivType(univType);
                    univDetail[i].setUnivLogo(univLogo);

                    Log.e("TAG","univDetail.getUnivId: "+univDetail[i].getUnivId());
                    Log.e("TAG","univDetail.getUnivName: "+univDetail[i].getUnivName());
                    Log.e("TAG","univDetail.getUnivRanking"+univDetail[i].getUnivRanking());
                    Log.e("TAG","univDetail.getUnivState: "+univDetail[i].getUnivState());

//                arrayListUniversities.add(univDetail.getUnivId());
                    arrayListUniversities.add(univName);
                    arrayListRanking.add(univDetail[i].getUnivRanking());
                    arrayListRating.add(univDetail[i].getUnivRating());

//                    arrayListLogos.add();
                }
            i++;

        }


        try {
            MyAdapter dataAdapterUniversity = new MyAdapter(context, R.layout.custom_spinner ,arrayListUniversities);
            dataAdapterUniversity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapterUniversity.notifyDataSetChanged();
            spinnerUniversity.setAdapter(dataAdapterUniversity);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        public View getView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }
        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {
            Log.e("TAG","position: "+position);
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, parent,
                    false);

                TextView tvUnivName = (TextView) mySpinner
                        .findViewById(R.id.tvProfileUnivName);
//            tvUnivName.setText(univDetail[position].getUnivName());
                tvUnivName.setText(arrayListUniversities.get(position));

                TextView tvRanking = (TextView) mySpinner
                        .findViewById(R.id.tvProfileRanking);
                tvRanking.setText(arrayListRanking.get(position));

                TextView tvRating = (TextView) mySpinner
                        .findViewById(R.id.tvProfileRating);
                tvRating.setText(arrayListRating.get(position));
//            subSpinner.setText("rank");

                try{
//                ImageView left_icon = (ImageView) mySpinner
//                        .findViewById(R.id.univ_logo_pic);
//                left_icon.setImageResource(list2b.get(position));
                }
                catch (Exception exc2){
                    Log.e("TAG","exc2 exception in imageview is: "+exc2);
                }

//            }

            return mySpinner;
        }
    }
}