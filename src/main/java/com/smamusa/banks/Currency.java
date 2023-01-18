package com.smamusa.banks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Currency {
    AUD("AUD"),
    USD("USD"),
    CAD("CAD"),
    CZK("CZK"),
    HUF("HUF"),
    JPY("JPY"),
    NOK("NOK"),
    SEK("SEK"),
    DKK("DKK"),
    CHF("CHF"),
    GBP("GBP"),
    BAM("BAM"),
    PLN("PLN"),
    RUB("RUB"),
    RSD("RSD"),
    RON("RON"),
    BGN("BGN");

    private final String text;

    /**
     * @param text
     */
    Currency(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static List<String> getCurrencies(){
        return Arrays.asList(Currency.values()).stream().map(el -> el.toString())
                .collect(Collectors.toList());
    }
}

