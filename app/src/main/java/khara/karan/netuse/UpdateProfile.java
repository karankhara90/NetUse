package khara.karan.netuse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
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
    String univName,univRank;
    String objID, univRate;
    List<String> list2;
    List<String> list2a;
    List<Integer> list2b;

    UnivDetail univDetail;
    private float mUniRating;
    private float mPredScore;
    private ImageView image_expert4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        mDone = (Button) findViewById(R.id.myDone);


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
        query.whereEqualTo("UnivCountry","United States");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

//                List<String> list3 = new ArrayList<String>();
                spinner3 = (Spinner) findViewById(R.id.spNewUniv);
                for (ParseObject univDetailObj : list) {
                    univName = univDetailObj.get("univName").toString();
                    univRank = univDetailObj.get("Ranking").toString();
                    univDetail = new UnivDetail();
                    univDetail.setUnivCountry(univDetailObj.get("UnivCountry").toString());

//                    if(univDetailObj.get("UnivCountry").toString().equals("United States")) {
                        univDetail.setUnivName(univName);
                        objID = univDetailObj.getObjectId().toString();
                        univDetail.setObjectID(objID);

                    univDetail.setRanking(univRank);
                        univRate = univDetailObj.get("univRating").toString();
                        univDetail.setUnivRating(univRate);
                        list2.add(univName);
                        list2a.add(univRank);


                        MyAdapter dataAdapter4 = new MyAdapter(context,
                                R.layout.custom_spinner, list2);
                        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        dataAdapter4.notifyDataSetChanged();
                        spinner3.setAdapter(dataAdapter4);
                    //}
                }
                mDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ParseUser currentUser = ParseUser.getCurrentUser();

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
//                                                parseObject.getParseObject("nowUnivName");
                                                mUniRating =  Float.valueOf(parseObject2.get("univRating").toString());
                                                mPredScore = Float.valueOf(parseObject.get("predScore").toString());

                                                float rateDiff = Math.abs(mPredScore - mUniRating);
                                                if(parseObject.getString("rateDiff") == null){
                                                    parseObject.put("rateDiff",rateDiff);
                                                }

                                                parseObject.saveInBackground();

                                                Intent intent = new Intent(UpdateProfile.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                                                startActivity(intent);
                                            }
                                        });
                                    }else{
                                        Log.e("TAG", "e is not null in update profile");
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
//                image_expert4 = (ImageView)mySpinner.findViewById(R.id.univ_logo_pic);
//
//                ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
//                query.whereEqualTo("univName", list2.get(position));
//                query.getFirstInBackground(new GetCallback<ParseObject>() {
//                    @Override
//                    public void done(ParseObject parseObject, ParseException e) {
//                        ParseFile image = (ParseFile) parseObject.getParseFile("univLogo");
//                        displayImage(image, image_expert4);
//                    }
//                });
            }
            catch (Exception exc2){
                Log.e("TAG","exc2 exception in imageview is: "+exc2);
            }

            return mySpinner;
        }
    }
    private void displayImage(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {

                @Override
                public void done(byte[] data, ParseException e) {

                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);

                        if (bmp != null) {

                            Log.e("parse file ok", " null");
                            // img.setImageBitmap(Bitmap.createScaledBitmap(bmp,
                            // (display.getWidth() / 5),
                            // (display.getWidth() /50), false));
                            img.setImageBitmap(bmp);

                            // img.setPadding(10, 10, 0, 0);



                        }
                    } else {
                        Log.e("paser after downloade", " null");
                    }

                }
            });
        } else {

            Log.e("parse file", " null");

            // img.setImageResource(R.drawable.ic_launcher);

            img.setPadding(10, 10, 10, 10);
        }

    }
}

//        list3.add("--Choose New University--");
//        list3.add("MIT Univ");list3.add("Stanford Univ");list3.add("UC Berkeley");
//        list3.add("Univ of Southern California");list3.add("Univ of Chicago");
//        list3.add("UT Austin");list3.add("UC Los Angeles");list3.add("UC San Diego");list3.add("Princeton Univ");
//        list3.add("Northwestern Univ");list3.add("UC Santa Barbara");
//        list3.add("UC San Francisco");list3.add("Penn St Univ");list3.add("Univ of Washington");
//        list3.add("Duke University");list3.add("Rice University");list3.add("Ohio St Univ");
//        list3.add("UC Davis");list3.add("Yale Univ");list3.add("Boston Univ");
//        list3.add("UC Irvine");list3.add("Iowa State University");
//        list3.add("Michigan State University");list3.add("University of Utah");list3.add("University at Buffalo SUNY");
//        list3.add("Stony Brook University SUNY");list3.add("University of California Riverside");
//        list3.add("UT Dallas");list3.add("Oregon State University");list3.add("Washington State University");
//        list3.add("University of California Santa Cruz");list3.add("University of Texas Arlington");
//        list3.add("San Jose St Univ");list3.add("University of South Florida");
//        list3.add("Binghamton University SUNY");list3.add("New Mexico State University");
//        list3.add("University of North Carolina Charlotte");list3.add("Wright State University");
//        list3.add("University of California Merced");list3.add("San Diego State University");
//        list3.add("California St University Fresno");list3.add("California St University Fullerton");
//        list3.add("California St University Long Beach");