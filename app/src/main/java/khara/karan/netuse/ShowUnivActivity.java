package khara.karan.netuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ShowUnivActivity extends AppCompatActivity {
    public static final String TAG=ShowUnivActivity.class.getSimpleName();
    protected Button mBtnBackUniv;
    ListView listAllUniv;
    ArrayList<String> mArrUnivNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_univ);

        listAllUniv = (ListView)findViewById(R.id.allUnivList);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
        query.whereNotEqualTo("UnivCountry", "India");
        query.orderByAscending("Ranking");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mArrUnivNames = new ArrayList<String>();
                for (ParseObject obj : list) {
                    mArrUnivNames.add(obj.get("univName").toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowUnivActivity.this,
                            android.R.layout.simple_selectable_list_item, mArrUnivNames);
                     listAllUniv.setAdapter(adapter);
                    //ListView listView = getListView();

                    listAllUniv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        String univ_name;

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ListView lv = (ListView) parent;
                            TextView tv = (TextView) lv.getChildAt(position);
                            univ_name = tv.getText().toString();
                            Intent intent = new Intent(ShowUnivActivity.this, UnivProfile.class);
                            Log.e("TAG", "Univ Name -------------" + univ_name);
                            intent.putExtra("univ-name", univ_name);
                            startActivity(intent);
                        }
                    });

                }
//                mBtnBackUniv = (Button) findViewById(R.id.btnBackUniv);
//                mBtnBackUniv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(ShowUnivActivity.this, FutureStudent.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
//                        startActivity(intent);
//                    }
//                });
            }
        });



    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
