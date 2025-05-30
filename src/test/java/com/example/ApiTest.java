package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {

   
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    
    @Test
    public void getTest() {
        try {
            given()
                .accept(ContentType.JSON) 
            .when()
                .get("/posts/1") 
            .then()
                .log().all() 
                .statusCode(200) 
                .contentType(ContentType.JSON) 
                .body("userId", equalTo(1))
                .body("id", equalTo(1)) 
                .body("title", notNullValue()) 
                .body("body", notNullValue()) 
                .time(lessThan(3000L)); 
        } catch (Exception e) {
            System.err.println("GET Test Hatası: " + e.getMessage());
        }
    }

    
    @Test
    public void postTest() {
        String requestBody = "{\n" +
         "    \"title\": \"Deneme Başlığı\",\n" +
         "    \"body\": \"Bu bir test içeriğidir.\",\n" +
         "    \"userId\": 1\n" +
          "}";

        try {
            given()
                .header("Content-Type", "application/json") 
                .body(requestBody) 
            .when()
                .post("/posts") 
            .then()
                .log().all() 
                .statusCode(201)
                .contentType(ContentType.JSON) 
                .body("title", equalTo("Deneme Başlığı"))
                .body("body", equalTo("Bu bir test içeriğidir."))
                .body("userId", equalTo(1)) 
                .body("id", notNullValue()) 
                .time(lessThan(3000L)); 
        } catch (Exception e) {
            System.err.println("POST Test Hatası: " + e.getMessage());
        }
    }
}
