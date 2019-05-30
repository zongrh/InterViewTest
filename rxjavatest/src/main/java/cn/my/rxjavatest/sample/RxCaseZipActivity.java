package cn.my.rxjavatest.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cn.my.rxjavatest.R;
import cn.my.rxjavatest.model.CategoryResult;
import cn.my.rxjavatest.model.MobileAddress;
import cn.my.rxjavatest.net.Network;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 结合多个接口的数据再更新 UI
 * zip操作符的使用场景
 */
public class RxCaseZipActivity extends AppCompatActivity {
    String TAG = "RxAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_case_zip);

        Observable<MobileAddress> observable1 = Rx2AndroidNetworking.get("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                .build()
                .getObjectObservable(MobileAddress.class);

        Observable<CategoryResult> observable2 = Network.getGankApi()
                .getCategoryData("Android", 1, 1);

        Observable.zip(observable1, observable2, new BiFunction<MobileAddress, CategoryResult, String>() {
            @Override
            public String apply(MobileAddress mobileAddress, CategoryResult categoryResult) throws Exception {
                return "合并后的数据为：手机归属地："+mobileAddress.getResult().getMobilearea()+"人名："+categoryResult.toString();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "accept: 成功：" + s+"\n");

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "accept: 失败：" + throwable+"\n");
            }
        });

    }
}
