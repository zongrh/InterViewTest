package cn.my.rxjavatest.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

/**
 * "interval 使用场景\n"
 * "间隔任务实现心跳\n"
 * "可能我们会遇上各种即时通讯，如果是自己家开发的 IM 即时通讯，我相信在移动端一定少不了心跳包的管理，\n"
 * "而我们 RxJava 2.x 的 interval 操作符棒我们解决了这个问题。"
 */
public class RxCaseIntervalActivity extends AppCompatActivity {
    String TAG = "RxAct";
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_case_interval);

//        间隔任务实现心跳
        mDisposable = Flowable
                .interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept: doOnNext : " + aLong);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept: 设置文本 ：" + aLong);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

}
