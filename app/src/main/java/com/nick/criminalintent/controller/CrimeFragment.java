package com.nick.criminalintent.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.nick.criminalintent.R;
import com.nick.criminalintent.domain.Crime;
import com.nick.criminalintent.domain.CrimeLab;
import com.nick.criminalintent.tools.DebugTags;

import java.util.UUID;

/**
 * Created by Nick on 2016/8/24.
 */
public class CrimeFragment extends Fragment{
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private static final String ARG_CRIME_ID= "crime_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        Log.i(DebugTags.getInstance().getValueByKey(null),"onCreate(),title:"+mCrime.getTitle());
        //Log.i(TAG,TAG+"->onCreate()");
    }

    /**
     * 该方法实例化fragment视图的布局，然后将实例化的View返回给托管的activity
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.i(TAG,TAG+"->onCreateView()");
        //第一个参数是fragment的资源ID，第二个参数是视图的父视图，第三个参数告知布局生成器是否将生成的视图添加到父视图
        View v = inflater.inflate(R.layout.fragment_crime,container,false);//设置false，以activity代码的方式添加视图
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        //赋值
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());//editText发生变动后，则将变动后的字符串重新显示到editText
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(DateFormat.format("yyyy-MM-dd h:mmaa/EEEE",mCrime.getDate()).toString());
        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*
    * activity的FragmentManager负责调用队里中fragment的生命周期方法。
    * */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.i(TAG,TAG+"->onAttach()");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(TAG,TAG+"->onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.i(TAG,TAG+"->onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG,TAG+"->onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.i(TAG,TAG+"->onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.i(TAG,TAG+"->onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i(TAG,TAG+"->onDestroy()");
    }
}
