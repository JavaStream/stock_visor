package com.javastream.stock_visor.utils;

import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class JsoupUtil {

    private static final String PATTERN_INSTRUMENT = "[0-9]+";
    private static final String PATTERN_TICKER     = "\\([^)]*\\)";

    public static String extractInstrumentId(Element element) {
        String[] elements = element.data().split("instrument_id");
        String[] array = elements[1].split(",");
        String str = array[0];

        Matcher matcher = Pattern.compile(PATTERN_INSTRUMENT).matcher(str);
        if (matcher.find()) {
            return str.substring(matcher.start(), matcher.end());
        }

        return Strings.EMPTY;
    }

    public static String extractTicker(Document doc) {
        String title = doc.title();

        Matcher matcher = Pattern.compile(PATTERN_TICKER).matcher(title);
        if (matcher.find()) {
            String ticker = title.substring(matcher.start(), matcher.end());
            ticker = ticker.substring(1, ticker.length() - 1);
            return ticker;
        }

        return Strings.EMPTY;
    }
}
