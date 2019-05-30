package cn.my.rxjavatest.sample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import cn.my.rxjavatest.R;
import cn.my.rxjavatest.model.FoodList;
import com.jakewharton.rxbinding2.view.RxView;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class RxCaseDebounceActivity extends AppCompatActivity {
    private String TAG = "RxAct";

    private Button btn_debounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_case_debounce);

        btn_debounce = findViewById(R.id.btn_debounce);

//        减少频繁的网络请求
        RxView.clicks(btn_debounce)
                .debounce(2, TimeUnit.SECONDS) // 过滤掉发射频率小于2秒的发射事件
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        clickBtn();
                    }
                });

    }

    private void clickBtn() {
        Rx2AndroidNetworking.get("http://www.tngou.net/api/food/list")
                .addQueryParameter("rows", 2 + "") // 只获取两条数据
                .build()
                .getObjectObservable(FoodList.class)
                .subscribeOn(Schedulers.io())  // 在 io 线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程进行更新UI等操作
                .subscribe(new Consumer<FoodList>() {
                    @Override
                    public void accept(@NonNull FoodList foodList) throws Exception {
                        Log.e(TAG, "accept: 获取数据成功:" + foodList.toString() + "\n");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: 获取数据失败：" + throwable.getMessage() + "\n");
                    }
                });
    }
}
