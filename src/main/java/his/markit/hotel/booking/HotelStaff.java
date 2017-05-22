package his.markit.hotel.booking;

import java.util.concurrent.Callable;
import his.markit.hotel.booking.model.Booking;
import his.markit.hotel.booking.service.BookingManager;

public class HotelStaff implements Callable<Boolean> {
	
	private BookingManager bookingManager;
	private Booking booking;
	
	public HotelStaff(BookingManager bm, Booking newBooking) {
		this.bookingManager = bm;
		this.booking = newBooking;
		
	}

	public Boolean call() throws Exception {
		bookingManager.addBooking(booking.getGuest(), booking.getRoom(), booking.getDate());
		return true;
	}
}

