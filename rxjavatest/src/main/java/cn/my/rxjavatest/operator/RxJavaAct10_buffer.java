package cn.my.rxjavatest.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.util.List;

public class RxJavaAct10_buffer extends AppCompatActivity {
    String TAG = "RxJavaAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act10_buffer);
//        buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable 。
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3,2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.e(TAG, "buffer size : " + integers.size() + "\n");
                        Log.e(TAG, "buffer value : " + integers.toString());
                        for (Integer i : integers) {
                            Log.e(TAG, i + "");
                        }
                        Log.e(TAG, "\n");
                    }
                });
    }
}
