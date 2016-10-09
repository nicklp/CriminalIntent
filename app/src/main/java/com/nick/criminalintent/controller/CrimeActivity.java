package com.nick.criminalintent.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID="com.nick.criminalintent.crime_id";
    public static final String REQUEST_CODE_STR = "request_code_str";
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        int position = getIntent().getIntExtra(REQUEST_CODE_STR,-1);
        return new CrimeFragment().newInstance(crimeId,position);
    }

    /**
     * 使用intent方式，附加extra方式存放信息，并传递到fragment
     * @param packageContext
     * @param crimeId
     * @return
     */
    public static Intent newIntent(Context packageContext, UUID crimeId,int position){
        Intent intent = new Intent(packageContext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        intent.putExtra(REQUEST_CODE_STR,position);
        return intent;
    }

}
