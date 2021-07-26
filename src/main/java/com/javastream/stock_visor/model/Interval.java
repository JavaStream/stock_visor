package com.javastream.stock_visor.model;

public enum Interval {
    MIN_5("300"),
    MIN_30("1800"),
    HOUR_1("3600"),
    HOUR_4("14400"),
    DAY("86400"),
    WEEK("week"),
    MONTH("month");

    private String value;

    Interval(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Interval getByValue(String value){
        for(Interval in : Interval.values()){
            if(value.equals(in.value))
                return in;
        }
        return null;
    }



    @Override
    public String toString() {
        return "Interval{" +
                "value='" + value + '\'' +
                '}';
    }
}
