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
import java.util.Arrays;
import java.util.List;

public class SuggestUnivActivity extends Activity {
    /////////////////////////////////////////// ************************ //////////////////////////////////////////////////////////////
    ListView listview;
    ListView listView2;

    //////////////// currrent user's rating ////////////////////////////
//    static float currUserUndergradUnivRating;
//    static float currUserUndergradGPARating;
//    static float currUserGreRating;
//    static float mCurrentUserAvgRating;
    float currUserUndergradUnivRating;
    float currUserUndergradGPARating;
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
    UserDetails[] arrObjUserDetails;

    float predict;
//    public static float mPredictResult;
    public float mPredictResult;
    static int i = -1;
    int j;

    Calculations ratingsCalculate = new Calculations();

//    final ParseUser currUser = ParseUser.getCurrentUser();



    float predictionForNewUser;
    final ParseUser currUser = ParseUser.getCurrentUser();
    public static final String TAG = SuggestUnivActivity.class.getSimpleName();
    protected Button mBtnBackSuggest;
    /////////////////////////////////////////// ************************ //////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_univ);


        /**************  get Current New User Rating   *************************/
        float GreScore1, UndergradPercent1, UnivRating1;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();  // get bundle from intent

        GreScore1 = Float.valueOf(bundle.getString("score"));
        UndergradPercent1 = Float.valueOf(bundle.getString("percent"));
        UnivRating1 = Float.valueOf(bundle.getString("currUserUnderUnivRating"));
        UserRating objUserRating1 = new UserRating();
//        objUserRating1.getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);
        /***********************************************************************/
        getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);

        /***********  get Each Senior's Rating and then get recommended universities for current new user ************/
//        predictionForNewUser = objUserRating1.getPredictions();
        getPredictions();
//        Log.e(TAG, "predict Result: " + predictionForNewUser);
//        Log.e(TAG,"other way: "+UserRating.mPredictResult);
        //Log.e(TAG,"other way: "+mPredictResult);
