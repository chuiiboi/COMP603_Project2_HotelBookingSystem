package HotelBookingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author 2114050
 */
public class Bookings {

    private ArrayList<Booking> bookingList;
    public Guests guests;
    public Rooms rooms;
    private final DBManager dbManager;

    // Class that holds most controls for the Booking ArrayList:
    public Bookings(DBManager dbManager, Guests guests, Rooms rooms) {
        this.bookingList = new ArrayList<>();
        this.guests = guests;
        this.rooms = rooms;
        this.dbManager = dbManager;
        this.initializeBookingsTable(); // Creates BOOKINGS table in HotelDB if not already created
        this.getBookingsFromDB();
    }

    // Getter:
    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    // Adds a new Booking object to the ArrayList then updates DB table:
    // Returns added Guest object;
    public Booking add(Booking booking) {
        bookingList.add(booking);
        dbManager.updateDB("INSERT INTO BOOKINGS VALUES ('" + booking.getGuest().getFullName() + "', " + booking.getRoom().getRoomNum() + ", " + booking.getDays() + ")");
        return booking;
    }

    // Removes a specified Booking object from the ArrayList:
    public void remove(Booking booking) {
        if (!bookingList.contains(booking)) {
            System.out.println("Booking does not exist.");
        } else {
            bookingList.remove(booking);
            rooms.updateAvailability(booking.getRoom(), true);
            dbManager.updateDB("DELETE FROM BOOKINGS WHERE ROOMNUM=" + booking.getRoom().getRoomNum());
            System.out.println("Booking has been removed.");
        }
    }

    public void getBookingsFromDB() {
        Booking booking;
        Guest guest = null;
        Room room = null;
        ResultSet rs = this.dbManager.queryDB("SELECT * FROM BOOKINGS");
        try {
            while (rs.next()) {
                for(Guest g : guests.getGuestList()) { //Find Guest reference to correct guest
                    if(g.getFullName().equals(rs.getString(1))) {
                        guest = g;
                    }
                }
                room = rooms.getRoomMap().get(rs.getInt(2)); // Find Room reference to correct room
                booking = new Booking(guest, room, rs.getInt(3));
                bookingList.add(booking);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void initializeBookingsTable() {
        if (!this.dbManager.checkTableExists("BOOKINGS")) {
            this.dbManager.updateDB("CREATE  TABLE BOOKINGS  (GUESTNAME  VARCHAR(50),   ROOMNUM   INTEGER,   DAYS   INTEGER)");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (bookingList.isEmpty()) {
            sb.append("There are no bookings.");
        }
        else {
            for (Booking b : bookingList) {
                sb.append(b).append("\n");
            }
        }
        
        return sb.toString();
    }
    
}
