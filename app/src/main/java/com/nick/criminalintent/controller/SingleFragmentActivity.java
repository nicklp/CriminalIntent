package com.nick.criminalintent.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nick.criminalintent.R;

/**
 * Created by Nick on 2016/8/30.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    /**
     * 此处为了通用，将不同的fragment抽象，返回其父类型
     * @return
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        /**
         * FragmentManager负责管理fragment并将他们的视图添加到activity的视图结构中
         */
        FragmentManager fm = getSupportFragmentManager();
        //如果不考虑版本兼容性问题，可以直接继承Activity类并调用getFragmentManager()方法
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);//根据fragment视图结构的id实例化fragment
        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();//创建一个新的fragment事务，加入一个添加操作，然后提交该事务
        }
    }
}
