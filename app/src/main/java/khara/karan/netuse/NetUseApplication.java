package khara.karan.netuse;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by karan on 9/29/15.
 */
public class NetUseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        Parse.initialize(this, "MlC9BwlFHTFGhnRoZK3OZ6juYjBlgFCxs3WTwiup", "93O5Zw6ujGWbL2OxvqRwgMHuqLFBimGrtHgw7ATQ");
        //PushService.setDefaultPushCallback(this, MainActivity.class);
    }
}
