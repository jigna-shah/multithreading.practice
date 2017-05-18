package his.markit.hotel.booking;

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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((guest == null) ? 0 : guest.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Booking other = (Booking) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (guest == null) {
			if (other.guest != null) {
				return false;
			}
		} else if (!guest.equals(other.guest)) {
			return false;
		}
		if (room == null) {
			if (other.room != null) {
				return false;
			}
		} else if (!room.equals(other.room)) {
			return false;
		}
		return true;
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

	/**
	 * @return the guest
	 */
	public String getGuest() {
		return guest;
	}

	/**
	 * @return the room
	 */
	public Integer getRoom() {
		return room;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	
}
