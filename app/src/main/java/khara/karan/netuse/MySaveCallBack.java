package khara.karan.netuse;

import android.content.Context;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

/**
 * Created by karan on 10/8/15.
 */
class MySaveCallBack implements SaveCallback {
    protected Context context;

    MySaveCallBack(Context context)
    {
        this.context = context;
    }

    @Override
    public void done(ParseException e) {
        if (e == null) {
            Toast.makeText(context, "user profile created", Toast.LENGTH_LONG).show();
            //Log.e("saved no exce............", e.toString());

        } else {
            //Log.e("eventually2::::::::::;", e.toString());
            e.getMessage();
        }
    }
}
