package cn.my.rxjavatest.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import java.util.Random;

public class RxJavaAct17_Single extends AppCompatActivity {
    String TAG = "RxJavaAct";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_act17__single);
//        Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。

        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer value) {
                        Log.e(TAG, "single : onSuccess : "+value+"\n" );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "single : onError : "+e.getMessage()+"\n");
                    }
                });

    }
}
