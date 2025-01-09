package test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.BookingDates;
import pojo.BookingRequest;
import pojo.BookingResponse;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class BookingTest {
	
	@Test
	public void testAPi() {
        // Base URI
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Create BookingDates object
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2022-01-01");
        bookingDates.setCheckout("2024-01-01");

        // Create BookingRequest object
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setFirstname("testFirstName");
        bookingRequest.setLastname("lastName");
        bookingRequest.setTotalprice(10.11);
        bookingRequest.setDepositpaid(true);
        bookingRequest.setBookingdates(bookingDates);
        bookingRequest.setAdditionalneeds("testAdd");

        // Send POST request to create a new booking
        Response postResponse = given()
            .header("Content-Type", "application/json")
            .body(bookingRequest)
            .when()
            .post("/booking")
            .then()
            .statusCode(200) // Assert response code
            .extract().response();

        // Deserialize response into BookingResponse class
        BookingResponse bookingResponse = postResponse.as(BookingResponse.class);
        int bookingId = bookingResponse.getBookingid();
        System.out.println("Booking ID: " + bookingId);

        // Send GET request to validate the added booking
        Response getResponse = given()
            .header("Accept", "application/json")
            .when()
            .get("/booking/" + bookingId)
            .then()
            .statusCode(200) // Assert response code
            .extract().response();

        // Print GET response
        System.out.println("GET Response:");
        System.out.println(getResponse.asPrettyString());

        // Validate that the booking details match
        BookingRequest fetchedBooking = getResponse.as(BookingRequest.class);
        assert fetchedBooking.getFirstname().equals(bookingRequest.getFirstname());
        assert fetchedBooking.getLastname().equals(bookingRequest.getLastname());
        //assert fetchedBooking.getTotalprice() == bookingRequest.getTotalprice();
        assert fetchedBooking.isDepositpaid() == bookingRequest.isDepositpaid();
        assert fetchedBooking.getBookingdates().getCheckin().equals(bookingRequest.getBookingdates().getCheckin());
        assert fetchedBooking.getBookingdates().getCheckout().equals(bookingRequest.getBookingdates().getCheckout());
        assert fetchedBooking.getAdditionalneeds().equals(bookingRequest.getAdditionalneeds());

        System.out.println("Validation Successful: The booking details match!");
    }
}
