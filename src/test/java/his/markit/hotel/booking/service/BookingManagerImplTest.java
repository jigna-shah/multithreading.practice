package his.markit.hotel.booking.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
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
		Assert.assertTrue(manager.isRoomAvailable(101, LocalDate.now()));
		try {
			manager.addBooking("Smith", 101, LocalDate.now());
		} catch (BookingException e) {
			Assert.fail("BookingException not expected. Room should have been booked successfully");
		}
		Assert.assertFalse(manager.isRoomAvailable(101, LocalDate.now()));
		try {
			manager.addBooking("Jones", 101, LocalDate.now());
			Assert.fail("BookingException expected as room is already booked by other guest");
		} catch (BookingException e) {
			
		}
	}
	
	@Test
	public void getAvailableRooms() throws BookingException {
		BookingManagerImpl manager = new BookingManagerImpl(setupAndGetHotel());
		
		LocalDate today = LocalDate.now();
		Assert.assertTrue(manager.isRoomAvailable(101, today));
		manager.addBooking("Smith", 101, today);
		Iterable<Integer> availableRooms = manager.getAvailableRooms(today);
		Set<Integer> expectedAvailableRooms = new HashSet<Integer>();
		expectedAvailableRooms.add(201);
		expectedAvailableRooms.add(202);
		expectedAvailableRooms.add(102);
		Assert.assertEquals(availableRooms, expectedAvailableRooms);;
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
