	package his.markit.hotel.booking.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import his.markit.hotel.booking.exception.BookingException;
import his.markit.hotel.booking.model.Booking;
import his.markit.hotel.booking.model.BookingKey;
import his.markit.hotel.booking.model.Hotel;

public class BookingManagerImpl implements BookingManager {
	private Collection<Integer> rooms;
	private Map<BookingKey, Booking> bookingsByDate;
	
	public BookingManagerImpl(Hotel hotel) {
		rooms = hotel.getRooms();
		bookingsByDate = new ConcurrentHashMap<BookingKey, Booking>();
	}
	
	public boolean isRoomAvailable(Integer room, LocalDate date) {
		BookingKey key = new BookingKey(date, room);
		Booking booking = bookingsByDate.get(key);
		return booking == null;
	}
	
	public void addBooking(String guest, Integer room, LocalDate date) throws BookingException {
		BookingKey key = new BookingKey(date, room);
		boolean roomAvailable = isRoomAvailable(room, date);
		if (roomAvailable) {
			Booking	booking = new Booking(guest, key.getRoom(), key.getDate());
			bookingsByDate.putIfAbsent(key, booking);
		} else {
			throw new BookingException("Sorry, room " + room + " is fully booked for this date " + date);
		}
	}

	public Iterable<Integer> getAvailableRooms(LocalDate date) {
		Set<BookingKey> bookingKeys = bookingsByDate.keySet();
		List<BookingKey> bookedRoomsByDate =  bookingKeys.parallelStream().filter(key -> key.getDate().equals(date)).collect(Collectors.toList());
		if (null != bookedRoomsByDate) {
			List<Integer> bookedRooms = bookedRoomsByDate.stream().map(key -> key.getRoom()).collect(Collectors.toList());
			return rooms.stream().filter(room -> !bookedRooms.contains(room)) .collect(Collectors.toSet());
		}
		return Collections.unmodifiableCollection(rooms);
	}

}
