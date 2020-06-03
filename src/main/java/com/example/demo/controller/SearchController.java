package com.example.demo.controller;

import com.example.demo.services.SearchService;
import com.example.demo.model.request.SearchRequest;
import com.example.demo.model.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/counter-api")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Value("classpath:static/sample.txt")
    Resource resourceFile;

    @GetMapping(path = "/paragraph",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getParagraph() {

        try {
            String content = searchService.readFile(resourceFile.getFile());
            return new ResponseEntity(content, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(
            path = "/search",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SearchResponse> search(
            @RequestBody SearchRequest searchRequest) {
        SearchResponse searchResponse = new SearchResponse();

        try {
            Map<String, Integer> map = searchService.searchWords(searchRequest.getSearchText(), resourceFile.getFile());
            List<Map.Entry<String, Integer>> listOfMap = map.entrySet()
                    .stream()
                    .collect(Collectors.toList());
            searchResponse.setCount(listOfMap);
            return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/top/{value}", produces = "text/csv")
    public ResponseEntity generateReport(@PathVariable Integer value) {

        try {
            File fileInput = resourceFile.getFile();
            File fileOut = searchService.topxWordCount(value, fileInput);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileOut.getName() + ".csv")
                    .contentLength(fileOut.length())
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new FileSystemResource(fileOut));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
}
