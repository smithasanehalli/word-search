package com.example.demo;

import com.example.demo.services.SearchServiceImpl;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;

import static org.junit.Assert.*;

public class SearchServiceUnitTest {

    public static File file = new File("src/main/resources/test/testSample.txt");
    SearchServiceImpl searchServiceImpl = new SearchServiceImpl();

    @Test
    public void readFile_isContentNotnull() throws Exception {

        assertNotNull(searchServiceImpl.readFile(file));

    }

    @Test(expected = NoSuchFileException.class)
    public void readFile_whenFileNotPresent() throws Exception {

        searchServiceImpl.readFile(new File("src/main/resources/test/T.txt"));

    }

    @Test
    public void searchWords_withEmptyString() throws Exception {

        List<String> searchList = Arrays.asList(new String[]{""});
        Map<String, Integer> actualResult = searchServiceImpl.searchWords(searchList, file);
        assertTrue(actualResult.isEmpty());

    }

    @Test
    public void searchWords_whichAreNotPresentInFile() throws Exception {

        String[] searchArray = {"xxx", "yyy"};
        List<String> searchList = Arrays.asList(searchArray);

        Map<String, Integer> actualResult = searchServiceImpl.searchWords(searchList, file);
        assertEquals(0, actualResult.values().stream().reduce(0, Integer::sum).intValue());

    }

    @Test
    public void searchWords_whichArePresentInFile() throws Exception {

        String[] searchArray = {"duis", "sed", "donec", "augue", "pellentesque", "123"};
        List<String> searchList = Arrays.asList(searchArray);

        Map<String, Integer> actualResult = searchServiceImpl.searchWords(searchList, file);
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
