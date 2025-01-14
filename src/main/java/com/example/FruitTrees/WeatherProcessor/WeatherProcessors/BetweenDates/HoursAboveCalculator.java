package com.example.FruitTrees.WeatherProcessor.WeatherProcessors.BetweenDates;

import com.example.FruitTrees.WeatherConroller.WeatherResponse.YearlyValuesResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *  A weather processor that calculates the total amount of some
 *  weather value above a certain value  and between dates
 *
 */
@Component("HoursAbove")
public class HoursAboveCalculator extends ProcessWeatherBetweenDates {
    /**
     * the counted hours
     */
    private double hours;
    /**
     * the min value
     */
    private double minValue;
    public HoursAboveCalculator() {
    }
    @Override
    public void before() {
        if(inputParameters.isEmpty()){
            throw new IllegalArgumentException("Parameter");
        }
        this.processorName="Hours Above "+inputParameters.get(0);

        this.minValue = Double.parseDouble(inputParameters.get(0));
        values.clear();
        yearlyDataValues.clear();
    }

    @Override
    protected void onEndDate(String date) {
        LocalDateTime localDateTime=LocalDateTime.parse(date);
        int year= localDateTime.getYear();
        super.yearlyDataValues.add(hours);
       YearlyValuesResponse yearlyValuesResponse = locationWeatherResponse.getYearlyValues(String.valueOf(year));
        String text="Hours Of " +dataType+  " Above "+minValue;
        if(inputParameters.size()>1) {
            String requestText = inputParameters.get(1);
            if (requestText != null) {
                text = requestText;
            }
        }
        yearlyValuesResponse.getValues().put(text, String.valueOf(hours));
        if(!isOnlyCalculateAverage()) {
            values.add(text + year + " from: " + startMonth + "/" + startDay + " to " + endMonth + "/" + endDay + ": " + hours);
        }
        hours =0;
    }
    @Override
    void processWeatherBetween(Number data, String date) {
        double value=data.doubleValue();
        if( value>= minValue) {
            hours++;
        }
    }
}
