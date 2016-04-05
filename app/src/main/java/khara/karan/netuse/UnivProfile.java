package khara.karan.netuse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
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
    static String mUnivType,mUnivState,mUnivCity;
    private ProgressDialog progressDialog;
    private TextView mTextViewUnivState;
    private TextView mTextViewUnivCity;
    private TextView mTextViewUnivType;
    private ImageView image_expert;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_profile);
        context = this;

        //progress_bar();

        mTextViewUnivProfile = (TextView) findViewById(R.id.textUnivProfile);
        mTextViewUnivState = (TextView) findViewById(R.id.textUnivState);
        mTextViewUnivCity = (TextView) findViewById(R.id.textUnivCity);
        mTextViewUnivType = (TextView) findViewById(R.id.textUnivType);
        image_expert = (ImageView) findViewById(R.id.imageUniv);

        Intent intent = getIntent();
        univSelected = intent.getStringExtra("univ-name");
        mTextViewUnivProfile.setText(univSelected);

        mBtnBackUnivProfile = (Button) findViewById(R.id.btnBackUnivProfile);
        mBtnBackUnivProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnivProfile.this, SuggestUnivActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent);
                // onBackPressed();
            }

        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
        query.whereEqualTo("univName",univSelected);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parseObject, ParseException e) {
               mUnivType = parseObject.getString("UnivType");
               mUnivState = parseObject.getString("UnivState");
               mUnivCity = parseObject.getString("UnivCity");

                mTextViewUnivState.setText(mUnivState);
                mTextViewUnivCity.setText(mUnivCity);
                mTextViewUnivType.setText(mUnivType);

//                ParseFile fileObject = (ParseFile)parseObject.get("univLogo");
//                fileObject.getDataInBackground(new GetDataCallback() {
//                    @Override
//                    public void done(byte[] bytes, ParseException e) {
//                        if (e == null) {
//                            ImageView ad1=(ImageView) findViewById(R.id.ad1);
//                            // Set the Bitmap into the
//                            // ImageView
//                            ad1.setImageBitmap(bmp);
//
//                        } else {
//                            Log.d("test", "There was a problem downloading the data.");
//                        }
//                    }
//                });

                ParseFile image = (ParseFile) parseObject.getParseFile("univLogo");
                displayImage(image, image_expert);
//                ParseFile postImage = parseObject.getParseFile("univLogo");
//                String imageUrl = postImage.getUrl() ;//live url
//                Uri imageUri = Uri.parse(imageUrl);
//
//                Picasso.with(context).load(imageUri.toString()).into(image_expert);



            }
        });

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");

        query3.whereEqualTo("newUnivName",univSelected);
        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject obj : list) {
                    Log.e("TAG", "new univ: "+obj.get("newUnivName").toString());
                   // if(obj.get("newUnivName").toString().equals(univSelected)){
                        mGreScore = Integer.parseInt(obj.get("greScore").toString());

                        mPercent = Integer.parseInt(obj.get("undergradPercent").toString());
                        Log.e("TAG","gre: "+mGreScore);
                        Log.e("TAG","percent: "+mPercent);

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
                   // }

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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(UnivProfile.this, SuggestUnivActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
        startActivity(intent);
        //super.onBackPressed();
        //finish();
    }
    void progress_bar(){
        progressDialog = new ProgressDialog(UnivProfile.this);
        progressDialog.setMax(50);
        progressDialog.setMessage("Calculating Results...");
        progressDialog.setTitle("Please wait..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread() {

            public void run() {

                try{
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        sleep(50);
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
}
