package com.xdsjs.save.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.xdsjs.save.R;
import com.xdsjs.save.adapter.TimelineAdapter;
import com.xdsjs.save.bean.Bill;
import com.xdsjs.save.config.Global;
import com.xdsjs.save.controller.BaseController;
import com.xdsjs.save.controller.MyController;
import com.xdsjs.save.model.MyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by xdsjs on 2015/10/23.
 */
public class BillInfoFragment extends Fragment {

    private String mArgument;
    public static final String ARGUMENT = "argument";

    private View view;

    private TextView tvTotalOut, tvTotalIn;

    private MyModel myModel;

    private List<Bill> bills;

    private PieChart pieChart;

    private float totalIn;//总收入
    private float totalOut;//总支出
    private float totalMoney;//总金额

    HashMap<String, Float> map;

    private TimelineAdapter timelineAdapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mArgument = bundle.getString(ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_billinfo, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        myModel = ((MyController) BaseController.getInstance()).getMyModel();
        tvTotalOut = (TextView) view.findViewById(R.id.tvTotalOut);
        tvTotalIn = (TextView) view.findViewById(R.id.tvTotalIn);
        pieChart = (PieChart) view.findViewById(R.id.pie);

        if (mArgument.equals("0")) {
            bills = ((MyController) BaseController.getInstance()).getWeekBillList();
        } else if (mArgument.equals("1")) {
            bills = ((MyController) BaseController.getInstance()).getMonthBillList();
        } else {
            bills = ((MyController) BaseController.getInstance()).getYearBillList();
        }

        Log.e("---bills---bills-->", bills.toString());

        map = new HashMap<>();
        //计算总的支出和收入情况
        for (Bill bill : bills) {
            if (bill.getType().equals("0")) {
                //个人收入
                totalIn += Float.valueOf(bill.getMoney());
            } else {
                totalOut += Float.valueOf(bill.getMoney());
            }
            if (map.containsKey(bill.getType())) {
                map.put(bill.getType(), map.get(bill.getType()) + Float.valueOf(bill.getMoney()));
            } else {
                map.put(bill.getType(), Float.valueOf(bill.getMoney()));
            }
        }
        Log.e("map----->", map.toString());
        tvTotalIn.setText(String.valueOf(totalIn));
        tvTotalOut.setText(String.valueOf(totalOut));
        PieData mPieData = getPieData(map.size(), 100);
        showChart(pieChart, mPieData);

        //时光轴
        listView = (ListView) view.findViewById(R.id.list);
        timelineAdapter = new TimelineAdapter(this.getActivity(), bills);
        listView.setAdapter(timelineAdapter);
    }

    /**
     * 传入需要的参数，设置给arguments
     *
     * @param argument
     * @return
     */
    public static BillInfoFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        BillInfoFragment contentFragment = new BillInfoFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    //显示饼状图
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

//        pieChart.setDescription("测试饼状图");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("收入支出情况");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();

        Set set = map.keySet();
        Iterator iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String in = String.valueOf(iterator.next());
            int index = Integer.valueOf(in);
            xValues.add(Global.types[index]);
            yValues.add(new Entry(map.get(index + "") / (totalIn + totalOut), i));
            colors.add(Global.colors[index]);
            i++;
        }
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
