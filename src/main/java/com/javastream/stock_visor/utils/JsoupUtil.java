package com.javastream.stock_visor.utils;

import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupUtil {

    public static String extractInstrumentId(Element element) {
        String instrumentId;
        String[] instrument_ids = element.data().split("instrument_id");
        String[] split = instrument_ids[1].split(",");
        String str = split[0];

        String pattern = "[0-9]+";
        Matcher matcher = Pattern.compile(pattern).matcher(str);
        if (matcher.find()) {
            instrumentId = str.substring(matcher.start(), matcher.end());
            return instrumentId;
        }

        return Strings.EMPTY;
    }

    public static String extractTicker(Document doc) {
        String ticker;
        String title = doc.title();

        String pattern = "\\([^)]*\\)";
        Matcher matcher = Pattern.compile(pattern).matcher(title);
        if (matcher.find()) {
            ticker = title.substring(matcher.start(), matcher.end());
            ticker = ticker.substring(1, ticker.length() - 1);
            return ticker;
        }

        return Strings.EMPTY;
    }
}
