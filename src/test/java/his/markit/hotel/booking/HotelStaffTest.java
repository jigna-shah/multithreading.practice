package his.markit.hotel.booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
	
import org.junit.Assert;
import org.junit.Test;

import his.markit.hotel.booking.model.Booking;
import his.markit.hotel.booking.model.Hotel;
import his.markit.hotel.booking.service.BookingManager;
import his.markit.hotel.booking.service.BookingManagerImpl;

public class HotelStaffTest {
	ExecutorService service = Executors.newFixedThreadPool(10);
	
	@Test
	public void testBooking() {
		
		BookingManager bm = new BookingManagerImpl(setupAndGetHotel());
		
		LocalDate today = LocalDate.now();
		//creating threads        
        HotelStaff staff1 = new HotelStaff(bm, new Booking("Smith", 101, today));
        HotelStaff staff2 = new HotelStaff(bm, new Booking("Laurence", 102, today));
        HotelStaff staff3 = new HotelStaff(bm, new Booking("Cool", 102, today)); //should throw error
        HotelStaff staff4 = new HotelStaff(bm, new Booking("Cool", 102, today.plus(1, ChronoUnit.DAYS)));
        HotelStaff staff5 = new HotelStaff(bm, new Booking("Amir", 101, today)); //should throw error
        
        runAddBookingTask(staff1, true);
        runAddBookingTask(staff2, true);
        runAddBookingTask(staff3, false);
        runAddBookingTask(staff4, true);
        runAddBookingTask(staff5, false);
    }

	private void runAddBookingTask(HotelStaff thread, boolean expectedBookingSuccess) {
		
        Future<Boolean> future = null;
		future = service.submit(thread);
				
		try {
			boolean isBookingSuccess = future.get();
			Assert.assertEquals(expectedBookingSuccess, isBookingSuccess);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		} catch (ExecutionException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private Hotel setupAndGetHotel() {
		Set<Integer> rooms = new HashSet<Integer>();
		rooms.add(101);
		rooms.add(102);
		rooms.add(201);
		rooms.add(202);
		return new Hotel(rooms);
	}

}
