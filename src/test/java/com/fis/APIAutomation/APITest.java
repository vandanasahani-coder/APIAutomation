package com.fis.APIAutomation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class APITest {

    @Test
    public void verifyBPIInfo() {
        Response response = RestAssured
                .given()
                .baseUri("https://api.coindesk.com")
                .basePath("/v1/bpi/currentprice.json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Print entire response (for debugging/logging if needed)
        System.out.println("Response Body:\n" + response.prettyPrint());

        // Verify keys USD, GBP, EUR exist in bpi
        assertTrue(response.jsonPath().getMap("bpi").containsKey("USD"), "USD not found in bpi");
        assertTrue(response.jsonPath().getMap("bpi").containsKey("GBP"), "GBP not found in bpi");
        assertTrue(response.jsonPath().getMap("bpi").containsKey("EUR"), "EUR not found in bpi");

        // Verify GBP description
        String gbpDescription = response.jsonPath().getString("bpi.GBP.description");
        assertEquals(gbpDescription, "British Pound Sterling", "GBP description mismatch");
    }
}
