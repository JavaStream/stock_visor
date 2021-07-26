package com.javastream.stock_visor.service;

import com.javastream.stock_visor.exception.ApiException;
import org.jsoup.nodes.Document;

public interface TickerService {
    String findTicker(Document document);
    String findInstrumentId(Document document);
    Document getDocument(String longTicker) throws ApiException;
}
