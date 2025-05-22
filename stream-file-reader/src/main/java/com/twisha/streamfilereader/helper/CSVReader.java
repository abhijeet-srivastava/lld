package com.twisha.streamfilereader.helper;

import com.twisha.streamfilereader.model.CSVRow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CSVReader implements Iterator<CSVRow>, AutoCloseable {

    private final BufferedReader reader;
    private final String[] headers;
    private String nextLine;
    //private final int bufferSize = 10000; // Buffer 10,000 rows

    public CSVReader(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
        headers = reader.readLine().split(",");
        nextLine = reader.readLine();
    }
    @Override
    public void close() throws Exception {
        this.reader.close();
    }

    @Override
    public boolean hasNext() {
        return Objects.nonNull(this.nextLine);
    }

    @Override
    public CSVRow next() {
        if(!this.hasNext()) {
            throw new NoSuchElementException("No next line");
        }
        String[] values = this.nextLine.split(",");
        CSVRow row = new CSVRow(this.headers, values);
        try {
            this.nextLine = this.reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return row;
    }
}
