package cn.my.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import cn.my.rxjavatest.utils.RvUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView rvRoot = findViewById(R.id.rl_root);
        RvUtils.setCommonRv(rvRoot, getClazz(), getTitles(), this);


    }
    private List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add("操作符");
        titles.add("例子");

        return titles;
    }
    private List<Class> getClazz() {
        List<Class> listeners = new ArrayList<>();
        listeners.add(OperatorActivity.class);
        listeners.add(SampleActivity.class);
        return listeners;
    }



}
