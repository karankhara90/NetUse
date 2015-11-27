package khara.karan.netuse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    ListView listView2,listView3,listView4;
    TextView txtViewUnivType;
    TextView txtViewUnivStates;

    //////////////// currrent user's rating ////////////////////////////
//    static float mCurrUserUndergradUnivRating;
//    static float currUserUndergradGPARating;
//    static float currUserGreRating;
//    static float mCurrentUserAvgRating;
    static float GreScore1, UndergradPercent1, UnivRating1;

    static float currUserUndergradUnivRating;
    static float currUserUndergradGPARating;
    static float currUserGreRating;
    static float mCurrentUserAvgRating;

    //////////////// Senior user's rating and similarity array ////////////////////
    static float[] mArrThisSeniorUndergradUnivRating;
    static float[] mArrThisSeniorNewUnivRating;
    static float[] mArrThisSeniorUndergradGPARating;
    static float[] mArrThisSeniorGreRating;
    static float[] mArrThisSeniorUserAvgRating;
    static float[] mArrSimilarityInUsers;

    /////////////// Senior user's name and university arrays //////////////////
    static String[] mArrSeniorStudName;
    static String[] mArrSeniorPrevUnivName;
    static String[] mArrSeniorNewUnivName;
    static UserDetails[] arrObjUserDetails;

    float predict;
//    public static float mPredictResult;
    public float mPredictResult;
    static int i = -1;
    int j;
    String mUnivTypePrefer2;
    List <String> mListUnivStatesPrefer2;

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

        txtViewUnivType = (TextView)findViewById(R.id.txtUnivType);
        txtViewUnivStates = (TextView)findViewById(R.id.txtUnivStates);

//        HorizontalListView listview3 = (HorizontalListView) findViewById(R.id.listview);
//        listview.setAdapter(mAdapter);

        /**************  get Current New User Rating   *************************/
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();  // get bundle from intent
        GreScore1 = Float.valueOf(bundle.getString("mScore"));
        UndergradPercent1 = Float.valueOf(bundle.getString("mPercent"));
        UnivRating1 = Float.valueOf(bundle.getString("currUserUnderUnivRating"));
        UserRating objUserRating1 = new UserRating();
//        objUserRating1.getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);
        /***********************************************************************/
         getCurrentNewUserRating(GreScore1, UndergradPercent1, UnivRating1);
        /************************ put preferences of current user to database *****************************/


        /* ********************************************************************************************** */

        /***********  get Each Senior's Rating and then get recommended universities for current new user ************/
//        predictionForNewUser = objUserRating1.getPredictions();
              getPredictions();
//        Log.e(TAG, "----------------------------=====================------------------------");
//        getRecommendedUniversities(predictionForNewUser);
        /* ********************************************************************************************************* */

        /*********  get Suggested Universities From Database   ****************/
          //getSuggestedUnivFromDatabase();
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



