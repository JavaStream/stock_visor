package com.javastream.stock_visor.model;

public enum ChartType {
    BARS("bars"),
    CANDLES("candles"),
    HOLLOW_CANDLES("hollow_candles"),
    LINE("line"),
    AREA("area"),
    HEIKEN_ASHI("heiken_ashi");

    private String value;

    ChartType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ChartType getByValue(String value){
        for(ChartType in : ChartType.values()){
            if(value.equals(in.getValue()))
                return in;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ChartType{" +
                "value='" + value + '\'' +
                '}';
    }
}
