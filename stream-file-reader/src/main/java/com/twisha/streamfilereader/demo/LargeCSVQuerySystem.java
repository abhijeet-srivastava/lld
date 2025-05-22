package com.twisha.streamfilereader.demo;

import com.twisha.streamfilereader.helper.CSVReader;
import com.twisha.streamfilereader.query.Query;
import com.twisha.streamfilereader.query.processor.QueryProcessor;
import com.twisha.streamfilereader.service.ConsoleResultHandler;
import com.twisha.streamfilereader.service.QueryProcessorFactory;
import com.twisha.streamfilereader.service.ResultObserver;

import java.io.IOException;
import java.util.List;

public class LargeCSVQuerySystem {
    public static void main(String[] args) throws IOException {
        String filePath = "large_file.csv";
        Query query = new Query.Builder()
                .select("name", "age")
                .where(row -> Integer.parseInt(row.get("age")) > 30)
                .groupBy("name")
                .aggregate("SUM", "salary")
                .build();

        try (CSVReader reader = new CSVReader(filePath)) {
            QueryProcessor processor = QueryProcessorFactory.getProcessor(query);
            List<String> results = processor.processQuery(reader, query);
            ResultObserver observer = new ConsoleResultHandler();
            observer.onResult(results);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
