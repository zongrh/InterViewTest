package cn.my.rxjavatest;

import android.content.Context;
import com.androidnetworking.AndroidNetworking;

/**
 * @created by PingYuan at 8/2/18
 * @email: husteryp@gmail.com
 * @description:
 */
public class Application extends android.app.Application {

    public static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        AndroidNetworking.initialize(getApplicationContext());

    }
}
