package com.javastream.stock_visor.utils;

import com.javastream.spliterator.Spliterators;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class JsoupUtil {

    private static final String PATTERN_INSTRUMENT = "[0-9]+";
    private static final String PATTERN_TICKER     = "\\([^)]*\\)";
    private static final String INSTRUMENT_ID_KEY = "instrument_id";
    private static final String COMMA_KEY = ",";

    public static String extractInstrumentId(Element element) {
        String result = new Spliterators(element.data())
                .split(INSTRUMENT_ID_KEY)
                .choice(1)
                .split(COMMA_KEY)
                .choice(0)
                .trim()
                .build();

        Matcher matcher = Pattern.compile(PATTERN_INSTRUMENT).matcher(result);
        if (matcher.find()) {
            return result.substring(matcher.start(), matcher.end());
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
