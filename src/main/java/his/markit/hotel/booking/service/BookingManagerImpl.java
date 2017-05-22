	package his.markit.hotel.booking.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
		Booking	booking = new Booking(guest, key.getRoom(), key.getDate());
		Booking alreadyBooked  = bookingsByDate.putIfAbsent(key, booking);
		if (alreadyBooked != null) {
			throw new BookingException("Sorry, room " + room + " is fully booked for this date " + date);
		}
	}

	public Iterable<Integer> getAvailableRooms(LocalDate date) {
        List<Integer> available = new LinkedList<>();
        rooms.stream().forEach(t-> {
            if (isRoomAvailable(t, date)) {
                available.add(t);
            }
        });
        return available;
    }

}
