package com.example.demo;

import com.example.demo.services.SearchServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class SearchServiceTest {

    File file = null;
    SearchServiceImpl searchServiceImpl = new SearchServiceImpl();


    @Before
    public void getFile() throws Exception {
        file = new File("src/test/resources/TestSample.txt");
    }

    @Test
    public void readFile_isContentNotnull() throws Exception {

        String content = "";
        content = searchServiceImpl.readFile(file);
        assertNotNull(content);

    }

    @Test
    public void searchWords_withEmptyString() throws Exception {

        Map<String, Integer> actualResult = null;
        String[] searchArray = {""};
        List<String> searchList = Arrays.asList(searchArray);
        actualResult = searchServiceImpl.searchWords(searchList, file);
        assertTrue(actualResult.isEmpty());

    }

    @Test
    public void searchWords_whichAreNotPresentInFile() throws Exception {
        SearchServiceImpl searchServiceImpl = new SearchServiceImpl();
        Map<String, Integer> actualResult = null;
        String[] searchArray = {"xxx", "yyy"};
        List<String> searchList = Arrays.asList(searchArray);

        actualResult = searchServiceImpl.searchWords(searchList, file);
        assertEquals(0, actualResult.values().stream().reduce(0, Integer::sum).intValue());

    }

    @Test
    public void searchWords_whichArePresentInFile() throws Exception {

        Map<String, Integer> actualResult = null;
        String[] searchArray = {"duis", "sed", "donec", "augue", "pellentesque", "123"};
        List<String> searchList = Arrays.asList(searchArray);

        actualResult = searchServiceImpl.searchWords(searchList, file);
        System.out.println(actualResult.toString());

        assertEquals(11, actualResult.get("duis").intValue());
        assertEquals(16, actualResult.get("sed").intValue());
        assertEquals(8, actualResult.get("donec").intValue());
        assertEquals(7, actualResult.get("augue").intValue());
        assertEquals(6, actualResult.get("pellentesque").intValue());
        assertEquals(0, actualResult.get("123").intValue());
    }


    @Test
    public void topxWordCount_doesReturnsFile() throws IOException {
        File output = searchServiceImpl.topxWordCount(2, file);
        assertNotNull(output.isFile());
    }

    @Test
    public void topxWordCount_positiveCase() throws IOException {
        File output = searchServiceImpl.topxWordCount(1, file);

        Scanner scanner = new Scanner(output);
        String firstLine = scanner.nextLine();
        assertTrue(firstLine.contains("eget|17"));
    }
}
