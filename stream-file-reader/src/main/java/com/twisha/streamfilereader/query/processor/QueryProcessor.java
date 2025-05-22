package com.twisha.streamfilereader.query.processor;

import com.twisha.streamfilereader.helper.CSVReader;
import com.twisha.streamfilereader.query.Query;

import java.util.List;

public interface QueryProcessor {
    List<String> processQuery(CSVReader reader, Query query);
}
