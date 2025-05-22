package com.twisha.streamfilereader.service;

import java.util.List;

public class ConsoleResultHandler implements ResultObserver {
    @Override
    public void onResult(List<String> results) {
        results.forEach(System.out::println);
    }
}
