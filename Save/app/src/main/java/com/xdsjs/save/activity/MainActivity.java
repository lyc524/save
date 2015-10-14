package com.xdsjs.save.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xdsjs.save.R;
import com.xdsjs.save.utils.TimeUtils;


public class MainActivity extends BaseActivity {

    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setText(TimeUtils.getPrettyTime(Long.valueOf(TimeUtils.getFirstDayTimeOfWeek()))
                        + "\n" + TimeUtils.getPrettyTime(Long.valueOf(TimeUtils.getFirstDayTimeOfMonth()))
                        + "\n" + TimeUtils.getPrettyTime(Long.valueOf(TimeUtils.getFirstDayTimeOfYear()))
        );
    }
}
