package com.samsung.galaxy_babies.obj;

import com.samsung.galaxy_babies.data.HeightData;
import com.samsung.galaxy_babies.data.WeightData;

import java.util.Calendar;
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
}
