package cn.my.rxjavatest.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class RxJavaAct29_Flowable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act29_flowable);

        Flowable.just(1, 2, 3, 4)
                //seed 作为初始值
                .reduce(100, new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.e("RxJavaAct", "reduce :" + integer + "   +   " + integer2 + "  =  " + (integer + integer2) + "\n");
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("RxJavaAct", "Flowable :" + integer + "\n");

            }
        });
    }
}
