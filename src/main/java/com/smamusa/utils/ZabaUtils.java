package com.smamusa.utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smamusa.banks.Currency;
import com.smamusa.banks.otp.OtpCurrency;
import com.smamusa.banks.zaba.ZabaCurrency;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ZABA Utils
 *
 * @author Ivan Bojanic
 */
public class ZabaUtils {

    /**
     * Method used for converting list of td elements fetched from ZABA webpage into grouped list of OtpCurrency objects
     *
     * @param tdElements td elements list
     * @return List of ZabaCurrency objects
     * @throws ParseException
     */
    public static List<ZabaCurrency> parseToZabaCurrency(List<String> tdElements) throws ParseException {
        List<String> elementsGroupedByCurrency = new ArrayList<>();
        List<ZabaCurrency> zabaCurrencies = new ArrayList<>();
        String currentElement = "";
        for (String tdElement : tdElements) {
            if(tdElement.matches("[a-zA-Z]+") && !Currency.getCurrencies().contains(tdElement)){
                if(elementsGroupedByCurrency.isEmpty()){
                    continue;
                }else{
                    break;
                }
            }
            if(Currency.getCurrencies().contains(tdElement) && !currentElement.isEmpty()){
                elementsGroupedByCurrency.add(currentElement);
                currentElement = "";
            }
            currentElement += tdElement + "\n";
        }
        if (!currentElement.isEmpty()) {
            elementsGroupedByCurrency.add(currentElement);
        }
        for(String el : elementsGroupedByCurrency){
            System.out.println(el);
        }

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        for (String element : elementsGroupedByCurrency) {
            String[] data = element.split("\n");
            zabaCurrencies.add(new ZabaCurrency(Currency.valueOf(data[0]), Integer.parseInt(data[1]),
                    (Double) df.parse(data[2]), (Double) df.parse(data[4])));
        }
        return zabaCurrencies;
    }
}
