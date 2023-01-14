package com.smamusa;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.smamusa.banks.Currency;
import com.smamusa.banks.otp.OtpCurrency;
import com.smamusa.banks.otp.OtpScriptPreProcessor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("App started...");

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            WebClientOptions options = webClient.getOptions();
            webClient.setScriptPreProcessor(new OtpScriptPreProcessor());
            options.setJavaScriptEnabled(true);
            options.setThrowExceptionOnScriptError(false);
            options.setThrowExceptionOnFailingStatusCode(false);
            options.setCssEnabled(false);

            // OTP bank first
            final HtmlPage page = webClient.getPage("https://www.otpbanka.hr/tecajna-lista");
            webClient.waitForBackgroundJavaScript(500);

            List<String> rowData = page.getElementsByTagName("td").stream().map(td -> td.asNormalizedText()).collect(Collectors.toList());

            List<OtpCurrency> currencies = parseToOtpCurrency(rowData);

            System.out.printf("OTP Banka EUR cash buy/sell rates\n");
            System.out.printf("Currency\t Buy rate\t Sell rate\t");
            currencies.forEach(currency -> {
                System.out.printf("%s\t%f\t%f\t\n",currency.getCurrency(),currency.getBuyRate(),currency.getSellRate());
            });

        } catch (MalformedURLException e) {
            System.err.println("One of the used URLs was malformed");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static List<OtpCurrency> parseToOtpCurrency(List<String> tdElements) {
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

        for (String element : elements) {
            String[] data = element.split("\n");
            currencies.add(new OtpCurrency(Currency.valueOf(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Double.parseDouble(data[7])));
        }
        return currencies;
    }
}