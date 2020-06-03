package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class WordSearchApiTests {

    @Before
    public void setUp() {
        RestAssured.authentication = basic("optus", "candidates");
    }

    @Test
    public void testStatusPage() {

        given()
                .when()
                .get("http://localhost:8080/counter-api/paragraph")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void searchForGivenWords() {

        Header acceptJson = new Header("Accept", "application/json");
        ResponseBody response = (Response) given().contentType(ContentType.JSON)
                .header(acceptJson)
                .body("{\"searchText\":[\"Duis\", \"Sed\", \"Donec\", \"Augue\", \"Pellentesque\", \"123\"]}")
                .when()
                .post("http://localhost:8080/counter-api/search")
                .body();
        //{"count":[{"123":0},{"sed":16},{"duis":11},{"donec":8},{"pellentesque":6},{"augue":7}]}
        // List<Map.Entry<String, Integer>> counts = new ArrayList<Map.Entry<String, Integer>>();
        List<Map<String, Integer>> counts = response.jsonPath().get("count");
        assert (counts.size() == 6);
        System.out.println(counts);

    }

    @Test
    public void searchForTopXRecurringWords() throws IOException {

        Header acceptJson = new Header("Accept", "text/csv");
        byte[] result = given()
                .header(acceptJson)
                .get("http://localhost:8080/counter-api/top/20").asByteArray();

        System.out.println(result.length);

        File file = new File("src/main/resources/test/result.txt");
        long fileSize = file.length();
        System.out.println(fileSize);

        assertEquals(fileSize, result.length);
    }
}

