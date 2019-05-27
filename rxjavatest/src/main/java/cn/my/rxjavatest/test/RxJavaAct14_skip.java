package cn.my.rxjavatest.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxJavaAct14_skip extends AppCompatActivity {
    String TAG = "RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act14_skip);

//        skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，
//        代表跳过 count 个数目开始接收。
        Observable.just(1, 2, 3, 4, 5).skip(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "skip : "+integer + "\n");
                    }
                });
    }
}
