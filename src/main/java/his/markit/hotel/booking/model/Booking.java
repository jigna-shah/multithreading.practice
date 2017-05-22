package his.markit.hotel.booking.model;

import java.time.LocalDate;

public final class Booking {
	private String guest;
	private Integer room;
	private LocalDate date;
	
	public Booking(String guest, Integer room, LocalDate date) {
		this.guest = guest;
		this.room = room;
		this.date = date;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Booking [guest=").append(guest).append(", room=").append(room).append(", date=").append(date)
				.append("]");
		return builder.toString();
	}
	
}
