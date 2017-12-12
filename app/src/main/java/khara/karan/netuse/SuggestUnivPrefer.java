package khara.karan.netuse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestUnivPrefer extends AppCompatActivity {
    protected ListView listview;
    protected  ListView listView2,listView3,listView4;
    protected TextView txtViewUnivType;
    //protected TextView txtViewUnivStates;

    //////////////// currrent user's rating ////////////////////////////
//    static float mCurrUserUndergradUnivRating;
//    static float currUserUndergradGPARating;
//    static float currUserGreRating;
//    static float mCurrentUserAvgRating;
    protected   float GreScore3, UndergradPercent3, UnivRating3;

    protected float currUserUndergradUnivRating;
    protected float currUserUndergradGPARating;
    protected float currUserGreRating;
    protected float mCurrentUserAvgRating;

    //////////////// Senior user's rating and similarity array ////////////////////
    protected float[] mArrThisSeniorUndergradUnivRating;
    protected float[] mArrThisSeniorNewUnivRating;
    protected float[] mArrThisSeniorUndergradGPARating;
    protected float[] mArrThisSeniorGreRating;
    protected float[] mArrThisSeniorUserAvgRating;
    protected float[] mArrSimilarityInUsers;

    /////////////// Senior user's name and university arrays //////////////////
    protected String[] mArrSeniorStudName;
    protected String[] mArrSeniorPrevUnivName;
    protected String[] mArrSeniorNewUnivName;
    protected UserDetails[] arrObjUserDetails;

    protected float predict;
    //    public static float mPredictResult;
    int q,p;
    protected float mPredictResult;
    protected int i = -1;
    protected int j;
    protected String mUnivTypePrefer2;
    //protected String mUnivStatePrefer2;
    protected List<String> mListUnivStatesPrefer2;
    protected String univRank;
    protected float univRate;
    protected float temp_predict;
    protected ArrayList<String> recUniversities;
    protected ArrayList<String> list2a;
    protected ArrayList<Integer> list2b;
    protected ArrayList<String> recUnivType;
    //protected ArrayList<String> recUnivState;

    Calculations ratingsCalculate = new Calculations();

//    final ParseUser currUser = ParseUser.getCurrentUser();


    float predictionForNewUser;
    final ParseUser currUser = ParseUser.getCurrentUser();
    public static final String TAG = SuggestUnivActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private ImageView image_expert3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_univ_prefer);

        Toast.makeText(getApplicationContext(), "Wait while algorithm process results", Toast.LENGTH_SHORT).show();
        progress_bar();

        txtViewUnivType = (TextView)findViewById(R.id.txtUnivType);
       // txtViewUnivStates = (TextView)findViewById(R.id.txtUnivStates);

        recUniversities = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
        recUnivType = new ArrayList<String>();
        //recUnivState = new ArrayList<String>();

        list2a = new ArrayList<String>();

        /**************  get Current New User Rating   *************************/
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();  // get bundle from intent
        GreScore3 = Float.valueOf(bundle.getString("mmScore"));
        UndergradPercent3 = Float.valueOf(bundle.getString("mmPercent"));
        UnivRating3 = Float.valueOf(bundle.getString("mmcurrUserUnderUnivRating"));
        UserRating objUserRating1 = new UserRating();
//        objUserRating1.getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);
        /***********************************************************************/
        getCurrentNewUserRating(GreScore3, UndergradPercent3, UnivRating3);
        /************************ put preferences of current user to database *****************************/


        /* ********************************************************************************************** */

        /***********  get Each Senior's Rating and then get recommended universities for current new user ************/
