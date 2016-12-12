package com.yulin.horizontaltimeline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private HorizontalTimeLine mHorizontalTimeLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHorizontalTimeLine = (HorizontalTimeLine)findViewById(R.id.id_time_line);
        Map<Float, Float> map = new HashMap<>();
        map.put(0f,2f);
        map.put(5.8f,15.7f);
        map.put(18f,24f);
        mHorizontalTimeLine.setSelectedMap(map);
    }
}
