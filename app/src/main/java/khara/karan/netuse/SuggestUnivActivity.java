package khara.karan.netuse;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SuggestUnivActivity extends ListActivity {
    int score2,percent2;
    public static final String TAG=SuggestUnivActivity.class.getSimpleName();
   // protected ParseUser mCurrentUser3;
    protected Button mBtnBackSuggest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_univ);

        Intent intent5 = getIntent();
        // 4. get bundle from intent
        Bundle bundle = intent5.getExtras();
        // 5. get status value from bundle
         score2 = Integer.parseInt(bundle.getString("score"));
         percent2 = Integer.parseInt(bundle.getString("percent"));

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
    @Override
    protected void onResume() {
        super.onResume();
        ParseUser mCurrentUser3 = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("UserInfo");
        //Log.e("TAG","SCORE+++++ "+score2+" ,,, PERCENT ++++ "+percent2);
        query3.whereLessThanOrEqualTo("greScore", score2+7);
        query3.whereGreaterThanOrEqualTo("greScore", score2-15);
        query3.whereLessThanOrEqualTo("undergradPercent", percent2+15);
        query3.whereGreaterThanOrEqualTo("undergradPercent", percent2-15);

        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    //  String[] usernames = new String[obs.size()];
                    ArrayList<String> univnames = new ArrayList<String>();
                    int size = 30;
                    String[] arr = new String[size];
                    int i=0, repeat;
                    for (ParseObject b : list) {
                        //usernames[i] = a.get("fullName").toString();


                        String abc = b.get("newUnivName").toString();

                        if(i<=30){
                            arr[i] = abc;
                        }
                        repeat = 0;
                        for(int j=0;j<=i;j++)
                        {
                            if(abc.equals(arr[j])){
                                repeat++;
                                if(repeat>1)
                                { break;}
                            }
                        }

                        if(!abc.equals("blank") && repeat==1)
                        {
                            univnames.add(abc);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SuggestUnivActivity.this,
                                    android.R.layout.simple_list_item_1, univnames);
                            //android.R.layout.simple_selectable_list_item, usernames);
                            setListAdapter(adapter);
                        }
                        i++;
                    }
                }
                else {
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
