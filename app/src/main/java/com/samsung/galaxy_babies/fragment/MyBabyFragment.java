package com.samsung.galaxy_babies.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.samsung.galaxy_babies.MainActivity;
import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.data.HeightData;
import com.samsung.galaxy_babies.data.WeightData;
import com.samsung.galaxy_babies.obj.Baby;
import com.samsung.galaxy_babies.obj.BabyData;
import com.samsung.galaxy_babies.obj.Measure;
import com.samsung.galaxy_babies.obj.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyBabyFragment extends BaseFragment {
    // UI
    private View view;
    private Context context;

    private TextView tv_name;
    private TextView tv_description;
    private CircleImageView profileImg;
    private LinearLayout li_babies;
    private GridLayout gl_growth;

    private TextView tv_weight;
    private LineChart chartWeight;
    private TextView tv_height;
    private LineChart chartHeight;
    private TextView tv_weight_per;
    private LineChart chartWeightPer;
    private TextView tv_height_per;
    private LineChart chartHeightPer;

    private User user;
    private Baby selectedBaby;
    private BabyData babyData = new BabyData();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // UI
        view = inflater.inflate(R.layout.fragment_mybaby, container, false);
        context = container.getContext();

        user = ((MainActivity)getActivity()).getUser();
        selectedBaby = user.getBabies().get(0);

        init();

        updatePage();

        return view;

    }


    public void init(){

        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        profileImg = (CircleImageView) view.findViewById(R.id.profile_image);
        li_babies = (LinearLayout) view.findViewById(R.id.li_babies);
        gl_growth = (GridLayout) view.findViewById(R.id.gl_growth);

        tv_weight = (TextView) view.findViewById(R.id.tv_weight);
        chartWeight = (LineChart) view.findViewById(R.id.chart_weight);
        tv_height = (TextView) view.findViewById(R.id.tv_height);
        chartHeight = (LineChart) view.findViewById(R.id.chart_height);
        tv_weight_per = (TextView) view.findViewById(R.id.tv_weight_per);
        chartWeightPer = (LineChart) view.findViewById(R.id.chart_weight_per);
        tv_height_per = (TextView) view.findViewById(R.id.tv_height_per);
        chartHeightPer = (LineChart) view.findViewById(R.id.chart_height_per);


        for(Baby baby : user.getBabies()){
            final Baby b = baby;
            CircleImageView imageView = new CircleImageView(context);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    getDP(context, 40),
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(getDP(context, 5),0,0,0); // left, top, right, bottom
            imageView.setLayoutParams(params);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBorderWidth(1);
            imageView.setBorderColor(getColorId(context, R.color.white));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedBaby = b;
                    updatePage();
                }
            });

            Picasso.get().load(baby.getProfileImg()).into(imageView);

            li_babies.addView(imageView);
        }

        for(int i=0; i<12; i++){ // for gl_growth

        }

    }

    public void updatePage(){

        if(selectedBaby != null){
            tv_name.setText(selectedBaby.getName());
            tv_description.setText(selectedBaby.getDescription());
            Picasso.get().load(selectedBaby.getProfileImg()).into(profileImg);


            tv_weight.setText(selectedBaby.getWeightString());
            drawMeasureChart(chartWeight, selectedBaby.getWeights(), "kg");

            tv_height.setText(selectedBaby.getHeightString());
            drawMeasureChart(chartHeight, selectedBaby.getHeights(), "cm");

            int wperIndex = babyData.getKgPercentileIndex(selectedBaby.getGender(), selectedBaby.getLastWeight(), selectedBaby.getBirthday());
            tv_weight_per.setText(babyData.getHeader()[wperIndex]);
            drawPerChart(chartWeightPer, HeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday()), wperIndex, "kg");

            int hperIndex = babyData.getCmPercentileIndex(selectedBaby.getGender(), selectedBaby.getLastHeight(), selectedBaby.getBirthday());
            tv_height_per.setText(babyData.getHeader()[hperIndex]);
            drawPerChart(chartHeightPer, HeightData.getValues(selectedBaby.getGender(), selectedBaby.getBirthday()), hperIndex, "cm");

        }

    }

    public void drawMeasureChart(LineChart chart, ArrayList<Measure> list, final String unit){

        List<Entry> entries = new ArrayList<>();
        for (int i=0; i<Math.min(10, list.size()); i++) {
            Measure data = list.get(i);
            entries.add(new Entry(data.getDate().getTimeInMillis(), (float)data.getValue()));
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setCenterAxisLabels(false);
        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("M월 d일", Locale.KOREA);

            @Override
            public String getFormattedValue(float value) {
                return mFormat.format(new Date((long)value));
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        LineDataSet set1 = new LineDataSet(entries, "");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(getColorId(context, R.color.orange));
        set1.setValueTextColor(getColorId(context, R.color.orange));
        set1.setLineWidth(2f);
        set1.setCircleColor(getColorId(context, R.color.orange));
        set1.setDrawCircles(true);
        set1.setDrawValues(true);
//                set1.setFillAlpha(65);
//                set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(getColorId(context, R.color.dark_gray));
        set1.setDrawCircleHole(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(getColorId(context, R.color.dark_gray));
        data.setValueTextSize(9f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f%s", value, unit);
            }
        });
        data.setValueTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

        // set data
        chart.setData(data);
        chart.setBorderColor(getColorId(context, R.color.dark_gray));
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.invalidate(); // refresh

    }

    public void drawPerChart(LineChart chart,double [] list, int highlightIndex, final String unit){

        final String [] header = BabyData.getHeader();

        List<Entry> entries = new ArrayList<>();
        for (int i=0; i<list.length; i++) {
            entries.add(new Entry((float)i, (float)list[i]));
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setCenterAxisLabels(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return header[(int)value];
            }
        });

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        LineDataSet set1 = new LineDataSet(entries, "");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(getColorId(context, R.color.red));
        set1.setValueTextColor(getColorId(context, R.color.red));
        set1.setLineWidth(2f);
        set1.setCircleColor(getColorId(context, R.color.red));
        set1.setDrawCircles(true);
        set1.setDrawValues(true);
//                set1.setFillAlpha(65);
//                set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(getColorId(context, R.color.dark_gray));
        set1.setDrawCircleHole(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.getEntryForIndex(highlightIndex).setIcon(context.getDrawable(R.drawable.baseline_where_to_vote_24_red));
        set1.setIconsOffset(MPPointF.getInstance(0, -25));

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(getColorId(context, R.color.dark_gray));
        data.setValueTextSize(9f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f%s", value, unit);
            }
        });
        data.setValueTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

        // set data
        chart.setData(data);
        chart.setBorderColor(getColorId(context, R.color.dark_gray));
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.invalidate(); // refresh

    }

}
