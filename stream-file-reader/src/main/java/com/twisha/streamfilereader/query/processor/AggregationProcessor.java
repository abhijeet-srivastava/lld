package com.twisha.streamfilereader.query.processor;

import com.twisha.streamfilereader.helper.CSVReader;
import com.twisha.streamfilereader.model.CSVRow;
import com.twisha.streamfilereader.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregationProcessor implements QueryProcessor {
    @Override
    public List<String> processQuery(CSVReader reader, Query query) {
        Map<String, Double> aggregates = new HashMap<>();
        while (reader.hasNext()) {
            CSVRow row = reader.next();
            if (query.getFilter().test(row)) {
                String groupByValue = row.get(query.getGroupByColumn());
                double value = Double.parseDouble(row.get(query.getAggregateColumn()));
                aggregates.merge(groupByValue, value, Double::sum);
            }
        }
        List<String> results = new ArrayList<>();
        for(var entry: aggregates.entrySet()) {
            results.add(entry.getKey() + ":" + entry.getValue());
        }
        return  results;
    }
}
