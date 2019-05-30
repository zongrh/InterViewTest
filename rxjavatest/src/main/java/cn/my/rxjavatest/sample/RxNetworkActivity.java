package cn.my.rxjavatest.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import cn.my.rxjavatest.model.MobileAddress;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 使用框架 rx2-Networking
 * 1、通过 Rx2AndroidNetworking 的 get() 方法获取 Observable 对象(已解析);
 * 2、调度线程，根据请求结果更新 UI。
 */
public class RxNetworkActivity extends AppCompatActivity {
    String TAG = "RxAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_network);
        Rx2AndroidNetworking.get("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                .build()
                .getObjectObservable(MobileAddress.class)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<MobileAddress>() {
                    @Override
                    public void accept(MobileAddress mobileAddress) throws Exception {
                        Log.e(TAG, "doOnNext:" + Thread.currentThread().getName() + "\n");
                        Log.e(TAG, "doOnNext:" + mobileAddress.toString() + "\n");
                    }
                }).map(new Function<MobileAddress, MobileAddress.ResultBean>() {

            @Override
            public MobileAddress.ResultBean apply(MobileAddress mobileAddress) throws Exception {
                Log.e(TAG, "\n" );
                Log.e(TAG, "map:"+Thread.currentThread().getName()+"\n" );
                return mobileAddress.getResult();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<MobileAddress.ResultBean>() {
            @Override
            public void accept(MobileAddress.ResultBean resultBean) throws Exception {
                Log.e(TAG, "subscribe 成功:" + Thread.currentThread().getName() + "\n");
                Log.e(TAG, "成功:" + resultBean.toString() + "\n");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "subscribe 失败:"+Thread.currentThread().getName()+"\n" );
                Log.e(TAG, "失败："+ throwable.getMessage()+"\n" );
            }
        });


    }
}
