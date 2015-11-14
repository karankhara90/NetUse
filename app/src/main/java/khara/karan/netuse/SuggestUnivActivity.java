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
    /////////////////////////////////////////// ************************ //////////////////////////////////////////////////////////////
    ListView listview;
    //////////////// currrent user's rating ////////////////////////////
    float currUserUndergradUnivRating;
    float currUserUndergradPercentRating;
    float currUserGreRating;
    float mCurrentUserAvgRating;

    //////////////// Senior user's rating and similarity array ////////////////////
    float[] mArrThisSeniorUndergradUnivRating;
    float[] mArrThisSeniorNewUnivRating;
    float[] mArrThisSeniorUndergradGPARating;
    float[] mArrThisSeniorGreRating;
    float[] mArrThisSeniorUserAvgRating;
    float[] mArrSimilarityInUsers;

    /////////////// Senior user's name and university arrays //////////////////
    String[] mArrSeniorStudName;
    String[] mArrSeniorPrevUnivName;
    String[] mArrSeniorNewUnivName;


    int i;

    final ParseUser currUser = ParseUser.getCurrentUser();
    public static final String TAG = SuggestUnivActivity.class.getSimpleName();
    protected Button mBtnBackSuggest;
    /////////////////////////////////////////// ************************ //////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_univ);


        /**************  get Current New User Rating   *********************/
        float GreScore1, UndergradPercent1, UnivRating1;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();  // get bundle from intent

        GreScore1 = Float.valueOf(bundle.getString("score"));
        UndergradPercent1 = Float.valueOf(bundle.getString("percent"));
        UnivRating1 = Float.valueOf(bundle.getString("currUserUnderUnivRating"));
        UserInfo objUserInfo1 = new UserInfo();
        objUserInfo1.getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);
        /*****************************************************************/

        /*********  get Suggested Universities From Database   ************/
        getSuggestedUnivFromDatabase();
        /*****************************************************************/

        /**************  get Each Senior's Rating   *********************/
        Log.e(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        UserInfo objUserInfo2 = new UserInfo();
        objUserInfo2.getEachSeniorRating();
        /*************************************************************/

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

    float getGreRating(float greScore) {
        float greRating;
        float temp = greScore - 240;
        greRating = temp / 10;
        return greRating;
    }

    float getUndergradStudRating(float undergradPercent) {
        undergradPercent = undergradPercent / 10;
        return undergradPercent;
    }

    void getSuggestedUnivFromDatabase() {

        ParseQuery<ParseObject> q = ParseQuery.getQuery("UserInfo");
        q.whereEqualTo("userId", currUser);
        q.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {

                String gre_score = parseObject.get("greScore").toString();
                String under_percent = parseObject.get("undergradPercent").toString();

                suggestDB(gre_score, under_percent);
            }
        });
    }
    //Intent intent = getIntent();
    Intent intent = getIntent();



    void Recommend(float greRating, float undergradUnivRating, float undergradStudRating) {

    }

    void suggestDB(String grescore, String Percentage) {
        float greScore = Float.valueOf(grescore);
        float percentage = Float.valueOf(Percentage);
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");

        query3.whereLessThanOrEqualTo("greScore", greScore + 7);
        query3.whereGreaterThanOrEqualTo("greScore", greScore - 15);
        query3.whereLessThanOrEqualTo("undergradPercent", percentage + 15);
        query3.whereGreaterThanOrEqualTo("undergradPercent", percentage - 15);

        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    listview = (ListView) findViewById(R.id.listViewSuggest);
                    ArrayList<String> univnames = new ArrayList<String>();  // didn't use String[] usernames = new String[obs.size()]; Instead used arraylist
                    int size = 30;
                    String[] arr = new String[size];
                    int i = 0, repeat;
                    for (ParseObject b : list) {
                        String abc = b.get("newUnivName").toString();
                        if (i <= 30) {
                            arr[i] = abc; }
                        repeat = 0;
                        for (int j = 0; j <= i; j++) {
                            if (abc.equals(arr[j])) {
                                repeat++;
                                if (repeat > 1) {
                                    break; }
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
