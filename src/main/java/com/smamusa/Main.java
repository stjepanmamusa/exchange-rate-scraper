package com.smamusa;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smamusa.banks.Currency;
import com.smamusa.banks.otp.OtpCurrency;
import com.smamusa.utils.OtpUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.smamusa.utils.LoggingUtils.disableNonAppLoggers;

public class Main {
    public static void main(String[] args) {

        disableNonAppLoggers();

        System.out.println("App started...");


        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            WebClientOptions options = webClient.getOptions();
            options.setThrowExceptionOnScriptError(false);
            options.setUseInsecureSSL(true);
            options.setJavaScriptEnabled(true);
            options.setCssEnabled(false);

            // OTP bank first
            final HtmlPage page = webClient.getPage("https://www.otpbanka.hr/tecajna-lista");
            webClient.waitForBackgroundJavaScript(1000);

            List<String> rowData = page.getElementsByTagName("td").stream().map(DomNode::asNormalizedText).collect(Collectors.toList());

            List<OtpCurrency> currencies = OtpUtils.parseToOtpCurrency(rowData);

            System.out.print("OTP Banka EUR cash buy/sell rates\n");
            System.out.printf("%-15s %-15s %-15s\n", "Currency", "Buy rate", "Sell rate");
            currencies.forEach(currency -> {
                System.out.printf("%-15s %-15f %-15f\n", currency.getCurrency(), currency.getBuyRate(), currency.getSellRate());
            });

        } catch (MalformedURLException e) {
            System.err.println("One of the used URLs was malformed");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.err.println("Error parsing exchange rates, check your received data");
        }
    }




}
