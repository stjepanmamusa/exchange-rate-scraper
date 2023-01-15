package com.smamusa;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smamusa.banks.Currency;
import com.smamusa.banks.otp.OtpCurrency;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("App started...");

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            WebClientOptions options = webClient.getOptions();
            options.setThrowExceptionOnScriptError(false);
            options.setJavaScriptEnabled(true);
            options.setCssEnabled(false);

            // OTP bank first
            final HtmlPage page = webClient.getPage("https://www.otpbanka.hr/tecajna-lista");
            webClient.waitForBackgroundJavaScript(500);

            List<String> rowData = page.getElementsByTagName("td").stream().map(DomNode::asNormalizedText).collect(Collectors.toList());

            List<OtpCurrency> currencies = parseToOtpCurrency(rowData);

            System.out.print("OTP Banka EUR cash buy/sell rates\n");
            System.out.printf("%-15s %-15s %-15s\n","Currency", "Buy rate", "Sell rate");
            currencies.forEach(currency -> {
                System.out.printf("%-15s %-15f %-15f\n",currency.getCurrency(),currency.getBuyRate(),currency.getSellRate());
            });

        } catch (MalformedURLException e) {
            System.err.println("One of the used URLs was malformed");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ParseException e) {
            System.err.println("Error parsing exchange rates, check your received data");
        }
    }


    public static List<OtpCurrency> parseToOtpCurrency(List<String> tdElements) throws ParseException {
        List<String> elements = new ArrayList<>();
        List<OtpCurrency> currencies = new ArrayList<>();

        String dataElement = "";

        for (int x = 0; x < tdElements.size(); x++) {

            if (x > 0 && (tdElements.get(x).matches("[a-zA-Z]+") || x + 1 == tdElements.size())) {
                elements.add(dataElement);
                dataElement = "";
            }
            dataElement += tdElements.get(x) + "\n";
        }

        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        for (String element : elements) {
            String[] data = element.split("\n");
            currencies.add(new OtpCurrency(Currency.valueOf(data[0]), data[1], Integer.parseInt(data[2]), (Double) df.parse(data[3]), (Double) df.parse(data[7])));
        }
        return currencies;
    }
}