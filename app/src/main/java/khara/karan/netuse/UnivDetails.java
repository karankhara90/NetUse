package khara.karan.netuse;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class UnivDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
