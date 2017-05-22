	package his.markit.hotel.booking.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import his.markit.hotel.booking.exception.BookingException;
import his.markit.hotel.booking.model.Booking;
import his.markit.hotel.booking.model.Hotel;

public class BookingManagerImpl implements BookingManager {
private ReentrantLock lock = new ReentrantLock();	
	private Collection<Integer> rooms;
	private Map<LocalDate, Set<Booking>> bookingsByDate;
	private Map<LocalDate, Set<Integer>> bookedRoomsByDate;
	
	public BookingManagerImpl(Hotel hotel) {
		rooms = hotel.getRooms();
		bookingsByDate = new ConcurrentHashMap<LocalDate, Set<Booking>>();
		bookedRoomsByDate = new ConcurrentHashMap<LocalDate, Set<Integer>>();
	}
	
	public boolean isRoomAvailable(Integer room, LocalDate date) {
		if (!rooms.contains(room)) {
			//should throw invalid exception
		}
		boolean booked = false;
		Set<Booking> bookings = bookingsByDate.get(date);
		if (bookings != null) {
			for (Booking booking : bookings) {
				if (booking.getRoom().equals(room)) {
					booked = true;
					break;
				}
			}
		}
		System.out.println("isRoomAvailable::: thread name: " + Thread.currentThread().getName() + ", isBooked::::" + booked + ", room:::::" + room);
		return !booked;
	}
	
	public void addBooking(String guest, Integer room, LocalDate date) throws BookingException {
		try {
			lock.lock();
			boolean roomAvailable = isRoomAvailable(room, date);
			if (roomAvailable) {
				bookRoom(guest, room, date);
				maintainBookedRooms(room, date);
			} else {
				throw new BookingException("Sorry, room " + room + " is fully booked for this date " + date);
			}
		} finally {
			lock.unlock();
		}
	}

	private void maintainBookedRooms(Integer room, LocalDate date) {
		Set<Integer> bookedRooms =  bookedRoomsByDate.get(date);
		if (bookedRooms == null) {
			bookedRooms = new HashSet<Integer>();
			bookedRoomsByDate.put(date, bookedRooms);
		}
		bookedRooms.add(room);
	}

	private void bookRoom(final String guest, final Integer room, final LocalDate date) {
		Set<Booking> bookings =  bookingsByDate.get(date);
		if (bookings == null) {
			bookings = new HashSet<Booking>();
			bookingsByDate.put(date, bookings);
		}
		Booking newBooking = new Booking(guest, room, date);
		bookings.add(newBooking);
		System.out.println("Room booked, Booking Info :: " + newBooking);
	}

	public Iterable<Integer> getAvailableRooms(LocalDate date) {
		Set<Integer> bookedRooms =  bookedRoomsByDate.get(date);
		System.out.println("###booked rooms###" + rooms);
		Set<Integer> availableRooms = rooms.stream().filter(room -> !bookedRooms.contains(room)) .collect(Collectors.toSet()); 
		System.out.println("available rooms" + availableRooms);
		return availableRooms;
	}

}
