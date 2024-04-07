package com.example.FruitTrees.WeatherProcessor.WeatherProcessors.Monthly;

import org.springframework.stereotype.Component;

/**
 *  A weather processor that calculates the total amount of some
 *  weather value above a certain value  for each month
 *
 */
@Component("HoursBelowMonthly")
public class HoursBelowMonthly extends MonthlyWeatherProcessor {
    /**
     * the counted hours
     */
    private double hours;
    /**
     * the max value
     */
    private double maxValue;
    public HoursBelowMonthly() {
        super("Hours Below Monthly");
    }
    @Override
    public void before() {
        super.before();
        if(inputParameters.size()<1){
            throw new IllegalArgumentException(" Params Array Size<1 You Must Include  The Maximum Value In The Array Of Parameters ");
        }
        this.maxValue = Double.parseDouble(inputParameters.get(0));
        values.clear();
    }

    @Override
    protected void onMonthEnd(Number value, String date) {
        String text="Monthly Hours Of " +dataType+  " Above "+ maxValue;
        if(inputParameters.size()>1) {
            String requestText = inputParameters.get(1);
            if (requestText != null) {
                text = requestText;
            }
        }
        monthlyValuesResponse.getValues().put(text, String.valueOf(hours));
        values.add(text+ " For "+ currentMonthName+" "+currentYear+" : "+ hours);
        monthlyValues.get(currentMonthName).add(hours);
        hours =0;
    }
    @Override
    protected void processWeatherBetween(Number data, String date) {
        double value=data.doubleValue();
        if( value<= maxValue) {
            hours++;
        }
    }
}