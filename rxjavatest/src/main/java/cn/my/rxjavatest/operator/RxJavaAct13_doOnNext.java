package cn.my.rxjavatest.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxJavaAct13_doOnNext extends AppCompatActivity {
    String TAG = "RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act13_do_on_next);

//        doOnNext 它的作用是让订阅者在接收到数据之前干点有意思的事情
//        例如我们在获取到数据之前想先保存一下数据
        Observable.just(1, 2, 3, 4)
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "doOnNext 保存 " + integer + "成功" + "\n");
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "doOnNext :" + integer + "\n");
            }
        });

    }
}
