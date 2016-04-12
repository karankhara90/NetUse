package khara.karan.netuse;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;

//import android.webkit.JavascriptInterface;

@SuppressLint("SetJavaScriptEnabled")
public class ShowWebChartActivity extends AppCompatActivity {
    Button mBtnBackWebChart;
    int mNum1,mNum2,mNum3,mNum4,mNum5,mNum6,mNum7,mNum8,mNum9,mNum10;
    int mPerc1,mPerc2,mPerc3,mPerc4,mPerc5,mPerc6,mPerc7,mPerc8,mPerc9,mPerc10;

    WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web_chart);
        progress_bar();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        mNum1 = intent.getIntExtra("num1", 0);
        mNum2 = intent.getIntExtra("num2", 0);
        mNum3 = intent.getIntExtra("num3", 0);
        mNum4 = intent.getIntExtra("num4", 0);
        mNum5 = intent.getIntExtra("num5", 0);
        mNum6 = intent.getIntExtra("num6", 0);
        mNum7 = intent.getIntExtra("num7", 0);
        mNum8 = intent.getIntExtra("num8", 0);
        mNum9 = intent.getIntExtra("num9", 0);
        mNum10 = intent.getIntExtra("num10", 0);

        mPerc1 = intent.getIntExtra("perc1", 0);
        mPerc2 = intent.getIntExtra("perc2", 0);
        mPerc3 = intent.getIntExtra("perc3", 0);
        mPerc4 = intent.getIntExtra("perc4", 0);
        mPerc5 = intent.getIntExtra("perc5", 0);
        mPerc6 = intent.getIntExtra("perc6", 0);
        mPerc7 = intent.getIntExtra("perc7", 0);
        mPerc8 = intent.getIntExtra("perc8", 0);
        mPerc9 = intent.getIntExtra("perc9", 0);
        mPerc10 = intent.getIntExtra("perc10", 0);

        webView = (WebView)findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/chart.html");


        mBtnBackWebChart = (Button) findViewById(R.id.btnBackWebChart);
        mBtnBackWebChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowWebChartActivity.this, FutureStudent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent);
                //onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ShowWebChartActivity.this, FutureStudent.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
        startActivity(intent);
//        super.onBackPressed();
//        //finish();

    }

    void progress_bar(){
        progressDialog = new ProgressDialog(ShowWebChartActivity.this);
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

    public class WebAppInterface {

        @JavascriptInterface
        public int getNum1() {
            return mNum1;
        }
        @JavascriptInterface
        public int getNum2() {
            return mNum2;
        }
        @JavascriptInterface
        public int getNum3() {
            return mNum3;
        }
        @JavascriptInterface
        public int getNum4() {
            return mNum4;
        }
        @JavascriptInterface
        public int getNum5() {
            return mNum5;
        }
        @JavascriptInterface
        public int getNum6() {
            return mNum6;
        }
        @JavascriptInterface
        public int getNum7() {
            return mNum7;
        }
        @JavascriptInterface
        public int getNum8() {
            return mNum8;
        }
        @JavascriptInterface
        public int getNum9() {
            return mNum9;
        }
        @JavascriptInterface
        public int getNum10() {
            return mNum10;
        }


        @JavascriptInterface
        public int getPerc1() {
            return mPerc1;
        }
        @JavascriptInterface
        public int getPerc2() {
            return mPerc2;
        }
        @JavascriptInterface
        public int getPerc3() {
            return mPerc3;
        }
        @JavascriptInterface
        public int getPerc4() {
            return mPerc4;
        }
        @JavascriptInterface
        public int getPerc5() {return mPerc5; }
        @JavascriptInterface
        public int getPerc6() {return mPerc6; }
        @JavascriptInterface
        public int getPerc7() {return mPerc7; }
        @JavascriptInterface
        public int getPerc8() {return mPerc8; }
        @JavascriptInterface
        public int getPerc9() {return mPerc9; }
        @JavascriptInterface
        public int getPerc10() {return mPerc10; }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


}
