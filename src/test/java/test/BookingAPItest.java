package test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
public class BookingAPItest {
	 // Base URI
    static {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void testGetBooking_Positive() {
        int validBookingId = 1416; // Replace with a valid ID

        // Send GET request
        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + validBookingId)
                .then()
                .statusCode(200) // Assert status code
                .extract().response();

        // Assert response data
        String firstName = response.jsonPath().getString("firstname");
        String lastName = response.jsonPath().getString("lastname");
        int totalPrice = response.jsonPath().getInt("totalprice");
        String checkin = response.jsonPath().getString("bookingdates.checkin");
        String checkout = response.jsonPath().getString("bookingdates.checkout");
        String additionalNeeds = response.jsonPath().getString("additionalneeds");

        Assert.assertEquals("testFirstName", firstName, "Firstname mismatch");
        Assert.assertEquals("lastName", lastName, "Lastname mismatch");
        Assert.assertEquals(10, totalPrice, "Total price mismatch");
        Assert.assertEquals("2022-01-01", checkin, "Checkin date mismatch");
        Assert.assertEquals("2024-01-01", checkout, "Checkout date mismatch");
        Assert.assertEquals("testAdd", additionalNeeds, "Additional needs mismatch");

        System.out.println("Positive Test Passed: Booking details match");
    }

    @Test
    public void testGetBooking_NonExistentId() {
        int invalidBookingId = 99999; // Use a non-existent ID

        // Send GET request
        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + invalidBookingId)
                .then()
                .statusCode(404) // Assert status code for non-existent ID
                .extract().response();

        // Assert response body is empty
        Assert.assertTrue(response.asString().isEmpty(), "Response body should be empty for non-existent ID");

        System.out.println("Negative Test Passed: Non-existent ID handled correctly");
    }

    @Test
    public void testGetBooking_InvalidIdFormat() {
        String invalidId = "invalidId"; // Invalid ID format

        // Send GET request
        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + invalidId)
                .then()
                .statusCode(400) // Assert status code for invalid ID format
                .extract().response();

        // Assert response contains an error message
        Assert.assertTrue(response.asString().contains("Bad Request"), "Expected 'Bad Request' error");

        System.out.println("Negative Test Passed: Invalid ID format handled correctly");
    }
}