//    private static String[] dataObjects = new String[]{ "Text #1",
//            "Text #2",
//            "Text #3","Text #4","Text #5","Text #6","Text #7","Text #8","Text #9","Text #10"
//    };
//
//    private BaseAdapter mAdapter = new BaseAdapter() {
//
//        private DialogInterface.OnClickListener mOnButtonClicked = new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(SuggestUnivActivity.this);
//                builder.setMessage("hello from " + v);
//                builder.setPositiveButton("Cool", null);
//                builder.show();
//
//            }
//        };
//
//        @Override
//        public int getCount() {
//            return dataObjects.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, null);
//            TextView title = (TextView) retval.findViewById(R.id.title);
//            Button button = (Button) retval.findViewById(R.id.clickbutton);
//            button.setOnClickListener(mOnButtonClicked);
//            title.setText(dataObjects[position]);
//
//            return retval;
//        }
//
//    };

    void getCurrentNewUserRating(float GreScore1,float UndergradPercent1,float UnivRating1) {

        currUserGreRating = ratingsCalculate.getGreRating(GreScore1);
        currUserUndergradGPARating = ratingsCalculate.getUndergradStudRating(UndergradPercent1);
        currUserUndergradUnivRating = UnivRating1;
        mCurrentUserAvgRating = ratingsCalculate.getThisUserAvgRatings(currUserGreRating, currUserUndergradUnivRating, currUserUndergradGPARating);

         /*Log.e("TAG", "SCORE=============== " + currUserGreRating + " ,,, PERCENT =============== " +
                 currUserUndergradGPARating + "Univ rating ================="+mCurrUserUndergradUnivRating); */
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

                /* ************************** get recommended universities for current new user ************************** */
                Log.e(TAG, "----------------------------=====================------------------------");
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
        Log.e(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        final String[] recUniversities = new String[5];
        ParseQuery<ParseObject> queryRec = ParseQuery.getQuery("UnivDetail");
        queryRec.whereEqualTo("UnivCountry","United States");
        queryRec.whereGreaterThanOrEqualTo("univRating", predict+2 );
        queryRec.whereLessThanOrEqualTo("univRating", predict + 4.5);
        queryRec.whereLessThanOrEqualTo("univRating", 10);
        queryRec.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
            if(e == null) {
                listView2 = (ListView) findViewById(R.id.listViewRecommend);
                listView3 = (ListView) findViewById(R.id.listViewRecUnivType);
                ArrayList<String> recUniversities = new ArrayList<String>();  // didnt use arrays. coz arraylist's size is flexible unlike arrays
                ArrayList<String> recUnivType = new ArrayList<String>();
                int i = 0;
                Intent intent2 = getIntent();
                Bundle bundle = intent2.getExtras();  // get bundle from intent
                mUnivTypePrefer2 = bundle.getString("mUnivTypePrefer").toString();
                txtViewUnivType.setText("Recommendation on: " + mUnivTypePrefer2 + " universities");

//                try{
//                    mListUnivStatesPrefer2.add(bundle.getString("mListUnivStatesPrefer"));
//                    txtViewUnivStates.setText(mListUnivStatesPrefer2.toString());
//                    Log.e(TAG, "states preferred: " + mListUnivStatesPrefer2);
//                }catch(Exception exc){
//                    Log.e(TAG,"exc exception in univStatesprefer is: "+exc);
//                }

                Log.e(TAG,"mUnivTypePrefer2: "+mUnivTypePrefer2);
////                if(! mUnivTypePrefer2.equals("-- Select Type you prefer --")){
//                if(mUnivTypePrefer2.equals("public") || mUnivTypePrefer2.equals("private")){
//                    Log.e(TAG,"mUnivTypePrefer2: "+mUnivTypePrefer2);
//                    String predResult = String.valueOf(mPredictResult);
//                    getRecommendedUniversitiesUnivType(predResult,mUnivTypePrefer2);
//                }
                for (ParseObject parseObj : list) {
                    String rec,rec2,rec3;
                    if (i < 15) {
                        rec = parseObj.get("univName").toString();
                        Log.e(TAG, "Univ name : " + rec);
                        if(parseObj.get("UnivType").toString().equals(mUnivTypePrefer2))
                        {
                            rec2 = parseObj.get("univName").toString();
                            Log.e(TAG, "==== "+mUnivTypePrefer2 +" Univ name : " + rec2);
                            recUnivType.add(rec2);
                        }

//                        for(String listUniv : mListUnivStatesPrefer2){
//                            if(parseObj.get("UnivState").toString().equals(listUniv)){
//                                rec3 = parseObj.get("univName").toString();
//                                //Log.e(TAG, "==== "+mUnivTypePrefer2 +" Univ name : " + rec2);
//                                recUnivType.add(rec3);
//                                break;
//                            }
//                        }

                        i++;
                    } else {
                        break;
                    }

                    recUniversities.add(rec);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, recUniversities);
                    listView2.setAdapter(adapter2);
                    listView2.setOnItemClickListener(new ListClickHandler());

                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, recUnivType);
                    listView3.setAdapter(adapter3);
                    listView3.setOnItemClickListener(new ListClickHandler());
                }
            }
            }
        });
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
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SuggestUnivActivity.this, R.layout.list_view_row, R.id.listText, recUniversities);
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
            TextView listText = (TextView) view.findViewById(R.id.listText);
            univ_name = listText.getText().toString();
            Intent intent = new Intent(SuggestUnivActivity.this, UnivProfile.class);
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
