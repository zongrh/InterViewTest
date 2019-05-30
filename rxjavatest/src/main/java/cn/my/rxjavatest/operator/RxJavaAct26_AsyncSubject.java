package cn.my.rxjavatest.operator;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;

public class RxJavaAct26_AsyncSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act26__async_subject);

        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();


        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("RxJavaAct", "First onSubscribe :"+d.isDisposed()+"\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("RxJavaAct", "First onNext value :"+integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("RxJavaAct", "First onError:"+e.getMessage()+"\n" );
            }

            @Override
            public void onComplete() {
                Log.e("RxJavaAct", "First onComplete!\n");
            }
        });

        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);

        asyncSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("RxJavaAct", "Second onSubscribe :"+d.isDisposed()+"\n");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("RxJavaAct", "Second onNext value :"+integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("RxJavaAct", "Second onError:"+e.getMessage()+"\n" );
            }

            @Override
            public void onComplete() {
                Log.e("RxJavaAct", "Second onComplete!\n");
            }
        });

        asyncSubject.onNext(4);
        asyncSubject.onNext(5);
        asyncSubject.onComplete();


    }
}
