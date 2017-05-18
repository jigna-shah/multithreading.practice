package his.markit.hotel.booking;

import java.time.LocalDate;

public interface BookingManager {
	
	public boolean isRoomAvailable(Integer room, LocalDate date);
	
	public void addBooking(String guest, Integer room, LocalDate date);
	
	public Iterable<Integer> getAvailableRooms(LocalDate date); 
}
