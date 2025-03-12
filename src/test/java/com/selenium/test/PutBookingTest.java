package com.selenium.test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PutBookingTest {

    @Test
    public void putBookingTest() {
        // Base URL
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // PUT isteği gönder ve yanıtı kontrol et
        given()
            .contentType("application/json")
            .body("{\n" +
                    "    \"firstname\" : \"Jane\",\n" +
                    "    \"lastname\" : \"Smith\",\n" +
                    "    \"totalprice\" : 150,\n" +
                    "    \"depositpaid\" : true,\n" +
                    "    \"bookingdates\" : {\n" +
                    "        \"checkin\" : \"2025-02-01\",\n" +
                    "        \"checkout\" : \"2025-02-15\"\n" +
                    "    },\n" +
                    "    \"additionalneeds\" : \"Dinner\"\n" +
                    "}")
            .when()
            .put("/booking/1")  // 1 numaralı booking'i güncelle
            .then()
            .statusCode(200)  // Status code kontrolü
            .body("firstname", equalTo("Jane"))  // Güncellenmiş firstname kontrolü
            .body("lastname", equalTo("Smith"));
    }
}
