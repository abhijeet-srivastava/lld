package com.twisha.streamfilereader.service;

import com.twisha.streamfilereader.query.Query;
import com.twisha.streamfilereader.query.processor.AggregationProcessor;
import com.twisha.streamfilereader.query.processor.FilterProcessor;
import com.twisha.streamfilereader.query.processor.QueryProcessor;

public class QueryProcessorFactory {

    public static QueryProcessor getProcessor(Query query) {
        if (query.getAggregateFunction() != null) {
            return new AggregationProcessor();
        }
        return new FilterProcessor();
    }
}
