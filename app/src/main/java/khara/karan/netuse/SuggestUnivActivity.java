package khara.karan.netuse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SuggestUnivActivity extends Activity {
    ListView listview;

    float UndergradUnivRating1, UndergradStudRating1, GreRating1;
    float[] UndergradUnivRating2;
    float[] UndergradStudRating2;
    float[] GreRating2;
   String fullname, undergradUniv1,undergradUniv2 ;
    float greScore, undergradPercent;
//    public static
    public float tempRating=0;

    String[] seniorStuds;
    int seniorStudSize;
    float[] studAvgRating;
    float avgSeniorUserRating;
    String currentuser;
    public int condition = 0;
    public float totalUserRating;
    ParseUser currUser;
    int i;
    String[] univRating;




    public static final String TAG=SuggestUnivActivity.class.getSimpleName();
   // protected ParseUser mCurrentUser3;
    protected Button mBtnBackSuggest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_univ);

        currUser = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        query.whereEqualTo("userId", currUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                //Log.e(TAG, "gre")
                //fullname = parseObject.get("fullName").toString();
                undergradUniv1 = parseObject.get("undergradUniv").toString();

                float GreScore1, UndergradPercent1;
                GreScore1 = Float.valueOf(parseObject.get("greScore").toString());
                UndergradPercent1 = Float.valueOf(parseObject.get("undergradPercent").toString());
                suggestDB(parseObject.get("greScore").toString(), parseObject.get("undergradPercent").toString());


                GreRating1 = getGreRating(GreScore1);
                UndergradStudRating1 = getUndergradStudRating(UndergradPercent1);
                //condition = 1;

                //getThisUserAllRatings(parseObject);
             /*  String  grescore = parseObject.get("greScore").toString();
               String undergradpercent = parseObject.get("undergradPercent").toString();
                suggestDB(grescore, undergradpercent);

                float greScore = Float.valueOf(grescore);
                float undergradPercent = Float.valueOf(undergradpercent);
                float greRating = getGreRating(greScore);
                float undergradStudRating = getUndergradStudRating(undergradPercent); */


                //getThisUserTwoRatings(parseObject,UndergradStudRating, GreRating );
                //getUserUnivRating();
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UnivDetail");
                query1.whereEqualTo("univName", undergradUniv1);
                query1.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if (parseObject != null) {
                            //float underGradUnivRating;
                            //tempRating = Integer.parseInt(parseObject.get("univRating").toString());

                            String temp = parseObject.get("univRating").toString();
                            //Log.e(TAG, "rating is **** " + tempRating);
                            UndergradUnivRating1 = Float.valueOf(temp);
                            //Log.e(TAG, "greRating: " + GreRating + ",   undergradUnivRating: " + undergradUnivRating + ",  undergradStudRating: " + UndergradStudRating);
                            //Log.e("LOG", "TOTAL RATING+++" + totalUserRating);
                            Log.e(TAG, "greRating1: " + GreRating1 + ",   undergradUnivRating1: " + UndergradUnivRating1 + ",  undergradStudRating21: " + UndergradStudRating1);
                            float getCurrentUserTotalRating = getThisUserAvgRatings(GreRating1, UndergradUnivRating1, UndergradStudRating1);
                            Log.e(TAG, "Current Student's total rating: " + getCurrentUserTotalRating);

                        } else {
                            Log.e(TAG, "exception = ^^^^^^^" + e);
                        }
                    }
                });

                // float getCurrentUserTotalrRating = getThisUserTotalAvgRating(fullname,undergraduniv,grescore,undergradpercent);
                // float thisStudTotalRating =totalUserRating;


                //getQuery2();


            }
        });
        getQuery2();


        //to get average rating of seniors



      //  Log.e(TAG, "No of seniors: " + seniorStudSize);
       // Log.e(TAG, "Senior Students");
       // Log.e(TAG, "first senior: "+ seniorStuds[0]);
       // for (int j = 0; j <= 1; j++) {
            //Log.e(TAG, "" + j + ": " + seniorStuds[j]);
            //userSimilarities(currentuser, seniorStuds[j]);
        //}

        //userSimilarities(currentuser, seniorStuds);

       // Recommend(GreRating, UndergradUnivRating, UndergradStudRating);// ,ratingUniv);



        mBtnBackSuggest = (Button) findViewById(R.id.btnBackNames);
        mBtnBackSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SuggestUnivActivity.this, FutureStudent.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent2);
            }
        });

    }

    //void getThisUserTwoRatings(float UndergradStudRating,float GreRating){

        //GreRating = getGreRating(greScore);
        //UndergradStudRating = getUndergradStudRating(undergradPercent);
    //}


    float getGreRating(float greScore){
        float greRating;
        float temp = greScore-240;
        greRating = temp/10;
        return greRating;
    }
    float getUndergradStudRating(float undergradPercent){
        undergradPercent = undergradPercent/10;
        return undergradPercent;
    }
   /* float getThisUserTotalAvgRating(String fullname,String undergraduniv,String grescore,String undergradpercent){
        //condition = condition2;
        float greScore = Float.valueOf(grescore);
        float undergradPercent = Float.valueOf(undergradpercent);
               float greRating = getGreRating(greScore);
        float undergradStudRating = getUndergradStudRating(undergradPercent);

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UnivDetail");
        query1.whereEqualTo("univName", undergradUniv);
        query1.getFirstInBackground(new GetCallback<ParseObject>() {
            //@Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject != null) {
                    //tempRating = Integer.parseInt(parseObject.get("univRating").toString());
                    String temp = parseObject.get("univRating").toString();
                    tempRating = Float.valueOf(temp);
                    //Log.e(TAG, "rating is **** " + tempRating);
                    undergradUnivRating = tempRating;
                    //Log.e(TAG, "greRating: " + GreRating + ",   undergradUnivRating: " + undergradUnivRating + ",  undergradStudRating: " + UndergradStudRating);
                    totalUserRating = getThisUserAvgRatings(greRating, undergradUnivRating, undergradStudRating);
                    //Log.e("LOG", "TOTAL RATING+++" + totalUserRating);


                    /*if(condition == 1){
                        getQuery2();
                    } */

              /*  } else {
                    Log.e(TAG, "exception = ^^^^^^^" + e);
                }
            }
        });
        
        return totalUserRating;
} */
      //float getThisUserTotalAvgRating{
          //condition = condition2;
          //float greScore = Float.valueOf(grescore);
          //float undergradPercent = Float.valueOf(undergradpercent);
          //float greRating = getGreRating(greScore);
          //float undergradStudRating = getUndergradStudRating(undergradPercent);
        /*void getUserUnivRating(){
          ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UnivDetail");
          query1.whereEqualTo("univName", undergradUniv);
          query1.getFirstInBackground(new GetCallback<ParseObject>() {
              //@Override
              public void done(ParseObject parseObject, ParseException e) {
                  if (parseObject != null) {
                      //float underGradUnivRating;
                      //tempRating = Integer.parseInt(parseObject.get("univRating").toString());
                      String temp = parseObject.get("univRating").toString();
                     // tempRating = Float.valueOf(temp);
                      //Log.e(TAG, "rating is **** " + tempRating);
                      //UndergradUnivRating = Float.valueOf(temp);
                      //Log.e(TAG, "greRating: " + GreRating + ",   undergradUnivRating: " + undergradUnivRating + ",  undergradStudRating: " + UndergradStudRating);
                     // totalUserRating = getThisUserAvgRatings(greRating, underGradUnivRating, undergradStudRating);
                      //Log.e("LOG", "TOTAL RATING+++" + totalUserRating);


            /*if(condition == 1){
                getQuery2();
            } */

                /*  } else {
                      Log.e(TAG, "exception = ^^^^^^^" + e);
                  }
              }
          });

          //return totalUserRating;
      } */

    void getQuery2(){

        Intent intent = getIntent();
        // 4. get bundle from intent
        Bundle bundle = intent.getExtras();
        //String message = intent.getStringExtra("mPressureGroup");
        // 5. get status value from bundle
        String fullname = bundle.getString("fullname");
        Log.e(TAG,"fullname: "+fullname);

        ParseQuery<ParseObject> query2;
        query2 = ParseQuery.getQuery("UserInfo");
        query2.whereNotEqualTo("newUnivName", "blank");
        query2.whereNotEqualTo("fullName", fullname);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                //seniorStudSize = list.size();
                Log.e(TAG, "list size: " + list.size());
                seniorStuds = new String[list.size()];
                studAvgRating = new float[list.size()];
                i = -1;
                //avgSeniorUserRating = 0;
                String[] underUniv = new String[list.size()];
                GreRating2 = new float[list.size()];
                UndergradStudRating2 = new float[list.size()];
                for (ParseObject po : list) {
                    i++;
                    //Log.e(TAG, "i :: " + i);
                    seniorStuds[i] = po.get("fullName").toString();
                    //getThisUserData(seniorStuds[i]);
                    String thisSenior = seniorStuds[i];
                    Log.e(TAG, "This Senior: " + thisSenior);
                    //  ParseQuery query5 = ParseQuery.getQuery("UserInfo");
                    // query5.whereEqualTo("fullName", thisSenior);
                    //query5.getFirstInBackground(new GetCallback() {
                    //  @Override
                    //public void done(ParseObject parseObject, ParseException e) {

                    //getThisUserTwoRatings(po);
                    underUniv[i] = po.get("undergradUniv").toString();
                    Log.e(TAG, "undergradUniv: " + underUniv[i]);
                    float GreScore2, UndergradPercent2;
                    GreScore2 = Float.valueOf(po.get("greScore").toString());
                    UndergradPercent2 = Float.valueOf(po.get("undergradPercent").toString());

                    GreRating2[i] = getGreRating(GreScore2);
                    UndergradStudRating2[i] = getUndergradStudRating(UndergradPercent2);
                    //Log.e(TAG, "greRating2: " + GreRating2 + ",  undergradStudRating2: " + UndergradStudRating2);


                    // String fullname = parseObject.get("fullName").toString();
                   /* String undergradUniv = po.get("undergradUniv").toString();
                    String grescore = po.get("greScore").toString();
                    String undergradpercent = po.get("undergradPercent").toString();  */
                    //condition = 0;
                    //  float getTotalUserRating = getThisUserTotalAvgRating(thisSenior, undergradUniv, grescore, undergradpercent);
                    //float thisStudTotalRating =totalUserRating;
                   /* getUserUnivRating();
                    float getCurrentUserTotalRating = getThisUserAvgRatings(GreRating, UndergradUnivRating, UndergradStudRating);
                    Log.e(TAG, "This User's total rating: " + getCurrentUserTotalRating); */

                }
                //for(int j= 0; j<list.size();j++){
                //float univRating = getUnivRating(underUniv[j]);
                //Log.e(TAG,"again univ rating: "+univRating);
                   /* ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UnivDetail");
                    query3.whereEqualTo("univName", underUniv[j]);
                    query3.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            //fullname = parseObject.get("fullName").toString();
                            //undergradUniv2 = parseObject.get("undergradUniv").toString();
                            if (parseObject != null) {
                                String temp = parseObject.get("univRating").toString();
                                //Log.e(TAG, "rating is **** " + tempRating);
                                UndergradUnivRating2[i] = Float.valueOf(temp);   */
                univRating = new String[list.size()];
                UndergradUnivRating2 = new float[list.size()];
                for (int j = 0; j < list.size(); j++) {
                    Intent intent2 = getIntent();
                    // 4. get bundle from intent
                    Bundle bundle = intent2.getExtras();
                    //String message = intent.getStringExtra("mPressureGroup");
                    // 5. get status value from bundle
                    univRating[j] = bundle.getString("univRating[" + j + "]");
                   // Log.e(TAG,"univ rating["+j+"]"+univRating[j]);
                    //UndergradUnivRating2[j] = Float.valueOf(univRating[j]);


                    /*Log.e(TAG, "greRating" + j + "]: " + GreRating2[j] + ",   undergradUnivRating" + j + "]: " + UndergradUnivRating2[j] + ",  undergradStudRating"+j+"]: "+ UndergradStudRating2[j]);
                    float getThisUserTotalRating = getThisUserAvgRatings(GreRating2[j], UndergradUnivRating2[j], UndergradStudRating2[j]);
                    Log.e(TAG, "This Student " + i + "'s total rating: " + getThisUserTotalRating); */
                }
            }
        });

    }

                            /*@Override
                            public void done(Object o, Throwable throwable) {

                            }*/

               // Log.e(TAG, "Senior Students");
                //for (int j = 0; j < list.size(); j++) {
                    //Log.e(TAG, "" + j + ": " + seniorStuds[j]);
                    // Log.e(TAG, "" + j + ": " + studAvgRating[j]);
                    // userSimilarities(currentuser, seniorStuds[j]);
                //}
            //}
       // });
   // }

    /*float getUnivRating(String undergradUniv2) {

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UnivDetail");
        query3.whereEqualTo("univName", undergradUniv2);
        query3.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                //fullname = parseObject.get("fullName").toString();
                //undergradUniv2 = parseObject.get("undergradUniv").toString();
                if (parseObject != null) {
                    String temp = parseObject.get("univRating").toString();
                    //Log.e(TAG, "rating is **** " + tempRating);
                    UndergradUnivRating2[i] = Float.valueOf(temp);

                    // Log.e(TAG, "greRating2: " + GreRating2 + ",   undergradUnivRating2: " + UndergradUnivRating2 + ",  undergradStudRating2: " + UndergradStudRating2);
                    float getThisUserTotalRating = getThisUserAvgRatings(GreRating2, UndergradUnivRating2, UndergradStudRating2);
                    Log.e(TAG, "This Student " + i + "'s total rating: " + getThisUserTotalRating);
                } else {
                    Log.e(TAG, "exception $$$$$$  :" + e);
                }
            }
        });
        Log.e(TAG,"undergrad university rating: "+UndergradUnivRating2);
        return UndergradUnivRating2;
    }*/

    /*void getThisUserData(String thisSenior){
        ParseQuery query5 = ParseQuery.getQuery("UserInfo");
        query5.whereEqualTo("fullName", thisSenior);
        query5.getFirstInBackground(new GetCallback() {
            @Override
            public void done(ParseObject parseObject3, ParseException e) {
                getThisUserAllRatings(parseObject3);

               String fullname = parseObject3.get("fullName").toString();
               String undergradUniv = parseObject3.get("undergradUniv").toString();
                String  grescore = parseObject3.get("greScore").toString();
                String undergradpercent = parseObject3.get("undergradPercent").toString();
                //condition = 0;
                float getTotalUserRating = getThisUserTotalAvgRating(fullname,undergradUniv,grescore, undergradpercent );
                //float thisStudTotalRating =totalUserRating;
                Log.e(TAG, "This User's total rating: " + getTotalUserRating);


            }

            /*@Override
            public void done(Object o, Throwable throwable) {

            }*/
      /*  });

    } */



    float getThisUserAvgRatings(float greRating,float undergradUnivRating,float undergradStudRating){
        float totalUserRating;
        float sum = greRating+undergradStudRating+undergradUnivRating;
        float div = sum/3;
        totalUserRating = div;
        return totalUserRating;
    }



    void Recommend(float greRating, float undergradUnivRating ,float undergradStudRating)
    {

    }

  /*  String getCurrentUsers(String currentuser){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInfo");
        query.whereNotEqualTo("newUnivName","blank");
        query.whereNotEqualTo("fullName",currentuser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

            }
        });

        return ;
    } */

    float userSimilarities(String currentUser, String seniorStud){

        //float avg

        return 0;
    }


    // @Override
    //protected void onResume() {
      //  super.onResume();
    void suggestDB(String grescore, String Percentage){
        float greScore = Float.valueOf(grescore);
        float percentage = Float.valueOf(Percentage);
                ParseUser mCurrentUser3 = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");
        //Log.e("TAG","SCORE+++++ "+greScore+" ,,, PERCENT ++++ "+percentage);
        query3.whereLessThanOrEqualTo("greScore", greScore+7);
        query3.whereGreaterThanOrEqualTo("greScore", greScore-15);
        query3.whereLessThanOrEqualTo("undergradPercent", percentage + 15);
        query3.whereGreaterThanOrEqualTo("undergradPercent", percentage-15);

        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    listview = (ListView) findViewById(R.id.listView);
                    //  String[] usernames = new String[obs.size()];
                    ArrayList<String> univnames = new ArrayList<String>();
                    int size = 30;
                    String[] arr = new String[size];
                    int i = 0, repeat;
                    for (ParseObject b : list) {
                        //usernames[i] = a.get("fullName").toString();
                        String abc = b.get("newUnivName").toString();
                        if (i <= 30) {
                            arr[i] = abc;
                        }
                        repeat = 0;
                        for (int j = 0; j <= i; j++) {
                            if (abc.equals(arr[j])) {
                                repeat++;
                                if (repeat > 1) {
                                   // Log.e("LOG","repeat now > 1 with value: "+repeat);
                                    break;
                                }
                            }
                        }
                        if (!abc.equals("blank") && repeat == 1) {
                            univnames.add(abc);
                            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivActivity.this,
                            //      android.R.layout.simple_list_item_1, univnames);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, univnames);
                            //android.R.layout.simple_selectable_list_item, usernames);
                            listview.setAdapter(adapter);
                        }
                        i++;
                    }
                } else {
                    //System.out.println(" e is not null");
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuggestUnivActivity.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_users, menu);
        return true;
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


}
