package cn.my.rxjavatest.test;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class RxJavaAct22_reduce extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act22_reduce);

//        reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。

        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    //我们中间采用 reduce ，支持一个 function 为两数值相加
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e("RxJavaAct", "BiFunction: apply : " + integer + "  +  " + integer2 + " = " + (integer + integer2) + "\n");

                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("RxJavaAct", "accept: reduce : " + integer + "\n");
            }
        });

    }
}
