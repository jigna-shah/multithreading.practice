package his.markit.hotel.booking;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
	
public final class Hotel {
	private Set<Integer> rooms = new HashSet<Integer>();
	
	public Hotel(Set<Integer> rooms) {
		this.rooms = rooms;
	}

	/**
	 * @return the rooms
	 */
	public Collection<Integer> getRooms() {
		return Collections.unmodifiableCollection(rooms);
	}
	

}
