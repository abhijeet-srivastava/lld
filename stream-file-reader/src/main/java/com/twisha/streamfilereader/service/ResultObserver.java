package com.twisha.streamfilereader.service;

import java.util.List;

public interface ResultObserver {
    void onResult(List<String> results);
}
