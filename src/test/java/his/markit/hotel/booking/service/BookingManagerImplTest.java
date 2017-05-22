package his.markit.hotel.booking.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

import his.markit.hotel.booking.exception.BookingException;
import his.markit.hotel.booking.model.Hotel;
import his.markit.hotel.booking.service.BookingManagerImpl;

public class BookingManagerImplTest {
	
	
	@Test
	public void isRoomAvailable() {
		BookingManagerImpl manager = new BookingManagerImpl(setupAndGetHotel());
		Assert.assertTrue(manager.isRoomAvailable(101, LocalDate.now()));
	}
	
	@Test
	public void addBooking() {
		BookingManagerImpl manager = new BookingManagerImpl(setupAndGetHotel());
		//room is available
		Assert.assertTrue(manager.isRoomAvailable(101, LocalDate.now()));
		
		//room is booked
		try {
			manager.addBooking("Smith", 101, LocalDate.now());
		} catch (BookingException e) {
			Assert.fail("BookingException not expected. Room should have been booked successfully");
		}
		Assert.assertFalse(manager.isRoomAvailable(101, LocalDate.now()));
		
		//room is unavailable, throws exception
		try {
			manager.addBooking("Jones", 101, LocalDate.now());
			Assert.fail("BookingException expected as room is already booked by other guest");
		} catch (BookingException e) {
			
		}
	}
	
	@Test
	public void getAvailableRooms() throws BookingException {
		BookingManagerImpl manager = new BookingManagerImpl(setupAndGetHotel());
		List<Integer> expectedAvailableRooms = new LinkedList<Integer>();
		expectedAvailableRooms.add(102);
		expectedAvailableRooms.add(201);
		expectedAvailableRooms.add(202);
		
		LocalDate today = LocalDate.now();
		Assert.assertTrue(manager.isRoomAvailable(101, today));
		manager.addBooking("Smith", 101, today);
		Iterable<Integer> availableRooms = manager.getAvailableRooms(today);
		Assert.assertEquals(expectedAvailableRooms,availableRooms);
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
