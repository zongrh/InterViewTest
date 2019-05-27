package cn.my.rxjavatest.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.io.Serializable;

public class RxJavaAct5_cancat extends AppCompatActivity {
    String TAG="RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act_cancat);

        //两个发射器连接成一个发射器
        Observable.concat(Observable.just(1, 2, 3, 4), Observable.just("hello", 5, 6))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object serializable) throws Exception {
                        Log.e(TAG, "concat : "+ serializable + "\n" );
                    }
                });

//
//        .subscribe(new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object value) {
//                Log.e(TAG, "concat : "+ value + "\n" );
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

    }
}
