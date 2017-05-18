package his.markit.hotel.booking;

public class HotelStaff implements Runnable {
	
	private BookingManager bookingManager;
	private Booking booking;
	
	public HotelStaff(BookingManager bm, Booking newBooking) {
		this.bookingManager = bm;
		this.booking = newBooking;
	}

	public void run() {
		bookingManager.getAvailableRooms(booking.getDate());
		bookingManager.addBooking(booking.getGuest(), booking.getRoom(), booking.getDate());
		bookingManager.getAvailableRooms(booking.getDate());
	}
	

}
