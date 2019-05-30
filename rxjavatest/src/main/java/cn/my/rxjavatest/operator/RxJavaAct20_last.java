package cn.my.rxjavatest.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxJavaAct20_last extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act20_last);

//        last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
        Observable.just(1, 2, 3,99,33,0)
        .last(5)
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("RxJavaAct", "last : " + integer + "\n");
            }
        });
    }
}
