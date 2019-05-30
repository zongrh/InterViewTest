package cn.my.rxjavatest.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class RxJavaAct15_take extends AppCompatActivity {
    String TAG = "RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act15_take);

//        take，接受一个 long 型参数 count ，代表至多接收 count 个数据。
        Flowable.fromArray(1, 2, 3, 4, 5)
        .take(2)//最多接收多少个参数
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "accept: take : "+integer + "\n" );
            }
        });

    }
}
