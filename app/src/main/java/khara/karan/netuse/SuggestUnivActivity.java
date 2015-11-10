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
//    public static

    String[] seniorStuds;
    float[] studAvgRating;
    float avgSeniorUserRating;
    ParseUser currUser;
    int i,j;
    String[] univRating;
    Object object;
    int listSize;
    String rate;


    public static final String TAG=SuggestUnivActivity.class.getSimpleName();
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

                undergradUniv1 = parseObject.get("undergradUniv").toString();

                float GreScore1, UndergradPercent1;
                GreScore1 = Float.valueOf(parseObject.get("greScore").toString());
                UndergradPercent1 = Float.valueOf(parseObject.get("undergradPercent").toString());
                suggestDB(parseObject.get("greScore").toString(), parseObject.get("undergradPercent").toString());


                GreRating1 = getGreRating(GreScore1);
                UndergradStudRating1 = getUndergradStudRating(UndergradPercent1);

                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UnivDetail");
                query1.whereEqualTo("univName", undergradUniv1);
                query1.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if (parseObject != null) {
                            String temp = parseObject.get("univRating").toString();

                            UndergradUnivRating1 = Float.valueOf(temp);
                            Log.e(TAG, "greRating1: " + GreRating1 + ",   undergradUnivRating1: " + UndergradUnivRating1 + ",  undergradStudRating21: " + UndergradStudRating1);
                            float getCurrentUserTotalRating = getThisUserAvgRatings(GreRating1, UndergradUnivRating1, UndergradStudRating1);
                            Log.e(TAG, "Current Student's total rating: " + getCurrentUserTotalRating);

                        } else {
                            Log.e(TAG, "exception = ^^^^^^^" + e);
                        }
                    }
                });

            }
        });
        //**************  getEachSeniorRating()   *********************/

        getEachSeniorRating();

        //*************************************************/

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

    void getEachSeniorRating(){

        ParseQuery<ParseObject> query2;
        query2 = ParseQuery.getQuery("UserInfo");
        query2.whereNotEqualTo("newUnivName", "blank");
        query2.whereNotEqualTo("fullName", fullname);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                Log.e(TAG, "list size: " + list.size());
                seniorStuds = new String[list.size()];
                studAvgRating = new float[list.size()];
                i = -1;
                listSize = list.size();

                String[] underUniv = new String[list.size()];
                GreRating2 = new float[list.size()];
                UndergradStudRating2 = new float[list.size()];
                UndergradUnivRating2 = new float[list.size()];
                for (ParseObject po : list) {
                    i++;
                    seniorStuds[i] = po.get("fullName").toString();
                    String thisSenior = seniorStuds[i];
                    Log.e(TAG, "This Senior: " + thisSenior);

                    underUniv[i] = po.get("undergradUniv").toString();
                    Log.e(TAG, "undergradUniv: " + underUniv[i]);
                    float GreScore2, UndergradPercent2;
                    GreScore2 = Float.valueOf(po.get("greScore").toString());
                    UndergradPercent2 = Float.valueOf(po.get("undergradPercent").toString());

                    GreRating2[i] = getGreRating(GreScore2);
                    UndergradStudRating2[i] = getUndergradStudRating(UndergradPercent2);

                    try{
                        ParseObject preunivname = po.getParseObject("prevUnivName");
                        Log.e(TAG, "obj id ==== " + preunivname.getObjectId());
                        UndergradUnivRating2[i] = (float)preunivname.fetchIfNeeded().getInt("univRating");
                        Log.e(TAG, "greRating2: " + GreRating2[i] + ",  undergradStudRating2: " +
                                UndergradStudRating2[i]+", UndergradUnivRating2["+i+"] : " + UndergradUnivRating2[i]);
                    }catch (Exception excpt){
                        Log.e("TAG","excpt is : "+excpt);
                    }


                    Log.e(TAG, "_____________________________________________________________________");
                }
            }
        });
        ///////////////////////////////////////
    }

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


    float userSimilarities(String currentUser, String seniorStud){

        //float avg

        return 0;
    }

    void suggestDB(String grescore, String Percentage){
        float greScore = Float.valueOf(grescore);
        float percentage = Float.valueOf(Percentage);
        ParseUser mCurrentUser3 = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");

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
                                    break;
                                }
                            }
                        }
                        if (!abc.equals("blank") && repeat == 1) {
                            univnames.add(abc);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, univnames);
                            listview.setAdapter(adapter);
                        }
                        i++;
                    }
                } else {
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
