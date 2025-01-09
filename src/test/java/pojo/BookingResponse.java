package pojo;

public class BookingResponse {
	 private int bookingid;
	    private BookingRequest booking;

	    // Getters and Setters
	    public int getBookingid() {
	        return bookingid;
	    }

	    public void setBookingid(int bookingid) {
	        this.bookingid = bookingid;
	    }

	    public BookingRequest getBooking() {
	        return booking;
	    }

	    public void setBooking(BookingRequest booking) {
	        this.booking = booking;
	    }
}