//        predictionForNewUser = objUserRating1.getPredictions();
        getPredictions();
    }
    void progress_bar(){
        progressDialog = new ProgressDialog(SuggestUnivPrefer.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Calculating Results...");
        progressDialog.setTitle("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread() {

            public void run() {

                try{
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        sleep(100);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }.start();

    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.incrementProgressBy(1);
        }
    };
    @Override
    public void onBackPressed(){
        Intent intent2 = new Intent(SuggestUnivPrefer.this, FutureStudent.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
        startActivity(intent2);
//        super.onBackPressed();
//        finish();
    }



    void getCurrentNewUserRating(float GreScore1,float UndergradPercent1,float UnivRating1) {

        currUserGreRating = ratingsCalculate.getGreRating(GreScore1);
        currUserUndergradGPARating = ratingsCalculate.getUndergradStudRating(UndergradPercent1);
        currUserUndergradUnivRating = UnivRating1;
        mCurrentUserAvgRating = ratingsCalculate.getThisUserAvgRatings(currUserGreRating, currUserUndergradUnivRating, currUserUndergradGPARating);

         /*Log.e("TAG", "SCORE=============== " + currUserGreRating + " ,,, PERCENT =============== " +
                 currUserUndergradGPARating + "Univ rating ================="+mCurrUserUndergradUnivRating); */
    }

    void getPredictions() {
//        setProgressBarIndeterminateVisibility(true);
        Log.e(TAG, "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ ====  ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
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

//                    Log.e(TAG, "i is: " + i);

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
//                        Log.e(TAG, "greRating2: " + mArrThisSeniorGreRating[i] + ", undergradStudRating2: " + mArrThisSeniorUndergradGPARating[i] + ", mArrThisSeniorUndergradUnivRating["
//                                + i + "] : " + mArrThisSeniorUndergradUnivRating[i] + ", mArrThisSeniorNewUnivRating[" + i + "]: " + mArrThisSeniorNewUnivRating[i]);
//                        Log.e(TAG, "This Senior " + mArrSeniorStudName[i] + ":" + i + " has total Average rating: " + mArrThisSeniorUserAvgRating[i]);
//                        Log.e(TAG, "---------------------------------------------");
//                        Log.e(TAG, "greRating1: " + currUserGreRating + ",   undergradUnivRating1: " + currUserUndergradUnivRating + ",  undergradStudRating21: " + currUserUndergradGPARating);
//                        Log.e(TAG, "Current new Student " + currUser.getUsername() + " has total Average rating: " + mCurrentUserAvgRating);
//                        Log.e(TAG, "---------------------------------------------");
                        /* ************************************************************************* */

                    } catch (Exception ex3) {
                        Log.e("TAG", "excpt is : " + ex3);
                    }

                    /* ************************** get Similarity of Each Senior user with current new user ************************** */
                    mArrSimilarityInUsers[i] = getUserSimilarities(i);
                    /* *********************************************************************************************************** */

//                    Log.e(TAG, "sim(" + currUser.getUsername() + ", " + mArrSeniorStudName[i] + " ) ::: " + mArrSimilarityInUsers[i]);
//                    Log.e(TAG, "_____________________________________________________________________________________");

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
                /* ************************** get prediction mScore for current new user ************************** */
                mPredictResult = getPredictScore(list.size() - 1);
                /* *********************************************************************************************************** */
                Log.e(TAG,"mPredict: "+mPredictResult);
                /* ************************** get recommended universities for current new user ************************** */
                /*** ----------------------------=====================------------------------  */
                   getRecommendedUniversities(mPredictResult);
                /* *********************************************************************************************************** */
                Log.e(TAG, "----------------------------=====================------------------------");

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
       // Log.e(TAG,"mArrSimilarityInUsers["+0+"]"+mArrSimilarityInUsers[0] + "mArrSimilarityInUsers["+size+"]"+mArrSimilarityInUsers[size]);
        Log.e(TAG, "First 3 similarities: " + tempArrSimInUsers[size] + ", " + tempArrSimInUsers[size-1] + ", " + tempArrSimInUsers[size-2]);

        j =0;
        while(j <= size){
            while(mArrSimilarityInUsers[j] != tempArrSimInUsers[size] && j<=size ){
                j++;
            }
           // Log.e(TAG,"j: "+j);
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

//        Log.e(TAG, "[0] This Senior New Univ Rating: "+tempArrObj[0].getmArrThisSeniorNewUnivRating());
//        Log.e(TAG, "- [0] This Senior User Avg Rating -: "+tempArrObj[0].getmArrThisSeniorUserAvgRating());
//
//        Log.e(TAG, "[1] This Senior New Univ Rating: "+tempArrObj[1].getmArrThisSeniorNewUnivRating());
//        Log.e(TAG, "- [1] This Senior User Avg Rating -: "+tempArrObj[1].getmArrThisSeniorUserAvgRating());
//
//        Log.e(TAG, "[2] This Senior New Univ Rating: "+tempArrObj[2].getmArrThisSeniorNewUnivRating());
//        Log.e(TAG, "- [2] This Senior User Avg Rating -: "+tempArrObj[2].getmArrThisSeniorUserAvgRating());
        Log.e(TAG, "------------------------------------------------------------------------------------------");

        firstMulti = topFirstSimilarity
                * firstSenRatingDiff;
        secMulti = topSecondSimilarity
                * secSenRatingDiff;
        thirdMulti = topThirdSimilarity
                * thirdSenRatingDiff;
        //Log.e(TAG, "firstSenRatingDiff : "+firstSenRatingDiff);
        //Log.e(TAG, "secondSenRatingDiff: "+secSenRatingDiff);
        //Log.e(TAG, "thirdSenRatingDiff : "+thirdSenRatingDiff);
        //Log.e(TAG, "------------------------------------------------------------------------------------------");

        numeratorResult =  mCurrentUserAvgRating + firstMulti + secMulti + thirdMulti +2;
        Log.e(TAG, "mCurrentUserAvgRating: "+mCurrentUserAvgRating);
//        Log.e(TAG, "firstMulti : "+firstMulti);
//        Log.e(TAG, "secondMulti: "+secMulti);
//        Log.e(TAG, "thirdMulti : "+thirdMulti);
//        Log.e(TAG, "numerator result: "+ numeratorResult);
//        Log.e(TAG, "------------------------------------------------------------------------------------------");

        // Math.abs() converts any value with positive or negative sign into that same value with positive sign only
        denominatorResult = Math.abs(topFirstSimilarity) + Math.abs(topSecondSimilarity) + Math.abs(topThirdSimilarity);

//        Log.e(TAG,"|topFirstSimilarity | : "+ Math.abs(topFirstSimilarity));
//        Log.e(TAG,"|topSecondSimilarity| : "+ Math.abs(topSecondSimilarity));
//        Log.e(TAG,"|topThirdSimilarity | : "+ Math.abs(topThirdSimilarity));
//        Log.e(TAG, "denominator Result: "+ denominatorResult);
//        Log.e(TAG, "------------------------------------------------------------------------------------------");

        predict = (numeratorResult / denominatorResult) +4;
        Log.e(TAG, "predict Result: " + predict);
        Log.e(TAG, "------------------------------------------------------------------------------------------");
        return predict;
    }

    float getUserSimilarities(int i) {
        /*Log.e(TAG, " " + mCurrentUserAvgRating + " , " + mArrThisSeniorUserAvgRating[i] + " , " + currUserGreRating + " , " +
                currUserUndergradGPARating
                + " , " + mCurrUserUndergradUnivRating + " , " + mArrThisSeniorGreRating[i] + " , " + mArrThisSeniorUndergradGPARating[i]
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


    void getRecommendedUniversities(float predict){
        temp_predict = predict;
       // Log.e(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        final String[] recUniversities = new String[5];
        ParseQuery<ParseObject> queryRec = ParseQuery.getQuery("UnivDetail");
        queryRec.whereEqualTo("UnivCountry", "United States");
        queryRec.whereGreaterThanOrEqualTo("univRating", predict + 1);
        queryRec.whereLessThanOrEqualTo("univRating", predict + 2.5);
        queryRec.whereLessThanOrEqualTo("univRating", 10);
        queryRec.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {

                if(e == null) {
//                    listView2 = (ListView) findViewById(R.id.listViewRecommend);
                    listView3 = (ListView) findViewById(R.id.listViewRecUnivType);
                   // listView4 = (ListView) findViewById(R.id.listViewRecUnivStates);
                    //                ArrayList<String> recUniversities = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
                    //                ArrayList<String> recUnivType = new ArrayList<String>();
                    int i = 0;
                    Intent intent2 = getIntent();
                    Bundle bundle = intent2.getExtras();  // get bundle from intent
                    mUnivTypePrefer2 = bundle.getString("mmUnivTypePrefer");
                   // mUnivStatePrefer2 = bundle.getString("mmUnivStatePrefer");
                    q = Integer.parseInt(bundle.getString("iValue"));

                    //int i =
//                    while( j >=0){
//                        mListUnivStatesPrefer2.add( bundle.getString("mmListUnivStatesPrefer[" + i + "]"));
//                    }
                    //Log.e(TAG, "PPPPPPPPP:  " + bundle.getString("mmListUnivStatesPrefer"));
                    //mListUnivStatesPrefer2.add( bundle.getString("mmListUnivStatesPrefer"));
                    txtViewUnivType.setText("Recommendation on: " + mUnivTypePrefer2 + " universities");
                   // txtViewUnivStates.setText("Recommendation on: " + mUnivStatePrefer2 + " universities");

                    Log.e(TAG,"mUnivTypePrefer2: "+mUnivTypePrefer2);
                    ////                if(! mUnivTypePrefer2.equals("-- Select Type you prefer --")){
                    //                if(mUnivTypePrefer2.equals("public") || mUnivTypePrefer2.equals("private")){
                    //                    Log.e(TAG,"mUnivTypePrefer2: "+mUnivTypePrefer2);
                    //                    String predResult = String.valueOf(mPredictResult);
                    //                    getRecommendedUniversitiesUnivType(predResult,mUnivTypePrefer2);
                    //                }
                    for (ParseObject parseObj : list) {
                        p = q;
                        String rec,rec2,rec3;
                        if (i < 10) {
                            rec = parseObj.get("univName").toString();
                            univRank = parseObj.get("Ranking").toString();
                            univRate = Float.valueOf(parseObj.get("univRating").toString());
                            Log.e(TAG, "Univ name : " + rec);

                            if(parseObj.get("UnivType").toString().equals(mUnivTypePrefer2))
                            {
                                rec2 = parseObj.get("univName").toString();
                                Log.e(TAG, "==== "+mUnivTypePrefer2 +" Univ name : " + rec2);
                                recUnivType.add(rec2);
                            }
//                            if(parseObj.get("UnivState").toString().equals(mUnivStatePrefer2))
//                            {
//                                rec3 = parseObj.get("univName").toString();
//                                Log.e(TAG, ")))))))) "+mUnivStatePrefer2 +" Univ name : " + rec3);
//                                recUnivState.add(rec3);
//                            }

//                            while(p >=0){
//                                if(parseObj.get("UnivState").toString().equals(mListUnivStatesPrefer2.get(p))){
//                                    rec3 = parseObj.get("univName").toString();
//                                    Log.e(TAG, "==== "+mListUnivStatesPrefer2 +" Univ name : " + rec3);
//                                    recUnivStates.add(rec3);
//                                    break;
//                                }
//                                p--;
//                            }

//                            else if(parseObj.get("UnivState").toString().equals(mListUnivStatesPrefer2.get(p))){
//
//                            }



                            i++;
                        } else {
                            break;
                        }

                        setProgressBarIndeterminateVisibility(false);
                        recUniversities.add(rec);
                        list2a.add(univRank);
//                        MyAdapter adapter2 = new MyAdapter(SuggestUnivPrefer.this, R.layout.custom_list_suggest, recUniversities);
//                        //                    MyAdapter dataAdapter4 = new MyAdapter(context,
//                        //                            R.layout.custom_spinner, list2);
//                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        adapter2.notifyDataSetChanged();
//                        listView2.setAdapter(adapter2);
//                        listView2.setOnItemClickListener(new ListClickHandler());

                        MyAdapter adapter3 = new MyAdapter(SuggestUnivPrefer.this, R.layout.list_view_row, recUnivType);
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter3.notifyDataSetChanged();
                        listView3.setAdapter(adapter3);
                        listView3.setOnItemClickListener(new ListClickHandler());


                        try{
//                            MyAdapter adapter4 = new MyAdapter(SuggestUnivPrefer.this, R.layout.list_view_row, recUnivState);
//                            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            adapter4.notifyDataSetChanged();
//                            listView4.setAdapter(adapter4);
//                            listView4.setOnItemClickListener(new ListClickHandler());
                        }catch (Exception excp){
                            Log.e(TAG,"excp in listview 4 is: "+excp);
                        }

                    }
                }
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
            View mySpinner = inflater.inflate(R.layout.custom_list_suggest, parent,
                    false);
            TextView main_text = (TextView) mySpinner
                    .findViewById(R.id.text_main_univ2);
            main_text.setText(recUniversities.get(position));

            TextView subSpinner = (TextView) mySpinner
                    .findViewById(R.id.sub_text_rank2);
            subSpinner.setText(list2a.get(position));

//            image_expert3 = (ImageView)mySpinner.findViewById(R.id.univ_logo_pic2);
//
//            ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
//            query.whereEqualTo("univName",recUniversities.get(position));
//            query.getFirstInBackground(new GetCallback<ParseObject>() {
//                @Override
//                public void done(ParseObject parseObject, ParseException e) {
//                    ParseFile image = (ParseFile) parseObject.getParseFile("univLogo");
//                    displayImage(image, image_expert3);
//                }
//            });


            TextView text_chance = (TextView) mySpinner
                    .findViewById(R.id.text_chance);
            if(univRate < temp_predict+3 && univRate > temp_predict)
            {
                text_chance.setText("......");
            }
            else{
                text_chance.setText("======");
            }


            try{
//                ImageView left_icon = (ImageView) mySpinner
//                        .findViewById(R.id.univ_logo_pic2);
//                left_icon.setImageResource(list2b.get(position));
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


    void getRecommendedUniversitiesUnivType(String strPredResult, String mUnivTypePrefer2){
        float predResult = Float.valueOf(strPredResult);
        ParseQuery<ParseObject> queryRecType = ParseQuery.getQuery("UnivDetail");
        queryRecType.whereEqualTo("UnivCountry","United States");
        queryRecType.whereEqualTo("UnivTypePrefer",mUnivTypePrefer2);
        queryRecType.whereGreaterThanOrEqualTo("univRating", predResult + 1.5);
        queryRecType.whereLessThanOrEqualTo("univRating", predResult + 4.5);
        queryRecType.whereLessThanOrEqualTo("univRating", 10);
        queryRecType.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                listView3 = (ListView) findViewById(R.id.listViewRecUnivType);
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
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SuggestUnivPrefer.this, R.layout.list_view_row, R.id.listText, recUniversities);
                    listView3.setAdapter(adapter2);
                    listView3.setOnItemClickListener(new ListClickHandler());
                }
            }
        });
    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        String univ_name;
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            TextView listText = (TextView) view.findViewById(R.id.text_main_univ2);
            univ_name = listText.getText().toString();
            Intent intent = new Intent(SuggestUnivPrefer.this, UnivProfile.class);
            Log.e("TAG", "Univ Name -------------" + univ_name);
            intent.putExtra("univ-name", univ_name);
            startActivity(intent);
        }
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivPrefer.this, R.layout.list_view_row, R.id.listText, univnames);
                            listview.setAdapter(adapter);
                        }
                        i++;
                    }
                } else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(SuggestUnivPrefer.this);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
