package com.selenium.test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostBookingTest {

    @Test
    public void postBookingTest() {
        // Base URL
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // POST isteği gönder ve yanıtı kontrol et
        given()
            .contentType("application/json")
            .body("{\n" +
                    "    \"firstname\" : \"John\",\n" +
                    "    \"lastname\" : \"Doe\",\n" +
                    "    \"totalprice\" : 100,\n" +
                    "    \"depositpaid\" : true,\n" +
                    "    \"bookingdates\" : {\n" +
                    "        \"checkin\" : \"2025-01-01\",\n" +
                    "        \"checkout\" : \"2025-01-15\"\n" +
                    "    },\n" +
                    "    \"additionalneeds\" : \"Breakfast\"\n" +
                    "}")
            .when()
            .post("/booking")
            .then()
            .statusCode(200)  // Status code kontrolü
            .body("booking.firstname", equalTo("John"))  // Yanıt body kontrolü
            .body("booking.lastname", equalTo("Doe"));
    }
}