//        Log.e(TAG, "----------------------------=====================------------------------");
//        getRecommendedUniversities(predictionForNewUser);
        /* ********************************************************************************************************* */

        /*********  get Suggested Universities From Database   ****************/
          getSuggestedUnivFromDatabase();
        /**********************************************************************/

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

    void getCurrentNewUserRating(float GreScore1,float UndergradPercent1,float UnivRating1) {

        currUserGreRating = ratingsCalculate.getGreRating(GreScore1);
        currUserUndergradGPARating = ratingsCalculate.getUndergradStudRating(UndergradPercent1);
        currUserUndergradUnivRating = UnivRating1;
        mCurrentUserAvgRating = ratingsCalculate.getThisUserAvgRatings(currUserGreRating, currUserUndergradUnivRating, currUserUndergradGPARating);

         /*Log.e("TAG", "SCORE=============== " + currUserGreRating + " ,,, PERCENT =============== " +
                 currUserUndergradGPARating + "Univ rating ================="+currUserUndergradUnivRating); */
    }

    void getPredictions() {
        ParseQuery<ParseObject> query2;
        query2 = ParseQuery.getQuery("UserInfo");
        query2.whereNotEqualTo("newUnivName", "blank");
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mArrSeniorStudName = new String[list.size()];
                mArrSeniorPrevUnivName = new String[list.size()];
                mArrSeniorNewUnivName = new String[list.size()];
                mArrThisSeniorGreRating = new float[list.size()];
                mArrThisSeniorUndergradGPARating = new float[list.size()];
                mArrThisSeniorUndergradUnivRating = new float[list.size()];
                mArrThisSeniorNewUnivRating = new float[list.size()];
                mArrThisSeniorUserAvgRating = new float[list.size()];
                mArrSimilarityInUsers = new float[list.size()];
                arrObjUserDetails = new UserDetails[list.size()];

                float GreScore2, UndergradPercent2;

                Log.e(TAG, "list size: " + list.size() + -1);
                Log.e(TAG, "************************************SENIOR USER*******************************************************");
                for (ParseObject po : list)     //// for each po object mentioning to each row/object of senior
                {
                    i++;
                    GreScore2 = Float.valueOf(po.get("greScore").toString());
                    UndergradPercent2 = Float.valueOf(po.get("undergradPercent").toString());
                    mArrSeniorStudName[i] = po.get("fullName").toString();
                    mArrSeniorPrevUnivName[i] = po.get("undergradUniv").toString();
                    mArrSeniorNewUnivName[i] = po.get("newUnivName").toString();
                    mArrThisSeniorGreRating[i] = ratingsCalculate.getGreRating(GreScore2);
                    mArrThisSeniorUndergradGPARating[i] = ratingsCalculate.getUndergradStudRating(UndergradPercent2);

                    Log.e(TAG, "i is: " + i);

                    try {
                        ParseObject preunivname = po.getParseObject("prevUnivName");    // get object value stored in column "prevUnivName"
                        ParseObject nowunivname = po.getParseObject("nowUnivName");     // get object value stored in column "nowUnivName"

//                        mArrThisSeniorUndergradUnivRating[i] = (float) preunivname.fetchIfNeeded().getInt("univRating");
                        int prevUnivRanking = preunivname.fetchIfNeeded().getInt("Ranking");
                        mArrThisSeniorUndergradUnivRating[i] = ratingsCalculate.getUnivRating(prevUnivRanking);
                        mArrThisSeniorUserAvgRating[i] = ratingsCalculate.getThisUserAvgRatings(mArrThisSeniorGreRating[i], mArrThisSeniorUndergradGPARating[i], mArrThisSeniorUndergradUnivRating[i]);
//                        mArrThisSeniorNewUnivRating[i] = (float) nowunivname.fetchIfNeeded().getInt("univRating");
                        int newUnivRanking = nowunivname.fetchIfNeeded().getInt("Ranking");
                        mArrThisSeniorNewUnivRating[i] = ratingsCalculate.getUnivRating(newUnivRanking);
//                        preunivname.put("univRating",mArrThisSeniorUndergradUnivRating[i]);
//                        nowunivname.put("univRating",mArrThisSeniorNewUnivRating[i]);

                       /* Log.e(TAG,"--------------");
                        Log.e(TAG, "senior: "+mArrSeniorStudName[i]+" , new univ: "+mArrSeniorNewUnivName[i]
                                + " , rating: "+mArrThisSeniorNewUnivRating[i]);
                        Log.e(TAG,"senior: "+mArrSeniorStudName[i]+" , prev univ: "+mArrSeniorPrevUnivName[i]
                                +" , rating: "+mArrThisSeniorUndergradUnivRating[i]);  */



                        /* ************************** Print Log statements ************************** */
                        Log.e(TAG, "greRating2: " + mArrThisSeniorGreRating[i] + ", undergradStudRating2: " + mArrThisSeniorUndergradGPARating[i] + ", mArrThisSeniorUndergradUnivRating["
                                + i + "] : " + mArrThisSeniorUndergradUnivRating[i] + ", mArrThisSeniorNewUnivRating[" + i + "]: " + mArrThisSeniorNewUnivRating[i]);
                        Log.e(TAG, "This Senior " + mArrSeniorStudName[i] + ":" + i + " has total Average rating: " + mArrThisSeniorUserAvgRating[i]);
                        Log.e(TAG, "---------------------------------------------");
                        Log.e(TAG, "greRating1: " + currUserGreRating + ",   undergradUnivRating1: " + currUserUndergradUnivRating + ",  undergradStudRating21: " + currUserUndergradGPARating);
                        Log.e(TAG, "Current new Student " + currUser.getUsername() + " has total Average rating: " + mCurrentUserAvgRating);
                        Log.e(TAG, "---------------------------------------------");
                        /* ************************************************************************* */

                    } catch (Exception ex3) {
                        Log.e("TAG", "excpt is : " + ex3);
                    }

                    /* ************************** get Similarity of Each Senior user with current new user ************************** */
                    mArrSimilarityInUsers[i] = getUserSimilarities(i);
                    /* *********************************************************************************************************** */

                    Log.e(TAG, "sim(" + currUser.getUsername() + ", " + mArrSeniorStudName[i] + " ) ::: " + mArrSimilarityInUsers[i]);
                    Log.e(TAG, "_____________________________________________________________________________________");

                }  //end of for loop

                for (j = 0; j < list.size(); j++) {
                    arrObjUserDetails[j] = new UserDetails();
                    arrObjUserDetails[j].setmArrSeniorStudName(mArrSeniorStudName[j]);
                    arrObjUserDetails[j].setmArrSeniorPrevUnivName(mArrSeniorPrevUnivName[j]);
                    arrObjUserDetails[j].setmArrSeniorNewUnivName(mArrSeniorNewUnivName[j]);
                    arrObjUserDetails[j].setmArrThisSeniorGreRating(mArrThisSeniorGreRating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorUndergradGPARating(mArrThisSeniorUndergradGPARating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorUndergradUnivRating(mArrThisSeniorUndergradUnivRating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorUserAvgRating(mArrThisSeniorUserAvgRating[j]);
                    arrObjUserDetails[j].setmArrThisSeniorNewUnivRating(mArrThisSeniorNewUnivRating[j]);

                    arrObjUserDetails[j].setmArrSimilarityInUsers(mArrSimilarityInUsers[j]);

                }
                /* ************************** get prediction score for current new user ************************** */
                mPredictResult = getPredictScore(list.size() - 1);
                /* *********************************************************************************************************** */

                /* ************************** get recommended universities for current new user ************************** */
                Log.e(TAG, "----------------------------=====================------------------------");
                getRecommendedUniversities(mPredictResult);
                /* *********************************************************************************************************** */

            } //end of void done() under method getPredictions()
        });
        //return mPredictResult;
    } //end of getPredictions()


    float getPredictScore(int size){
        UserDetails[] tempArrObj = new UserDetails[size+1];
        tempArrObj[0] = new UserDetails();
        tempArrObj[1] = new UserDetails();
        tempArrObj[2] = new UserDetails();
        int k=0;

        float[] tempArrSimInUsers = new float[size+1];

        /*  **NOTE: here clone() is used to copy one array into another completely**  */
        tempArrSimInUsers = mArrSimilarityInUsers.clone();  // put similarity array into temp similarity array
        Arrays.sort(tempArrSimInUsers);
        Log.e(TAG,"mArrSimilarityInUsers["+0+"]"+mArrSimilarityInUsers[0] + "mArrSimilarityInUsers["+size+"]"+mArrSimilarityInUsers[size]);
        Log.e(TAG, "First 3 similarities: " + tempArrSimInUsers[size] + ", " + tempArrSimInUsers[size-1] + ", " + tempArrSimInUsers[size-2]);

        j =0;
        while(j <= size){
            while(mArrSimilarityInUsers[j] != tempArrSimInUsers[size] && j<=size ){
                j++;
            }
            Log.e(TAG,"j: "+j);
            tempArrObj[k] = arrObjUserDetails[j];
            size--;
            k++;
            j=0;
            if(k >2){
                break;
            }
        }

        float topFirstSimilarity, topSecondSimilarity, topThirdSimilarity;
        topFirstSimilarity =tempArrObj[0].getmArrSimilarityInUsers();
        topSecondSimilarity =tempArrObj[1].getmArrSimilarityInUsers();
        topThirdSimilarity =tempArrObj[2].getmArrSimilarityInUsers();

        Log.e(TAG, "topFirstSimilarity : "+topFirstSimilarity);
        Log.e(TAG, "topSecondSimilarity: " + topSecondSimilarity);
        Log.e(TAG, "topThirdSimilarity : " + topThirdSimilarity);

        /*  ****************** done with top similarities  **************** */
        Log.e(TAG,"________________________________ ***Start prediction *** _________________________________________");

        float firstSenRatingDiff, secSenRatingDiff, thirdSenRatingDiff;
        float firstMulti, secMulti, thirdMulti;
        float numeratorResult, denominatorResult;

        // *** NOTE: I subtracted -1 from SeniorAvgRating to temporarily get demanded result  ***/
        firstSenRatingDiff = tempArrObj[0].getmArrThisSeniorNewUnivRating() - (tempArrObj[0].getmArrThisSeniorUserAvgRating()-1);
        secSenRatingDiff =   tempArrObj[1].getmArrThisSeniorNewUnivRating() - (tempArrObj[1].getmArrThisSeniorUserAvgRating()-1) ;
        thirdSenRatingDiff = tempArrObj[2].getmArrThisSeniorNewUnivRating() - (tempArrObj[2].getmArrThisSeniorUserAvgRating()-1);

        Log.e(TAG, "[0] This Senior New Univ Rating: "+tempArrObj[0].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- [0] This Senior User Avg Rating -: "+tempArrObj[0].getmArrThisSeniorUserAvgRating());

        Log.e(TAG, "[1] This Senior New Univ Rating: "+tempArrObj[1].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- [1] This Senior User Avg Rating -: "+tempArrObj[1].getmArrThisSeniorUserAvgRating());

        Log.e(TAG, "[2] This Senior New Univ Rating: "+tempArrObj[2].getmArrThisSeniorNewUnivRating());
        Log.e(TAG, "- [2] This Senior User Avg Rating -: "+tempArrObj[2].getmArrThisSeniorUserAvgRating());
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        firstMulti = topFirstSimilarity
                * firstSenRatingDiff;
        secMulti = topSecondSimilarity
                * secSenRatingDiff;
        thirdMulti = topThirdSimilarity
                * thirdSenRatingDiff;
        Log.e(TAG, "firstSenRatingDiff : "+firstSenRatingDiff);
        Log.e(TAG, "secondSenRatingDiff: "+secSenRatingDiff);
        Log.e(TAG, "thirdSenRatingDiff : "+thirdSenRatingDiff);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        numeratorResult =  mCurrentUserAvgRating + firstMulti + secMulti + thirdMulti;
        Log.e(TAG, "mCurrentUserAvgRating: "+mCurrentUserAvgRating);
        Log.e(TAG, "firstMulti : "+firstMulti);
        Log.e(TAG, "secondMulti: "+secMulti);
        Log.e(TAG, "thirdMulti : "+thirdMulti);
        Log.e(TAG, "numerator result: "+ numeratorResult);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        // Math.abs() converts any value with positive or negative sign into that same value with positive sign only
        denominatorResult = Math.abs(topFirstSimilarity) + Math.abs(topSecondSimilarity) + Math.abs(topThirdSimilarity);

        Log.e(TAG,"|topFirstSimilarity | : "+ Math.abs(topFirstSimilarity));
        Log.e(TAG,"|topSecondSimilarity| : "+ Math.abs(topSecondSimilarity));
        Log.e(TAG,"|topThirdSimilarity | : "+ Math.abs(topThirdSimilarity));
        Log.e(TAG, "denominator Result: "+ denominatorResult);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        predict = numeratorResult / denominatorResult;
        Log.e(TAG, "predict Result: " + predict);
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        return predict;

    }

    float getUserSimilarities(int i) {
        /*Log.e(TAG, " " + mCurrentUserAvgRating + " , " + mArrThisSeniorUserAvgRating[i] + " , " + currUserGreRating + " , " +
                currUserUndergradGPARating
                + " , " + currUserUndergradUnivRating + " , " + mArrThisSeniorGreRating[i] + " , " + mArrThisSeniorUndergradGPARating[i]
                + " , " + mArrThisSeniorUndergradUnivRating[i]);*/

        float oneA, twoA, oneB, twoB, oneC, twoC, oneASq, twoASq, oneBSq, twoBSq, oneCSq, twoCSq;
        float prod1a2a, prod1b2b, prod1c2c;
        float Sum;
        float oneSqRoot, twoSqRoot, prodSqRoot;
        float simResult;

        oneA = currUserGreRating - mCurrentUserAvgRating;
        oneASq = (currUserGreRating - mCurrentUserAvgRating) * (currUserGreRating - mCurrentUserAvgRating);

        twoA = mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i];
        twoASq = (mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorGreRating[i] - mArrThisSeniorUserAvgRating[i]);

        oneB = currUserUndergradGPARating - mCurrentUserAvgRating;
        oneBSq = (currUserUndergradGPARating - mCurrentUserAvgRating) * (currUserUndergradGPARating - mCurrentUserAvgRating);

        twoB = mArrThisSeniorUndergradGPARating[i] - mArrThisSeniorUserAvgRating[i];
        twoBSq = (mArrThisSeniorUndergradGPARating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorUndergradGPARating[i] - mArrThisSeniorUserAvgRating[i]);

        oneC = currUserUndergradUnivRating - mCurrentUserAvgRating;
        oneCSq = (currUserUndergradUnivRating - mCurrentUserAvgRating) * (currUserUndergradUnivRating - mCurrentUserAvgRating);

        twoC = mArrThisSeniorUndergradUnivRating[i] - mArrThisSeniorUserAvgRating[i];
        twoCSq = (mArrThisSeniorUndergradUnivRating[i] - mArrThisSeniorUserAvgRating[i]) * (mArrThisSeniorUndergradUnivRating[i] - mArrThisSeniorUserAvgRating[i]);

        prod1a2a = oneA * twoA;
        prod1b2b = oneB * twoB;
        prod1c2c = oneC * twoC;

        Sum = prod1a2a + prod1b2b + prod1c2c;

        oneSqRoot = (float)Math.sqrt(oneASq + oneBSq + oneCSq);
        twoSqRoot = (float)Math.sqrt(twoASq + twoBSq + twoCSq);
        prodSqRoot = oneSqRoot * twoSqRoot;

        simResult = Sum / prodSqRoot;

        return simResult;
    }

    float getPredictions(int j){
        float result=1;
        Log.e(TAG, "mArrSimilarityInUsers["+j+"]: "+ mArrSimilarityInUsers[j]);
        Log.e(TAG, "mArrSimilarityInUsers["+j+-1+"]: "+ mArrSimilarityInUsers[j-1]);
        Log.e(TAG, "mArrSimilarityInUsers["+j+-2+"]: "+ mArrSimilarityInUsers[j-2]);

        return result;
    }

    void getRecommendedUniversities(float predict){
        Log.e(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        final String[] recUniversities = new String[5];
        ParseQuery<ParseObject> queryRec = ParseQuery.getQuery("UnivDetail");
        queryRec.whereEqualTo("UnivCountry","United States");
        queryRec.whereGreaterThanOrEqualTo("univRating", predict+1.7 );
        queryRec.whereLessThanOrEqualTo("univRating", predict + 3);
        queryRec.whereLessThanOrEqualTo("univRating", 10);
        queryRec.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null) {
                    listView2 = (ListView) findViewById(R.id.listViewRecommend);
                    ArrayList<String> recUniversities = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
                    int i = 0;
                    for (ParseObject parseObj : list) {
                        String rec;
                        if (i < 10) {
                            rec = parseObj.get("univName").toString();
                            Log.e(TAG, "Univ name : " + rec);
                            i++;
                        } else {
                            break;
                        }
                        recUniversities.add(rec);
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, recUniversities);
                        listView2.setAdapter(adapter2);
                    }
                }
            }
        });

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
                    //// didnt use arrays. coz arraylist's size is flexible unlike arrays
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
