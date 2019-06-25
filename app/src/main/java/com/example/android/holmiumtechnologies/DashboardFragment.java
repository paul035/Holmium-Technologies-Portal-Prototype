package com.example.android.holmiumtechnologies;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HICondition;
import com.highsoft.highcharts.common.hichartsclasses.HICrosshair;
import com.highsoft.highcharts.common.hichartsclasses.HILabel;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIResponsive;
import com.highsoft.highcharts.common.hichartsclasses.HIRules;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class DashboardFragment extends Fragment{
    private TextView todayGen;
    private TextView totalGen;
    private TextView acPower;
    private TextView dcPower;

    private HIChartView chartView;
    private HIChartView chartView2;
    private HIOptions options;
    private HITitle title;
    private HIYAxis yaxis;
    private HILegend legend;
    private HIPlotOptions plotoptions;
    private HILine line1;
    private HIResponsive responsive;
    private HIRules rules1;

    private String newData1;
    private String newData2;
    private String newData3;
    private String newData4;


    MYViewModel myViewModel;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        todayGen = (TextView)view.findViewById(R.id.textView3);
        totalGen = (TextView)view.findViewById(R.id.textView5);
        acPower = (TextView)view.findViewById(R.id.textView7);
        dcPower = (TextView)view.findViewById(R.id.textView9);


        chartView = view.findViewById(R.id.hc);
        chartView2 = view.findViewById(R.id.hc1);

        options = new HIOptions();

        title = new HITitle();

        yaxis = new HIYAxis();

        legend = new HILegend();

        plotoptions = new HIPlotOptions();

        line1 = new HILine();

        responsive = new HIResponsive();

        rules1 = new HIRules();

        myViewModel = ViewModelProviders.of(getActivity()).get(MYViewModel.class);
        myViewModel.getData1().observe(this, new Observer <String>() {
            @Override
            public void onChanged(@Nullable String s) {
                todayGen.setText(s + " kw");
                newData1 = s;
            }
        });
        myViewModel.getData2().observe(this, new Observer <String>() {
            @Override
            public void onChanged(@Nullable String s) {
                totalGen.setText(s + " kw");
                newData2 = s;

            }
        });
        myViewModel.getData3().observe(this, new Observer <String>() {
            @Override
            public void onChanged(@Nullable String s) {
                acPower.setText(s + " kw");
                newData3 = s;

            }
        });
        myViewModel.getData4().observe(this, new Observer <String>() {
            @Override
            public void onChanged(@Nullable String s) {
                dcPower.setText(s + " kw");
                newData4 = s;
                DrawNewGraph(newData1, newData2, newData3, newData4);
                DrawBarGraph(newData1, newData2, newData3, newData4);
            }
        });

        return view;
    }

    private void DrawNewGraph(String a, String b, String c, String d) {
        title.setText("Plant: Line Chart Detail");
        options.setTitle(title);


        yaxis.setTitle(new HITitle());
        yaxis.getTitle().setText("Plant Detail");
        options.setYAxis(new ArrayList<>(Collections.singletonList(yaxis)));

        options.setYAxis(new ArrayList <HIYAxis>() {{
            add(yaxis);
        }});


        legend.setLayout("vertical");
        legend.setAlign("right");
        legend.setVerticalAlign("middle");
        options.setLegend(legend);


        plotoptions.setSeries(new HISeries());
        plotoptions.getSeries().setLabel(new HILabel());
        plotoptions.getSeries().getLabel().setConnectorAllowed(false);
        plotoptions.getSeries().setPointStart(1);
        options.setPlotOptions(plotoptions);


        line1.setName("Data");
        line1.setData(new ArrayList <>(Arrays.asList(Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c), Integer.parseInt(d))));


        rules1.setCondition(new HICondition());
        rules1.getCondition().setMaxWidth(1000);
        HashMap <String, HashMap> chartLegend = new HashMap <>();
        HashMap <String, String> legendOptions = new HashMap <>();
        legendOptions.put("layout", "horizontal");
        legendOptions.put("align", "center");
        legendOptions.put("verticalAlign", "bottom");
        chartLegend.put("legend", legendOptions);
        rules1.setChartOptions(chartLegend);
        responsive.setRules(new ArrayList <>(Collections.singletonList(rules1)));
        options.setResponsive(responsive);

        options.setSeries(new ArrayList <>(Arrays.asList(line1)));

        chartView.setOptions(options);
    }

    private void DrawBarGraph(String a, String b, String c, String d) {

        HIOptions options = new HIOptions();

        HIChart chart = new HIChart();
        chart.setType("column");
        options.setChart(chart);

        HITitle title = new HITitle();
        title.setText("Plant: Bar Chart Detail");
        options.setTitle(title);


        HIXAxis xAxis = new HIXAxis();
        String[] categoriesList = new String[]{"Data1", "Data2", "Data3", "Data4"};
        xAxis.setCategories(new ArrayList <>(Arrays.asList(categoriesList)));
        xAxis.setCrosshair(new HICrosshair());
        options.setXAxis(new ArrayList <HIXAxis>() {{
            add(xAxis);
        }});

        HIYAxis yAxis = new HIYAxis();
        yAxis.setMin(0);
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText("Plant Detail");
        options.setYAxis(new ArrayList <HIYAxis>() {{
            add(yAxis);
        }});

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
        plotOptions.getColumn().setPointPadding(0.2);
        plotOptions.getColumn().setBorderWidth(0);
        options.setPlotOptions(plotOptions);

        HIColumn series1 = new HIColumn();
        series1.setName("Values");
        Number[] series1_data = new Number[]{Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c), Integer.parseInt(d)};
        series1.setData(new ArrayList <>(Arrays.asList(series1_data)));

        options.setSeries(new ArrayList <>(Arrays.asList(series1)));
        chartView2.setOptions(options);

    }

}