package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    String readFile(File file) throws IOException;

    Map<String, Integer> searchWords(List<String> searchList, File file) throws IOException;

    File topxWordCount(Integer x, File file) throws IOException;
}
