	package his.markit.hotel.booking;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantLock;

public class BookingManagerImpl implements BookingManager {
private ReentrantLock lock = new ReentrantLock();
	private Set<Integer> availableRooms = new ConcurrentSkipListSet<Integer>();
	private ConcurrentHashMap<LocalDate, Set<Booking>> bookingsByDate = new ConcurrentHashMap<LocalDate, Set<Booking>>();
	private ConcurrentHashMap<LocalDate, Set<Integer>> roomsByDate = new ConcurrentHashMap<LocalDate, Set<Integer>>();
	
	public BookingManagerImpl(Hotel hotel) {
		availableRooms.addAll(hotel.getRooms());
	}
	
	public boolean isRoomAvailable(Integer room, LocalDate date) {
		boolean isBooked = false;
		Set<Booking> bookings = bookingsByDate.get(date);
		if (bookings == null) {
			return !isBooked;
		} else {
			for (Booking booking : bookings) {
				if (booking.getRoom().equals(room)) {
					isBooked = true;
					break;
				}
			}
			return !isBooked;
		}
	}
	
	public void addBooking(String guest, Integer room, LocalDate date) {
		boolean roomAvailable = isRoomAvailable(room, date);
		if (roomAvailable) {
			try {
				Set<Booking> bookings =  bookingsByDate.get(date);
				lock.lock();
				if (bookings == null) {
					bookings = new HashSet<Booking>();
					bookingsByDate.put(date, bookings);
				}
				bookings.add(new Booking(guest, room, date));
			} finally {
				lock.unlock();
			}
		} else {
			throw new RuntimeException("Sorry, room " + room + " is fully booked for this date " + date);
		}
	}

	public Iterable<Integer> getAvailableRooms(LocalDate date) {
		System.out.println("Avaible rooms," + roomsByDate.get(date));
		return roomsByDate.get(date);
	}

}
