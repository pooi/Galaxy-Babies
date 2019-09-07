package com.samsung.galaxy_babies.obj;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.samsung.galaxy_babies.R;
import com.samsung.galaxy_babies.data.HeightData;
import com.samsung.galaxy_babies.data.WeightData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class BabyData {

    // 1st	3rd	5th	10th	15th	25th	50th	75th	85th	90th	95th	97th	99th
    public static String[] header = {"1%", "3%", "5%", "10%", "15%", "25%", "50%", "75%", "85%", "90%", "95%", "97%", "99%"};


    public int getKgPercentileIndex(Baby.Gender gender, double kg, Calendar date){

        int age = getAge(date, Calendar.getInstance());

        double [] values;
        if(gender == Baby.Gender.MEN)
            values = WeightData.boy[age];
        else
            values = WeightData.girl[age];

        int index = 0;
        for(int i=0; i<values.length; i++){
            if(values[i] > kg)
                break;
            else
                index = i;
        }
        return index;

    }

    public int getCmPercentileIndex(Baby.Gender gender, double cm, Calendar date){

        int age = getAge(date, Calendar.getInstance());

        double [] values;
        if(gender == Baby.Gender.MEN)
            values = HeightData.boy[age];
        else
            values = HeightData.girl[age];

        int index = 0;
        for(int i=0; i<values.length; i++){
            if(values[i] > cm)
                break;
            else
                index = i;
        }
        return index;

    }


    // https://dayafterdaykikiai.tistory.com/145
    public static int getAge(Calendar c1, Calendar c2){ // 만나이 (개월)

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);

        return (int)(((year2-year1)*12)+(month2-month1)+((day2-day1)/30.4));
    }

    public double getBodyMassIndex(double kg, double cm){ // 체질량지수
        return kg / cm / cm * 10_000;
    }

    public static String[] getHeader() {
        return header;
    }
    public double getDummyWeight(Baby.Gender gender, Calendar date){
        Random random = new Random();
        int age = getAge(date, Calendar.getInstance());
        double [] values;
        if(gender == Baby.Gender.MEN)
            values = WeightData.boy[age];
        else
            values = WeightData.girl[age];
        return values[random.nextInt(values.length)];
    }
    public double getDummyHeight(Baby.Gender gender, Calendar date){
        Random random = new Random();
        int age = getAge(date, Calendar.getInstance());
        double [] values;
        if(gender == Baby.Gender.MEN)
            values = HeightData.boy[age];
        else
            values = HeightData.girl[age];
        return values[random.nextInt(values.length)];
    }

    public int getColorId(Context context, int id){
        return ContextCompat.getColor(context, id);
    }

    public int getDP(Context context, int dps){
        float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

    public void drawPerChart(Context context, LineChart chart, double [] list, int highlightIndex, final String unit){

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
        set1.setHighlightEnabled(true);
//        set1.setDrawHighlightIndicators(false);
//        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setHighLightColor(getColorId(context, R.color.red));
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
//        System.out.println(chart.getWidth() + " " +  chart.getHeight());
//        chart.highlightValue(chart.getHighlighter().getHighlight(highlightIndex-1, highlightIndex-1));
//        chart.highlightValue(highlightIndex, highlightIndex, false);
//        chart.animateX(1500);

        chart.invalidate(); // refresh

    }
}
