package his.markit.hotel.booking.service;

import java.time.LocalDate;

import his.markit.hotel.booking.exception.BookingException;

public interface BookingManager {
	
	public boolean isRoomAvailable(Integer room, LocalDate date);
	
	public void addBooking(String guest, Integer room, LocalDate date) throws BookingException;
	
	public Iterable<Integer> getAvailableRooms(LocalDate date); 
}
