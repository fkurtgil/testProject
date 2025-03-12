package com.selenium.test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetBookingTest {

    @Test
    public void getBookingTest() {
        // Base URL
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // GET isteği gönder ve status code ve response'ı kontrol et
        given()
            .when()
            .get("/booking")
            .then()
            .statusCode(200)  // Status code kontrolü
            .body("bookingid", not(empty()));  // bookingid'nin boş olmaması gerektiğini doğrula
    }
}
