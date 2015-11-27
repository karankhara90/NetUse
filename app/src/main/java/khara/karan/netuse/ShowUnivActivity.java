package khara.karan.netuse;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ShowUnivActivity extends ListActivity {
    public static final String TAG=ShowUnivActivity.class.getSimpleName();
    protected Button mBtnBackUniv;

    ArrayList<String> mArrUnivNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_univ);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UnivDetail");
        query.whereNotEqualTo("UnivCountry","India");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                mArrUnivNames = new ArrayList<String>();
                for (ParseObject obj : list) {
                    mArrUnivNames.add(obj.get("univName").toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ShowUnivActivity.this,
                            android.R.layout.simple_list_item_checked, mArrUnivNames);
                    setListAdapter(adapter);
                }
                mBtnBackUniv = (Button) findViewById(R.id.btnBackUniv);
                mBtnBackUniv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShowUnivActivity.this, FutureStudent.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // also clear the old one
                        startActivity(intent);
                    }
                });
            }
        });



    }

}
