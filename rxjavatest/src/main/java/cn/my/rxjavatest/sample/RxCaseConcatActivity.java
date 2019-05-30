package cn.my.rxjavatest.sample;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import cn.my.rxjavatest.model.FoodList;
import cn.my.rxjavatest.utils.CacheManager;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.net.CacheRequest;

/**
 * Concat 先读取缓存数据并展示UI再获取网络数据刷新UI
 * <p>
 * 1、concat 可以做到不交错的发射两个甚至多个 Observable 的发射物;
 * 2、并且只有前一个 Observable 终止（onComplete）才会订阅下一个 Observable
 */
public class RxCaseConcatActivity extends AppCompatActivity {
    private String TAG = "RxAct";
    private boolean isFromNet = false;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_case_concat);

//        先读取缓存再读取网络
        Observable<FoodList> cache = Observable.create(new ObservableOnSubscribe<FoodList>() {
            @Override
            public void subscribe(ObservableEmitter<FoodList> e) throws Exception {
                Log.e(TAG, "create当前线程:" + Thread.currentThread().getName());
                FoodList data = CacheManager.getInstance().getFoodListJsonData();

//                在操作符concat 中，只有调用onComplete 之后才会执行下一个Observable
                if (data != null) {//如果缓存数据不为空，则直接读取缓存数据，而不读取网络数据
                    isFromNet = false;
                    Log.e(TAG, "\nsubscribe: 读取缓存数据:");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "正在 : 读取缓存数据:");
                        }
                    });
                    e.onNext(data);
                } else {
                    isFromNet = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "正在 : 读取网络数据:");

                        }
                    });
                    Log.e(TAG, "\nsubscribe: 读取网络数据:");
                    e.onComplete();
                }
            }
        });

//        请求网络数据
        Observable<FoodList> network = Rx2AndroidNetworking.get("http://www.tngou.net/api/food/list")
                .addQueryParameter("rows", 10 + "")
                .build()
                .getObjectObservable(FoodList.class);

//        Concat 先读取缓存数据并展示UI再获取网络数据刷新UI
//        1、concat 可以做到不交错的发射两个甚至多个 Observable 的发射物;
//        2、并且只有前一个 Observable 终止（onComplete）才会订阅下一个 Observable

//        两个 Observable 的泛型应当保持一致
        Observable.concat(cache, network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FoodList>() {
                    @Override
                    public void accept(FoodList foodList) throws Exception {
                        Log.e(TAG, "subscribe 成功:" + Thread.currentThread().getName());
                        if (isFromNet) {
                            Log.e(TAG, "accept : 网络获取数据设置缓存: \n" + foodList.toString());
                            CacheManager.getInstance().setFoodListJsonData(foodList);
                        }
                        Log.e(TAG, "accept: 读取数据成功:" + foodList.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "subscribe 失败:" + Thread.currentThread().getName());
                        Log.e(TAG, "accept: 读取数据失败：" + throwable.getMessage());
                    }
                });

    }
}
