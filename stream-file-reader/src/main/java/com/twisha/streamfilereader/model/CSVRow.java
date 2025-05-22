package com.twisha.streamfilereader.model;

import java.util.HashMap;
import java.util.Map;

public class CSVRow {
    private final Map<String, String> data;
    private final String[] headers;

    public CSVRow(String[] headers, String[] values) {
        this.headers = headers;
        this.data = new HashMap<>();
        for (int i = 0; i < headers.length && i < values.length; i++) {
            data.put(headers[i], values[i]);
        }
    }
    public String get(String column) {
        return data.getOrDefault(column, "");
    }
}
