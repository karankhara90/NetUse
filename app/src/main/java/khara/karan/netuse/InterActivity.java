package khara.karan.netuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class InterActivity extends ActionBarActivity {
    int i,j;
    protected Button mBtnNextToSuggest;
    String[] underUniv;
    String[] univRating;
    Bundle bundle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBtnNextToSuggest = (Button) findViewById(R.id.btnNextToSuggest);
        mBtnNextToSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                // 4. get bundle from intent
                Bundle bundle = intent.getExtras();
                //String message = intent.getStringExtra("mPressureGroup");
                // 5. get status value from bundle
                String fullname = bundle.getString("fullname");

                ParseQuery<ParseObject> query3;
                query3 = ParseQuery.getQuery("UserInfo");           // UserInfo table
                query3.whereNotEqualTo("newUnivName", "blank");
                query3.whereNotEqualTo("fullName", fullname);
                query3.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        Log.e("TAG", "..........-----------");
                        Log.e("TAG", "list size: " + list.size());
                        underUniv = new String[list.size()];
                        i = -1;
                      /*  for (ParseObject po : list) {
                            i++;
                            //Log.e("TAG", "i :: " + i);
                            underUniv[i] = po.get("undergradUniv").toString();
                            //Log.e("TAG", "undergradUniv: " + underUniv[i]);
                           Log.e("TAG", "..............................................");
                            ParseQuery<ParseObject> query4 = ParseQuery.getQuery("UnivDetail");
                            //Log.e("TAG", ""
                            query4.whereEqualTo("univName", underUniv[i]);
                            //query4.findInBackground(new FindCallback<ParseObject>() {
                            //  @Override
                            //public void done(List<ParseObject> list, ParseException e) {
                            query4.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject parseObject, ParseException e) {

                                    Log.e("TAG", ".............***********.................................");
                                    //j = -1;
                                    //for (ParseObject po2 : list) {
                                    //  j++;
                                    univRating[i] = parseObject.get("univRating").toString();
                                    Log.e("TAG", ".................");
                                    Log.e("TAG", "univRating[" + i + "]: " + univRating[i]);

                                    //bundle2 = new Bundle();
                                    //bundle2.putString("univRating[" + j + "]", univRating[j]);


                                }
                            });
                        }
                        Intent intent3 = new Intent(InterActivity.this, SuggestUnivActivity.class);
//
                        bundle2 = new Bundle();
                        for (int k = 0; k < list.size(); k++) {
                            if(k< list.size()){
                                Log.e("TAG", "univ rating[" + k + "]" + univRating[k]);
                                bundle2.putString("univRating[" + k + "]", univRating[k]);
                            }

                        }
                        //dfd
                        intent3.putExtras(bundle2);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                        startActivity(intent3);
                        */

                    }


                });
                Intent intent3 = new Intent(InterActivity.this, SuggestUnivActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                startActivity(intent3);
            }
        });
    }

}
