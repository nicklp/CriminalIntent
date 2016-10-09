package com.nick.criminalintent.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Nick on 2016/8/24.
 */
public class CrimeFragment extends Fragment{
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mDateTimeButton;
    private CheckBox mSolvedCheckBox;
    private static final String ARG_CRIME_ID= "crime_id";
    private static final String MOD_POSITION = "mod_position";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_DATE_TIME = "DialogDateTime";
    private int position = -1;
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_DATE_TIME = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //position = getActivity().getIntent().getIntExtra(CrimeActivity.REQUEST_CODE_STR,0);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        position = getArguments().getInt(MOD_POSITION);

        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        returnResult();
    }

    public void returnResult(){
        Intent intent = new Intent();
        intent.putExtra(CrimeActivity.REQUEST_CODE_STR,position);
        getActivity().setResult(Activity.RESULT_OK,intent);
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
        mDateButton.setText(formatDate(mCrime.getDate()));
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //要讲DialogFragment添加给FragmentManager管理并放置到屏幕上，可调用show()方法，两种重载
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_DATE);//建立CrimeFragment和DatePickerFragment的联系
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mDateTimeButton = (Button) v.findViewById(R.id.crime_dateTime);
        Calendar time = Calendar.getInstance();
        time.setTime(mCrime.getDate());
        int hour = time.get(Calendar.HOUR);
        int min = time.get(Calendar.MINUTE);
        int ss = time.get(Calendar.SECOND);
        mDateTimeButton.setText(hour+":"+min+":"+ss);
        mDateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                TimePickerFragment fragment = TimePickerFragment.newInstance(mCrime.getDate());
                //fragment.setTargetFragment(CrimeFragment.this,REQUEST_DATE_TIME);
                fragment.show(fragmentManager,DIALOG_DATE_TIME);
            }
        });

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

    public static CrimeFragment newInstance(UUID crimeId,int position){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);
        args.putInt(MOD_POSITION,position);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(formatDate(mCrime.getDate()));
        }
    }

    private String formatDate(Date date){
        if(date == null) return "";
        return DateFormat.format("yyyy-MM-dd h:mmaa/EEEE",mCrime.getDate()).toString();
    }
}
