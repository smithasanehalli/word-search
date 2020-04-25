package com.example.demo.controller;

import com.example.demo.services.SearchService;
import com.example.demo.model.request.SearchRequest;
import com.example.demo.model.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/counter-api")
public class SearchControler {

    @Autowired
    private SearchService searchService;

    @GetMapping(path = "/paragraph",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getParagraph() {
        return new ResponseEntity(searchService.readFile(), HttpStatus.OK);
    }


    @PostMapping(
            path = "/search",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SearchResponse> search(
            @Valid @RequestBody SearchRequest searchRequest) {
        SearchResponse searchResponse = new SearchResponse();
        Map<String, Integer> map = searchService.searchWords(searchRequest.getSearchText());
        List<Map.Entry<String, Integer>> listOfMap = map.entrySet()
                .stream()
                .collect(Collectors.toList());
        searchResponse.setCount(listOfMap);
        return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
    }


    @GetMapping(value = "/top/{value}", produces = "text/csv")
    public ResponseEntity generateReport(@PathVariable Integer value) {

        File file = searchService.topxWordCount(value);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName() + ".csv")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new FileSystemResource(file));

    }

}
