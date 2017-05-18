package his.markit.hotel.booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BookingManagerImplTest {
	
	private static Set<Integer> rooms = new HashSet<Integer>();
	private ConcurrentHashMap<LocalDate, Set<Booking>> roomsByDate = new ConcurrentHashMap<LocalDate, Set<Booking>>();
	
	@Before
	public void setup() {
		rooms.add(101);
		rooms.add(102);
		rooms.add(201);
		rooms.add(202);
	}
	
	@Test
	public void isRoomAvailable() {
		BookingManagerImpl manager = new BookingManagerImpl(new Hotel(rooms));
		Assert.assertTrue(manager.isRoomAvailable(101, LocalDate.now()));
	}
	
	@Test
	public void addBooking() {
		BookingManagerImpl manager = new BookingManagerImpl(new Hotel(rooms));
		Assert.assertTrue(manager.isRoomAvailable(101, LocalDate.now()));
		manager.addBooking("Smith", 101, LocalDate.now());
		Assert.assertFalse(manager.isRoomAvailable(101, LocalDate.now()));
		manager.addBooking("Jones", 101, LocalDate.now());
	}

}
