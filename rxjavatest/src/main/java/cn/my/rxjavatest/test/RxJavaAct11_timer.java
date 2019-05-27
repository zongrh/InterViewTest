package cn.my.rxjavatest.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import cn.my.rxjavatest.R;
import cn.my.rxjavatest.utils.DateUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RxJavaAct11_timer extends AppCompatActivity {
    String TAG = "RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act11_timer);
        System.out.println();
        Log.e(TAG, "timer start : " + DateUtil.getStringDate() + "\n");
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "timer :" + aLong + " at " + DateUtil.getStringDate() + "\n");
                    }
                });
    }
}
