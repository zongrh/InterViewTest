package cn.my.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import cn.my.rxjavatest.sample.*;
import cn.my.rxjavatest.utils.RvUtils;

import java.util.ArrayList;
import java.util.List;


public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        RecyclerView rvRoot = findViewById(R.id.rl_root);
        RvUtils.setCommonRv(rvRoot, getClazz(), getTitles(), this);

    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("采用 OkHttp3 配合 map , doOnNext , 线程切换做简单的网络请求\n" +
                "1、通过 Observable.create() 方法，调用 OkHttp 网络请求;\n" +
                "2、通过 map 操作符结合 Gson , 将 Response 转换为 bean 类;\n" +
                "3、通过 doOnNext() 方法，解析 bean 中的数据，并进行数据库存储等操作;\n" +
                "4、调度线程，在子线程进行耗时操作任务，在主线程更新 UI;\n" +
                "5、通过 subscribe(),根据请求成功或者失败来更新 UI。");
        titles.add("使用框架 rx2-Networking\n" +
                "1、通过 Rx2AndroidNetworking 的 get() 方法获取 Observable 对象(已解析)；\n" +
                "2、调度线程，根据请求结果更新 UI。");

        titles.add("zip 使用场景\n" +
                "结合多个接口的数据再更新 UI\n" +
                "zip 操作符可以把多个 Observable 的数据接口成一个数据源再发出去");

        titles.add("flatMap 使用场景\n" +
                "多个网络请求依次依赖\n" +
                "1、注册用户前先通过接口A获取当前用户是否已注册，再通过接口B注册;\n" +
                "2、注册后自动登录，先通过注册接口注册用户信息，注册成功后马上调用登录接口进行自动登录。");

        titles.add("concat 使用场景\n" +
                "先读取缓存数据再读取网络请求,\n" +
                "实用场景中经常会用到缓存数据，以通过减少频繁的网络请求达到节约流量：\n" +
                "1、concat 可以做到不交错的发射两个甚至多个 Observable 的发射物;\n" +
                "2、并且只有前一个 Observable 终止（onComplete）才会订阅下一个 Observable");

        titles.add("debounce 使用场景\n" +
                "减少频繁的网络请求\n" +
                "设想情景：输入框数据变化或者点击一次按钮时就要进行网络请求，这样会产生大量的网络请求，而实际上又不需要，\n" +
                "这时候可以通过 debounce 过滤掉发射频率过快的请求。");


        titles.add("interval 使用场景\n" +
                "间隔任务实现心跳\n" +
                "可能我们会遇上各种即时通讯，如果是自己家开发的 IM 即时通讯，我相信在移动端一定少不了心跳包的管理，\n" +
                "而我们 RxJava 2.x 的 interval 操作符棒我们解决了这个问题。");


        titles.add("线程调度需要注意的\n" +
                "RxJava 内置的线程调度器的确可以让我们的线程切换得心应手，但其中也有些需要注意的地方。\n" +
                "- 简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn指定的就是订阅者接收事件的线程。\n" +
                "- 多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。\n" +
                "- 但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。");

        return titles;
    }

    private List<Class> getClazz() {
        List<Class> listeners = new ArrayList<>();
        listeners.add(RxNetSingleActivity.class);
        listeners.add(RxNetworkActivity.class);
        listeners.add(RxCaseZipActivity.class);
        listeners.add(RxCaseFlatMapActivity.class);
        listeners.add(RxCaseConcatActivity.class);
        listeners.add(RxCaseDebounceActivity.class);
        listeners.add(RxCaseIntervalActivity.class);
        listeners.add(RxThreadActivity.class);


        return listeners;
    }

}
