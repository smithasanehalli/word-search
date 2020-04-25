package com.example.demo.model.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResponse {
    private List<Map.Entry<String, Integer>> counts = new ArrayList<Map.Entry<String, Integer>>();

    public List<Map.Entry<String, Integer>> getCount() {
        return counts;
    }

    public void setCount(List<Map.Entry<String, Integer>> count) {
        this.counts = count;
    }
}

