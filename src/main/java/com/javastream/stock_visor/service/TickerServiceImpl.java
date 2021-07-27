package com.javastream.stock_visor.service;

import com.google.common.base.Strings;
import com.javastream.stock_visor.exception.ApiException;
import com.javastream.stock_visor.utils.JsoupUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Objects;

@Service
public class TickerServiceImpl implements TickerService {

    private final Logger logger = LoggerFactory.getLogger(TickerServiceImpl.class);

    private final String BASE_URL = "https://ru.investing.com/equities/";
    private static final String DATA_ELEMENT = "__NEXT_DATA__";

    @Override
    public String findTicker(Document document) {
        Objects.requireNonNull(document, "Jsoup Document is null");
        return JsoupUtil.extractTicker(document);
    }

    @Override
    public String findInstrumentId(Document document) {
        Element element = document.getElementById(DATA_ELEMENT);
        Objects.requireNonNull(element, "Jsoup Element is null");
        return JsoupUtil.extractInstrumentId(element);
    }

    @Override
    public Document getDocument(String longTicker) throws ApiException {
        try {
            return Jsoup.connect(BASE_URL + longTicker).get();
        } catch (IOException e) {
            logger.error("Error while parsing data", e);
            throw new ApiException(e.getMessage());
        }
    }
}