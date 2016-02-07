package khara.karan.netuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UnivProfile extends ActionBarActivity {
     TextView mTextViewUnivProfile;
    Button mBtnUnivDataGraph;
    Button mBtnBackUnivProfile;

    int mGreScore, num1 = 0,num2 = 0,num3 = 0,num4 = 0,num5 = 0,num6 = 0,num7 = 0,num8 = 0,num9 = 0,num10 = 0;
    int mPercent, perc1 = 0,perc2 = 0,perc3 = 0,perc4 = 0,perc5 = 0,perc6 = 0,perc7 = 0,perc8= 0,perc9= 0,perc10= 0;
     String univSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_profile);
        mTextViewUnivProfile = (TextView) findViewById(R.id.textUnivProfile);

        Intent intent = getIntent();
        univSelected = intent.getStringExtra("univ-name");
        mTextViewUnivProfile.setText(univSelected);

        mBtnBackUnivProfile = (Button) findViewById(R.id.btnBackUnivProfile);
        mBtnBackUnivProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnivProfile.this, SuggestUnivActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent);
               // onBackPressed();
            }

        });

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");
        query3.whereEqualTo("newUnivName",univSelected);
        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject obj : list) {
                    mGreScore = Integer.parseInt(obj.get("greScore").toString());
                    Log.e("TAG","gre: "+mGreScore);
                    mPercent = Integer.parseInt(obj.get("undergradPercent").toString());

                    if (mGreScore >= 290 && mGreScore <= 295) {
                        num1++;
                    }
                    else if (mGreScore >= 296 && mGreScore <= 300) {
                        num2++;
                    }
                    else if (mGreScore >= 301 && mGreScore <= 305) {
                        num3++;
                    }
                    else if (mGreScore >= 306 && mGreScore <= 310) {
                        num4++;
                    }
                    else if (mGreScore >= 311 && mGreScore <= 315) {
                        num5++;
                    } else if (mGreScore >= 316 && mGreScore <= 320) {
                        num6++;
                    } else if (mGreScore >= 321 && mGreScore <= 325) {
                        num7++;
                    }
                    else if (mGreScore >= 326 && mGreScore <= 330) {
                        num8++;
                    }
                    else if (mGreScore >= 331 && mGreScore <= 335) {
                        num9++;
                    }
                    else if (mGreScore >= 336 && mGreScore <= 340) {
                        num10++;
                    }


                    if (mPercent >= 51 && mPercent <= 55) {
                        perc1++;
                    } else if (mPercent >= 56 && mPercent <= 60) {
                        perc2++;
                    } else if (mPercent >= 61 && mPercent <= 65) {
                        perc3++;
                    } else if (mPercent >= 66 && mPercent <= 70) {
                        perc4++;
                    } else if (mPercent >= 71 && mPercent <= 75) {
                        perc5++;
                    }
                    else if (mPercent >= 76 && mPercent <= 80) {
                        perc6++;
                    }
                    else if (mPercent >= 81 && mPercent <= 85) {
                        perc7++;
                    }
                    else if (mPercent >= 86 && mPercent <= 90) {
                        perc8++;
                    }
                    else if (mPercent >= 91 && mPercent <= 95) {
                        perc9++;
                    }
                    else if (mPercent >= 96 && mPercent <= 100) {
                        perc10++;
                    }

                }

                mBtnUnivDataGraph = (Button) findViewById(R.id.btnUnivDataGraph);
                mBtnUnivDataGraph.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        setProgressBarIndeterminateVisibility(true);
                        setProgressBarIndeterminateVisibility(false);
                        Intent intent = new Intent(UnivProfile.this, ShowWebChartActivity.class);
                        Log.e("TAG", num1 + ", " + num2 + ", " + num3 + ", " + num4 + ", " + num5);

                        intent.putExtra("num1", num1);
                        intent.putExtra("num2", num2);
                        intent.putExtra("num3", num3);
                        intent.putExtra("num4", num4);
                        intent.putExtra("num5", num5);
                        intent.putExtra("num6", num6);
                        intent.putExtra("num7", num7);
                        intent.putExtra("num8", num8);
                        intent.putExtra("num9", num9);
                        intent.putExtra("num10", num10);

                        intent.putExtra("perc1", perc1);
                        intent.putExtra("perc2", perc2);
                        intent.putExtra("perc3", perc3);
                        intent.putExtra("perc4", perc4);
                        intent.putExtra("perc5", perc5);
                        intent.putExtra("perc6", perc6);
                        intent.putExtra("perc7", perc7);
                        intent.putExtra("perc8", perc8);
                        intent.putExtra("perc9", perc9);
                        intent.putExtra("perc10", perc10);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                        startActivity(intent);
                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UnivProfile.this, SuggestUnivActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
        startActivity(intent);
        //super.onBackPressed();
        //finish();
    }
}
