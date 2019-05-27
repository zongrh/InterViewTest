package cn.my.rxjavatest.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.Serializable;

public class RxJavaAct16_just extends AppCompatActivity {
    String TAG = "RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act16_just);
//        just  就是一个简单的发射器依次调用 onNext() 方法。
        Observable.just("1", "2",1,5,7)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Serializable>() {
            @Override
            public void accept(Serializable serializable) throws Exception {
                Log.e(TAG,"accept : onNext : " + serializable + "\n" );
            }
        });
    }
}
