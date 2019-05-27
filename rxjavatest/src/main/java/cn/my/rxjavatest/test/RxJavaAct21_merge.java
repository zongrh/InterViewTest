package cn.my.rxjavatest.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxJavaAct21_merge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act21_merge);

//        merge 顾名思义，熟悉版本控制工具的你一定不会不知道 merge 命令，
//        而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，
//        接受可变参数，也支持迭代器集合。注意它和 concat 的区别在于，
//        不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5, 99))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("RxJavaAct", "accept : " + integer + "\n");
                    }
                });

    }
}
