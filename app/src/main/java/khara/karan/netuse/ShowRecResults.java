package khara.karan.netuse;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ShowRecResults extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rec_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
