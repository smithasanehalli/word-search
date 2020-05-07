package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    public String readFile(File file) throws IOException;

    public Map<String, Integer> searchWords(List<String> searchList, File file) throws IOException;

    public File topxWordCount(Integer x, File file) throws IOException;
}
