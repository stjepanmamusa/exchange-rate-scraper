package com.smamusa.banks.otp;

import com.smamusa.banks.Currency;

public class OtpCurrency {
    private static int buyOffset = 3;
    private static int sellOffset = 7;

    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    private int code;
    private int unitAmount;

    private double buyRate;  //effective

    private double sellRate; //effective

    public OtpCurrency(Currency currency, int code, int unitAmount, double buyRate, double sellRate) {
        this.currency = currency;
        this.code = code;
        this.unitAmount = unitAmount;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public static int getBuyOffset() {
        return buyOffset;
    }

    public static void setBuyOffset(int buyOffset) {
        OtpCurrency.buyOffset = buyOffset;
    }


}
