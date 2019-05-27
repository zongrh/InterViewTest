package cn.my.rxjavatest;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import cn.my.rxjavatest.test.*;
import cn.my.rxjavatest.utils.RvUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        RecyclerView rvRoot = findViewById(R.id.rl_root);

        RvUtils.setCommonRv(rvRoot, getClazz(), getTitles(), this);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    }

    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("RxJavaAct");
        titles.add("RxJavaAct2_create");
        titles.add("RxJavaAc3_map");
        titles.add("RxJavaAc4_zip");
        titles.add("RxJavaAc5_cancat");
        titles.add("RxJavaAct6_flatMap");
        titles.add("RxJavaAct7_concatMap");
        titles.add("RxJavaAct8_distinct");
        titles.add("RxJavaAct9_Filter");
        titles.add("RxJavaAct10_buffer");
        titles.add("RxJavaAct11_timer");
        titles.add("RxJavaAct12_interval");
        titles.add("RxJavaAct13_doOnNext");
        titles.add("RxJavaAct14_skip");
        titles.add("RxJavaAct15_take");
        titles.add("RxJavaAct16_just");
        titles.add("RxJavaAct17_Single");
        titles.add("RxJavaAct18_debounce");
        titles.add("RxJavaAct19_defer");
        titles.add("RxJavaAct20_last");
        titles.add("RxJavaAct21_merge");
        titles.add("RxJavaAct22_reduce");
        titles.add("RxJavaAct23_scan");
        titles.add("RxJavaAct24_window");

        return titles;
    }

    private List<Class> getClazz() {
        List<Class> listeners = new ArrayList<>();
        listeners.add(RxJavaAct.class);
//        create 操作符应该是最常见的操作符了，主要用于产生一个 Obserable 被观察者对象，
//        为了方便大家的认知，以后的教程中统一把被观察者 Observable 称为发射器（上游事件），
//        观察者 Observer 称为接收器（下游事件）。
        listeners.add(RxJavaAct2_create.class);
//        Map 基本算是 RxJava 中一个最简单的操作符了，熟悉 RxJava 1.x 的知道，
//        它的作用是对发射时间发送的每一个事件应用一个函数，是的每一个事件都按照指定的函数去变化，
//        而在 2.x 中它的作用几乎一致。
        listeners.add(RxJavaAct3_map.class);
//        zip 专用于合并事件，该合并不是连接（连接操作符后面会说），
//        而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
        listeners.add(RxJavaAct4_zip.class);
//        对于单一的把两个发射器连接成一个发射器，虽然 zip 不能完成，
//        但我们还是可以自力更生，官方提供的 concat 让我们的问题得到了完美解决。
        listeners.add(RxJavaAct5_cancat.class);
//        FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，
//        然后将它们发射的事件合并后放进一个单独的Observable里.
//        flatMap 并不能保证事件的顺序
        listeners.add(RxJavaAct6_flatMap.class);
//        FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，
//        然后将它们发射的事件合并后放进一个单独的Observable里.
//        concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序，
        listeners.add(RxJavaAct7_concatMap.class);
//        distinct 去重
        listeners.add(RxJavaAct8_distinct.class);
//        Filter 你会很常用的，它的作用也很简单，过滤器嘛。
//        可以接受一个参数，让其过滤掉不符合我们条件的值
        listeners.add(RxJavaAct9_Filter.class);
//        buffer 操作符接受两个参数，buffer(count,skip)，
//        作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，
//        然后生成一个  Observable 。
        listeners.add(RxJavaAct10_buffer.class);
//        timer 很有意思，相当于一个定时任务。在 1.x 中它还可以执行间隔逻辑，
//        但在 2.x 中此功能被交给了 interval，
        listeners.add(RxJavaAct11_timer.class);
//        interval 操作符用于间隔时间执行某个操作，其接受三个参数，
//        分别是第一次发送延迟，间隔时间，时间单位。
//        由于interval默认在新线程，所以我们应该切回主线程
//        关闭当前Activity时需要在onDestory中回收掉
        listeners.add(RxJavaAct12_interval.class);
//        doOnNext 它的作用是让订阅者在接收到数据之前干点有意思的事情
//        例如我们在获取到数据之前想先保存一下数据
        listeners.add(RxJavaAct13_doOnNext.class);
//        skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，
//        代表跳过 count 个数目开始接收。
        listeners.add(RxJavaAct14_skip.class);
//        take，接受一个 long 型参数 count ，代表至多接收 count 个数据。
        listeners.add(RxJavaAct15_take.class);
//        just  就是一个简单的发射器依次调用 onNext() 方法。
        listeners.add(RxJavaAct16_just.class);
//        Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
        listeners.add(RxJavaAct17_Single.class);
//      debounce 两个参数：时间数值，时间单位； 低于时间数值的发射时间都被去除了
//      去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。
        listeners.add(RxJavaAct18_debounce.class);
//        简单地时候就是每次订阅都会创建一个新的 Observable，
//        并且如果没有被订阅，就不会产生新的 Observable。
        listeners.add(RxJavaAct19_defer.class);
//        last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
        listeners.add(RxJavaAct20_last.class);
//        merge 顾名思义，熟悉版本控制工具的你一定不会不知道 merge 命令，
//        而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
//        注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
        listeners.add(RxJavaAct21_merge.class);
//        reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
        listeners.add(RxJavaAct22_reduce.class);
//        scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，
//        而 scan 会始终如一地把每一个步骤都输出。
        listeners.add(RxJavaAct23_scan.class);
//        window  按照实际划分窗口，将数据发送给不同的 Observable
        listeners.add(RxJavaAct24_window.class);


        return listeners;
    }
}


