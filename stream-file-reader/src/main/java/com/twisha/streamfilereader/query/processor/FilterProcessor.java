package com.twisha.streamfilereader.query.processor;

import com.twisha.streamfilereader.helper.CSVReader;
import com.twisha.streamfilereader.model.CSVRow;
import com.twisha.streamfilereader.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FilterProcessor implements QueryProcessor {
    @Override
    public List<String> processQuery(CSVReader reader, Query query) {
        List<String> results = new ArrayList<>();
        while(reader.hasNext()) {
            CSVRow row = reader.next();
            if (query.getFilter().test(row)) {
                List<String> rowData = new ArrayList<>();
                for (String column : query.getSelectColumns()) {
                    rowData.add(row.get(column));
                }
                results.add(String.join(", ", rowData));
            }
        }
        return results;
    }
}
