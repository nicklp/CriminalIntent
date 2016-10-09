package com.nick.criminalintent.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.nick.criminalintent.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nick on 2016/9/9.
 */
public class TimePickerFragment extends DialogFragment {
    private TimePicker mTimePicker;
    private static final String EXTR_DATETIME = "dateTime";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_datetime,null);
        Date date = (Date) getArguments().getSerializable(EXTR_DATETIME);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_date_time_picker);
        mTimePicker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int ss = calendar.get(Calendar.SECOND);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok,null)
                .create();
    }

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTR_DATETIME,date);

        TimePickerFragment tpf = new TimePickerFragment();
        tpf.setArguments(args);
        return tpf;
    }
}
