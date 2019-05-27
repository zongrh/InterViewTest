package cn.my.rxjavatest.utils;

import android.content.Context;
import cn.my.rxjavatest.Application;

/**
 * @created by PingYuan at 8/2/18
 * @email: husteryp@gmail.com
 * @description: Use this class to get the resource from app
 */
public class AssetsUtils {
    private static Context sContext = Application.sAppContext;

    public static String getStringFromResource(int id) {
        return sContext.getResources().getString(id);
    }
}
