package khara.karan.netuse;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class UnivDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ParseUser currentUser = ParseUser.getCurrentUser();
        final ParseObject userInfo = new ParseObject("UserRating");
        //userInfo.put("univPtr",query);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserRating");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                String objectID = parseObject.getObjectId().toString();
                userInfo.put("univPtr",objectID);
            }
        });


    }

}
