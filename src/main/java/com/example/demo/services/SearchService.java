package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {
    /**
     * Reads text file from resource static folder
     * @return content as String
     */
    public String readFile() {
        String content = "";
        try {

            File file = ResourceUtils.getFile("classpath:static/sample.txt");
            //Read File Content
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return content;
    }

    /**
     *
     * @param searchList - list of words to be searched
     * @return word count map
     */
    public Map<String, Integer> searchWords(List<String> searchList) {
        String[] words = readFile().split("\\s+|\\,|\\.");
        Map<String, Integer> wordCountMap = new HashMap<String, Integer>();

        for (String s : searchList) {
            if (!s.trim().isEmpty()) {
                wordCountMap.put(s.toLowerCase(), 0);
            }

        }
        for (int i = 0; i < words.length; i++) {
            if (wordCountMap.containsKey(words[i].toLowerCase())) {
                int count = wordCountMap.get(words[i].toLowerCase());
                wordCountMap.put(words[i].toLowerCase(), count + 1);
            }
        }

        return wordCountMap;
    }

    /**
     * Reads the complete text file and count number of unique words
     * @return map of all words count
     */
    public Map<String, Integer> wordCount() {

        Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
        String[] splitWords = readFile().split("\\s+|\\,|\\.");

        for (String word : splitWords) {
            if (!word.trim().isEmpty()) {
                Integer oldCount = wordCountMap.get(word.toLowerCase());
                if (oldCount == null) {
                    oldCount = 0;
                }
                wordCountMap.put(word.toLowerCase(), oldCount + 1);
            }

        }

        Map<String, Integer> sortedMap = wordCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println(sortedMap);

        return Collections.unmodifiableMap(sortedMap);
    }

    /**
     *
     * @param x - Number of words
     * @return top X word count
     */
    public File topxWordCount(Integer x) {

        Map<String, Integer> sortedMap = wordCount();

        // get all keys from the LinkedHashMap
        Set<String> setKeys = sortedMap.keySet();
        //convert set to LinkedList
        LinkedList<String> listKeys = new LinkedList<String>(setKeys);
        // get descending iterator
        Iterator<String> iterator = listKeys.descendingIterator();
        //iterate the keys and get the values from the map
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < x; i++) {
            String key = iterator.next();
            sb.append(key + "|" + sortedMap.get(key));
            sb.append("\n");
        }

        File file = new File("result.txt");

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile(), false);
            bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close(); // Be sure to close BufferedWriter
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
