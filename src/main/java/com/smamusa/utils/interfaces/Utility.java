package com.smamusa.utils.interfaces;

import java.util.List;

public interface Utility {
    List<String> scrapeData(String url);
    List<?> parseRawData(List<String> tdElements);
    String outputData(List<?> parsedData);
}
