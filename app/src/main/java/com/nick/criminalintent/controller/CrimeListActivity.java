package com.nick.criminalintent.controller;

import android.support.v4.app.Fragment;

/**
 * Created by Nick on 2016/8/30.
 */
public class CrimeListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


}
