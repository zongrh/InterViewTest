package cn.my.rxjavatest.operator;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RxJavaAct28_Completable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act28__completable);

        Log.e("RxJavaAct", "Completable\n");

        Completable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("RxJavaAct", "onSubscribe : d :" + d.isDisposed() + "\n");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("RxJavaAct", "onComplete\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("RxJavaAct", "onError :" + e.getMessage() + "\n");
                    }
                });
    }
}
