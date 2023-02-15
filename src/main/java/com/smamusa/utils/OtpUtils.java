package com.smamusa.utils;

import com.smamusa.banks.Currency;
import com.smamusa.banks.otp.OtpCurrency;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * OTP Utils
 *
 * @author Ivan Bojanic
 */
public class OtpUtils {

    /**
     * Method used for converting list of td elements fetched from OTP webpage into grouped list of OtpCurrency objects
     *
     * @param tdElements td elements list
     * @return List of OtpCurrency objects
     * @throws ParseException
     */
    public static List<OtpCurrency> parseToOtpCurrency(List<String> tdElements) throws ParseException {
        List<String> elementsGroupedByCurrency = new ArrayList<>();
        List<OtpCurrency> otpCurrencies = new ArrayList<>();
        String currentElement = "";
        for (String tdElement : tdElements) {
            if (tdElement.matches("[a-zA-Z]+") && !currentElement.isEmpty()) {
                elementsGroupedByCurrency.add(currentElement);
                currentElement = "";
            }
            currentElement += tdElement + "\n";
        }
        if (!currentElement.isEmpty()) {
            elementsGroupedByCurrency.add(currentElement);
        }
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        for (String element : elementsGroupedByCurrency) {
            String[] data = element.split("\n");
            otpCurrencies.add(new OtpCurrency(Currency.valueOf(data[0]), data[1], Integer.parseInt(data[2]),
                    (Double) df.parse(data[3]), (Double) df.parse(data[7])));
        }
        return otpCurrencies;
    }
}
