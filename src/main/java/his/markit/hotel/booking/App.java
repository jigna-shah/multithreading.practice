package his.markit.hotel.booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args ) {
    	Set<Integer> rooms = new HashSet<Integer>();
    	rooms.add(101);
    	rooms.add(102);
    	rooms.add(201);
    	rooms.add(202);
    	Hotel hotel = new Hotel(rooms);
        BookingManager bm = new BookingManagerImpl(hotel);
        LocalDate today = LocalDate.now();
        HotelStaff staff1 = new HotelStaff(bm, new Booking("Smith", 101, today));
        HotelStaff staff2 = new HotelStaff(bm, new Booking("Laurence", 102, today));
        //HotelStaff staff3 = new HotelStaff(bm, new Booking("Cool", 102, today));
        
        HotelStaff staff4 = new HotelStaff(bm, new Booking("Cool", 102, today.plus(1, ChronoUnit.DAYS)));
        
        Thread t1 = new Thread(staff1);
        Thread t2 = new Thread(staff2);
        //Thread t3 = new Thread(staff3);
        Thread t4 = new Thread(staff4);
        
        t1.start();
        t2.start();
        //t3.start();
        t4.start();
        
    }
}
